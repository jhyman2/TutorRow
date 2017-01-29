'use strict';

import express    from 'express';
import bodyParser from 'body-parser';
import http       from 'http';
import path       from 'path';
import pg         from 'pg';

import React              from 'react';
import { renderToString } from 'react-dom/server';

import Login         from './views/components/login/index';
import LoginTemplate from './views/components/login/template';

import MainApp         from './views/components/main_app/index';
import MainAppTemplate from './views/components/main_app/template';

// db, app, and server listening setup
const app              = express();
const server           = http.Server(app);
const connectionString = process.env.DATABASE_URL || 'postgres://localhost:5432/tutorrow';

app.set('view engine', 'ejs');
app.use(express.static(path.join(__dirname, '../../dist')));
app.set('views', path.join(__dirname));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

function redirect (res) {
  return function (err, results) {
    res.redirect('/');
  };
}

pg.connect(connectionString, (err, client, done) => {

  app.get('/', (req, res) => {
    client.query('SELECT * FROM users', (err, result) => {
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

  app.post('/add', (req, res) => {
    const query = 'INSERT INTO users (first_name, last_name) VALUES ($1, $2);';

    client.query(query, [req.body.first_name, req.body.last_name], redirect(res));
  });

  app.post('/delete', function (req, res) {
    const query = 'DELETE FROM users WHERE first_name=$1 AND last_name=$2;';

    client.query(query, [req.body.first_name, req.body.last_name], redirect(res));
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