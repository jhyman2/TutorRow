export default function (client) {
  return (req, res) => {
    const checkQuery = 'SELECT * FROM course_instances WHERE student_id=$1 AND course_id=$2 AND role=$3;';

    // check if this user is already a student in this course
    client.query(checkQuery, [req.params.user_id, req.params.course_id, 'tutor'], (err, results) => {
      if (results.length) {
        res.status(500).send('User already signed up as tutor for this course');
      } else {
        const query = 'INSERT INTO course_instances(student_id, course_id, role) VALUES ($1, $2, $3);';

        client.query(query, [req.params.user_id, req.params.course_id, 'tutor'], (err, results) => {
          res.sendStatus(200);
        });
      }
    });
  };
};