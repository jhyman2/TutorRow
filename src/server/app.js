'use strict';

import express      from 'express';
import cookieParser from 'cookie-parser';
import session      from 'express-session';
import bodyParser   from 'body-parser';
import log          from 'loglevel';
import http         from 'http';
import path         from 'path';
import passport     from 'passport';
import { Pool }     from 'pg';

// routes
import HomeRoute    from './routes/home';
import MainAppRoute from './routes/main_app';
import PGNotRunning from './routes/pg_not_running';

import GetUnisRoute from './routes/universities/get_unis';

import GetCoursesByUni from './routes/courses/get_courses_by_uni';
import GetCourseById   from './routes/courses/get_course_by_id'

import PostCourseSignUpAsTutor   from './routes/courses/post_course_sign_up_as_tutor';
import PostCourseSignUpAsStudent from './routes/courses/post_course_sign_up_as_student';
import CoursesCancelTutoring     from './routes/courses/course_cancel_tutoring';
import CoursesCancelStudenting   from './routes/courses/course_cancel_studenting';

import GetUsersByUniNameRoute from './routes/users/get_by_uni_name';
import UpdateUserWithUni      from './routes/users/update_with_uni';

import GetStudentsForCourse from './routes/users/get_students_for_course';
import GetTutorsForCourse   from './routes/users/get_tutors_for_course';

import GraphQL from './graphql/';

process.env.PGDATABASE = 'tutorrow';
process.env.PGHOST     = 'localhost';
process.env.PGUSER     = 'postgres';

log.setLevel('debug', true);

// db, app, and server listening setup
const app              = express();
const server           = http.Server(app);
const GoogleStrategy   = require('passport-google-oauth20').Strategy;
const auth             = require('./auth.js');
const pool             = new Pool();

app.set('view engine', 'ejs');
app.use(express.static(path.join(__dirname, '../../dist')));
app.set('views', path.join(__dirname));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser()); // read cookies (needed for auth)

// user login setup
app.use(session({
  secret: 'secret',
  resave: true,
  saveUninitialized: true
}));
app.use(passport.initialize());
app.use(passport.session());

function redirect (res) {
  return function (err, results) {
    res.redirect('/');
  };
}

passport.serializeUser((user, done) => {
  done(null, user);
});

passport.deserializeUser((id, done) => {
  done(null, id);
});

pool.connect((err, client, done) => {
  // send the mongo client to the GraphQL server
  const graphQLServer = new GraphQL(client);

  passport.use(new GoogleStrategy({
    clientID:     auth.googleAuth.clientID,
    clientSecret: auth.googleAuth.clientSecret,
    callbackURL:  auth.googleAuth.callbackURL,
    passReqToCallback: true,
    scope:  ['profile', 'email', 'name', 'displayName']
  },
  function(request, accessToken, refreshToken, profile, done) {
    // save user to DB
    client.query('SELECT * FROM users WHERE google_id=$1;', [profile.id], (err, result) => {
      if (result && result.rows.length) {
        // if user already in database, find their record
        return done(null, result.rows[0].google_id);
      } else {
        // if user not in database, create record
        const query = 'INSERT INTO users (google_id, full_name, email) VALUES ($1, $2, $3);';

        client.query(query, [profile.id, profile.displayName, profile._json.email], (err, result) => {
          return done(null, profile.id);
        });
      }
    });
  }
  ));

  /*
   * VIEWS ROUTES
   */

  // Make sure Postgres is running, serve 500 error if it isn't
  if (!client) {
    app.get('/', PGNotRunning());
    return;
  }

  // Getting the Login screen
  app.get('/', HomeRoute(client));

  // Getting the main app after logging in
  app.get('/welcome', MainAppRoute(client));

  /*
   * UNIVERSITIES ROUTES
   */

  // GET all universities
  app.get('/universities', GetUnisRoute(client));

  /*
   * COURSES ROUTES
   */

   // GET courses for a particular university
   app.get('/courses/:university_id', GetCoursesByUni(client));
   app.get('/course/:university_name/:course_id', GetCourseById(client));

   // Signing up or removing oneself as a student or a tutor
   app.post('/course/signup/:user_id/studenting/:course_id', PostCourseSignUpAsStudent(client));
   app.post('/course/signup/:user_id/tutoring/:course_id', PostCourseSignUpAsTutor(client));
   app.delete('/course/cancel/:user_id/tutoring/:course_id', CoursesCancelTutoring(client));
   app.delete('/course/cancel/:user_id/studenting/:course_id', CoursesCancelStudenting(client));

  /*
   * USERS ROUTES
   */

  // GET users from a given university
  // ex: /users/university/UMBC
  app.get('/users/university/:name', GetUsersByUniNameRoute(client));

  // GET students for a given course
  // ex: /students/24
  app.get('/students/:course_id', GetStudentsForCourse(client));

  // GET tutors for a given course
  // ex: /tutors/24
  app.get('/tutors/:course_id', GetTutorsForCourse(client));

  // UPDATE user with university id
  app.post('/user/university', UpdateUserWithUni(client));

  /*
   * AUTHENTICATION ROUTES
   */

  // GET Google authentication
  app.get('/auth/google', passport.authenticate('google', { scope: ['email', 'profile'] }));

  // Callback after authenticating with Google
  app.get('/auth/google/callback', passport.authenticate('google', {
    successRedirect : '/welcome',
    failureRedirect : '/googleerror'
  }));

  // Error from authenticating with Google
  app.get('/googleerror', (req, res) => {
    res.send('Error logging into Google!');
  });
});

server.listen(8080);

process.on('exit', (code) => {
  console.log(`About to exit with code: ${code}`);
  // todo: maybe do graphQLServer.close()
});

module.exports = app;