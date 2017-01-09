'use strict';

import express     from 'express';
import bodyParser  from 'body-parser';
import path        from 'path';
import pg          from 'pg';

// db, app, and server listening setup
const app = express();
app.use(bodyParser.json());

const connectionString = process.env.DATABASE_URL || 'postgres://localhost:5432/todo';

app.get('/', (req, res, next) => {
  res.send('Up and rowing!');
});

app.post('/api/v1/todos', (req, res, next) => {
  console.log('req.body', req.body);
  const results = [];
  // Grab data from http request
  const data = {
    text: req.body.text,
    complete: false
  };
  // Get a Postgres client from the connection pool
  pg.connect(connectionString, (err, client, done) => {
    // Handle connection errors
    if(err) {
      done();
      console.log(err);
      return res.status(500).json({success: false, data: err});
    }
    // SQL Query > Insert Data
    client.query('INSERT INTO items(text, complete) values($1, $2)',
    [data.text, data.complete]);
    // SQL Query > Select Data
    const query = client.query('SELECT * FROM items ORDER BY id ASC');
    // Stream results back one row at a time
    query.on('row', (row) => {
      results.push(row);
    });
    // After all data is returned, close connection and return results
    query.on('end', () => {
      done();
      return res.json(results);
    });
  });
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


app.listen(3000, () => {
  console.log('The row is up and rowing at localhost:3000');
});

module.exports = app;