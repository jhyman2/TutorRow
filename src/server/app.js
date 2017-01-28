'use strict';

import express     from 'express';
import bodyParser  from 'body-parser';
import http        from 'http';
import path        from 'path';
import pg          from 'pg';

import React              from 'react';
import { renderToString } from 'react-dom/server';
import App                from './views/components/index';
import template           from './views/components/template';

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
      const appString    = renderToString(<App {...initialState} />);

      res.send(template({
        body: appString,
        title: 'Hello World from the server',
        initialState: JSON.stringify(initialState)
      }));
    });
  });

  app.post('/add', (req, res) => {
    var query = 'INSERT INTO users (first_name, last_name) VALUES ($1, $2);';

    client.query(query, [req.body.first_name, req.body.last_name], redirect(res));
  });

  app.post('/delete', function (req, res) {
    var query = 'DELETE FROM users WHERE first_name=$1 AND last_name=$2;';

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

// app.set('view engine', 'ejs');
// app.use(express.static(path.join(__dirname, '../../app')));
// app.set('views', path.join(__dirname));
// app.use(bodyParser.json());
// app.use(bodyParser.urlencoded({ extended: false }));


// // Whole app is served by this one view
// app.get('/', (req, res) => {
//   res.render('views/index.ejs');
// });

// // API starts here
// app.get('/', (req, res) => {
//   res.send('index.html');
// })

// app.post('/login', (req, res) => {
//   var username = req.body.user_name;
//   var password = req.body.password;

//   // check to make sure this is legit in the database
//   MongoClient.find('users', { username: username, password: password }, (records) => {
//     if (records) {
//       res.send('/mainPage');
//     } else {
//       res.send('/errorPage', 'Wrong info');
//     }
//   });
// }

// app.post('/universities', (req, res) => {
//   var name = req.params.name;

//   // creates a new university
// });

// app.get('/universities', (req, res) => {
//   var user = req.body.user;

//   // return list of universities to user
// });


// app.get('/classes/:university', (req, res) => {
//   var university = req.params.university;

//   // returns list of classes for a given university
// });

// app.post('/university/:university/classes', (req, res) => {
//   var className, classNum, department, teacher, description, numCredits, prereqs;

//   // add a class to a university
// });

// app.post('/signup/classes/:university/:class', (req, res) => {
//   var university = req.params.university;
//   var class = req.params.class;
//   var role  = req.params.role;

//   // Sign up a student or tutor depending on role for a class
// });

// app.post('/dropout/classes/:university/:class', (req, res) => {
//   var university = req.params.university;
//   var class = req.params.class;
//   var role  = req.params.role;
// });

// app.get('/profile', (req, res) => {
//   var user = req.params.user;

//   // Return users profile information
// });

// app.delete('/profile', (req, res) => {
//   var user = req.params.user;

//   // delete users' own account
// });

server.listen(8080);

module.exports = app;