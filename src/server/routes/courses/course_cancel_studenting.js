export default function (client) {
  return (req, res) => {
    const query = 'DELETE FROM course_instances WHERE student_id=$1 AND course_id=$2 AND role=$3;';

    client.query(query, [req.body.student_id, req.params.course_id, 'student'], (err, result) => {
      res.sendStatus(200);
    });
  };
};