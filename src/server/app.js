'use strict';

import express      from 'express';
import cookieParser from 'cookie-parser';
import session      from 'express-session';
import bodyParser   from 'body-parser';
import http         from 'http';
import path         from 'path';
import passport     from 'passport';
import pg           from 'pg';

import React              from 'react';
import { renderToString } from 'react-dom/server';

import Login         from './views/components/login/index';
import LoginTemplate from './views/components/login/template';

import MainApp         from './views/components/main_app/index';
import MainAppTemplate from './views/components/main_app/template';

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
app.use(session({ secret: 'secret' }));
app.use(passport.initialize());
app.use(passport.session());

function redirect (res) {
  return function (err, results) {
    res.redirect('/');
  };
}

passport.use(new FacebookStrategy({
  // pull in our app id and secret from our auth.js file
  clientID        : auth.facebookAuth.clientID,
  clientSecret    : auth.facebookAuth.clientSecret,
  callbackURL     : auth.facebookAuth.callbackURL
}, (token, refreshToken, profile, done) => {
  // save user to DB
  return done(null, profile._json);
}));

passport.serializeUser((user, done) => {
  done(null, user);
});

passport.deserializeUser((id, done) => {
  done(null, id);
});

pg.connect(connectionString, (err, client, done) => {

  // route middleware to make sure a user is logged in
  function isLoggedIn(req, res, next) {
    // if user is authenticated in the session, carry on
    if (req.isAuthenticated())
      return next();

    // if they aren't redirect them to the home page
    res.redirect('/');
  }

  app.get('/', (req, res) => {
    client.query('SELECT * FROM users;', (err, result) => {
      const users        = result.rows;
      const initialState = { users };
      const appString    = renderToString(<Login {...initialState} />);

      res.send(LoginTemplate({
        body: appString,
        title: 'Hello World from the server',
        initialState: JSON.stringify(initialState)
      }));
    });
  });

  app.post('/login', (req, res) => {
    const query = 'SELECT * FROM users WHERE first_name=$1 AND last_name=$2;';

    client.query(query, [req.body.first_name, req.body.last_name], (err, result) => {
      const user = result.rows[0];

      if (!err && user) { // loading the main app here if user logs in correctly
        client.query('SELECT * FROM universities', (err, result) => {
          const universities = result.rows;
          const initialState = { user, universities };
          const appString    = renderToString(<MainApp {...initialState} />);

          res.send(MainAppTemplate({
            body: appString,
            title: 'Main App!',
            initialState: JSON.stringify(initialState)
          }));
        });
      } else { // stay on the login page if login not successful
        res.redirect('/');
      }
    });
  });

  app.get('/welcome', (req, res) => {
    const user         = req.user;
    const initialState = { user };
    const appString    = renderToString(<MainApp {...initialState} />);

    res.send(MainAppTemplate({
      body: appString,
      title: 'Main App',
      initialState: JSON.stringify(initialState)
    }));
  });

  // GET users from a given university
  // ex: /users/university/UMBC
  app.get('/users/university/:name', (req, res) => {
    const query = 'SELECT * FROM universities WHERE UPPER(name)=UPPER($1);';

    client.query(query, [req.params.name], (err, result) => {
      const query = 'SELECT * FROM users WHERE university_id=$1;'
      const university = result.rows[0];

      if (!err && university) {
        client.query(query, [university.id], (err, result) => {
          res.send(JSON.stringify(result.rows));
        });
      } else {
        res.send('No students for this university');
      }
    });
  });

  app.post('/add', (req, res) => {
    const query = 'INSERT INTO users (first_name, last_name) VALUES ($1, $2);';

    client.query(query, [req.body.first_name, req.body.last_name], redirect(res));
  });

  app.post('/delete', (req, res) => {
    const query = 'DELETE FROM users WHERE first_name=$1 AND last_name=$2;';

    client.query(query, [req.body.first_name, req.body.last_name], redirect(res));
  });

  app.get('/auth/facebook', passport.authenticate('facebook'));

  app.get('/auth/facebook/callback',
        passport.authenticate('facebook', {
            successRedirect : '/welcome',
            failureRedirect : '/invalidlogin'
        }));

  app.get('/faceerror', (req, res) => {
    res.send('Error logging into Facebook!');
  });
});

process.on('exit', () => {
  pg.end((err) => {
    if (err) {
      throw err;
    } else {
      console.log('Shutdown postgres successfully');
    }
  })
});

server.listen(8080);

module.exports = app;