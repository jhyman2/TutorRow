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
