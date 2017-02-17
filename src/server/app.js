'use strict';

import express      from 'express';
import cookieParser from 'cookie-parser';
import session      from 'express-session';
import bodyParser   from 'body-parser';
import http         from 'http';
import path         from 'path';
import passport     from 'passport';
import pg           from 'pg';

// routes
import HomeRoute    from './routes/home';
import MainAppRoute from './routes/main_app';

import GetUnisRoute from './routes/universities/get_unis';

import GetCoursesByUni from './routes/courses/get_courses_by_uni';
import GetCourseById   from './routes/courses/get_course_by_id'

import GetUsersByUniNameRoute from './routes/users/get_by_uni_name';
import UpdateUserWithUni      from './routes/users/update_with_uni';

import GetStudentsForCourse from './routes/users/get_students_for_course';
import GetTutorsForCourse   from './routes/users/get_tutors_for_course';

// db, app, and server listening setup
const app              = express();
const server           = http.Server(app);
const FacebookStrategy = require('passport-facebook').Strategy;
const connectionString = process.env.DATABASE_URL || 'postgres://localhost:5432/tutorrow';
const auth             = require('./auth.js');

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

pg.connect(connectionString, (err, client, done) => {

  passport.use(new FacebookStrategy({
    // pull in our app id and secret from our auth.js file
    clientID        : auth.facebookAuth.clientID,
    clientSecret    : auth.facebookAuth.clientSecret,
    callbackURL     : auth.facebookAuth.callbackURL,
    profileFields   : ['id', 'displayName', ,'first_name', 'last_name', 'photos', 'email', 'age_range']
  }, (token, refreshToken, profile, done) => {
    // save user to DB
    client.query('SELECT * FROM users WHERE facebook_id=$1;', [profile.id], (err, result) => {
      if (result && result.rows.length) {
        // if user already in database, find their record
        return done(null, result.rows[0].facebook_id);
      } else {
        // if user not in database, create record
        const query = 'INSERT INTO users (facebook_id, full_name) VALUES ($1, $2);';

        client.query(query, [profile._json.id, profile._json.name], (err, result) => {
          return done(null, profile.id);
        });
      }
    });
  }));

  /*
   * VIEWS ROUTES
   */

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

  // GET facebook authentication
  app.get('/auth/facebook', passport.authenticate('facebook', { scope: ['email'] }));

  // Callback after authenticating with Facebook
  app.get('/auth/facebook/callback', passport.authenticate('facebook', {
    successRedirect : '/welcome',
    failureRedirect : '/faceerror'
  }));

  // Error from authenticating with Facebook
  app.get('/faceerror', (req, res) => {
    res.send('Error logging into Facebook!');
  });
});

server.listen(8080);

module.exports = app;