// Users
{
	_id: ObjectID,
	username: String,
	password: String,
	firstName: String,
	lastName: String,
	university: University_ID,
	classes: [ { class_id, role } ]
}

// Classes
{
	_id: ObjectID,
	name: String,
	num: int,
	dept: String,
	teacher: String,
	description: String,
	credits: int,
	prereqs: String
}

// Universities
{
	_id: ObjectID,
	name: String,
	classes: classTableID
}


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