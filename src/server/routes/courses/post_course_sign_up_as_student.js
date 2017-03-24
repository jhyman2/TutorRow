export default function (client) {
  return (req, res) => {
    const query = 'INSERT INTO course_instances(student_id, course_id, role) VALUES ($1, $2, $3);';
    client.query(query, [req.params.user_id, req.params.course_id, 'student'], (err, result) => {
      res.sendStatus(200);
    });
  };
};