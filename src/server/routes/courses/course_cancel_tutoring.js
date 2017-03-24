export default function (client) {
  return (req, res) => {
    console.log('req.body', req.body);
    console.log('res.params', req.params);
    const query = 'DELETE FROM course_instances WHERE student_id=$1 AND course_id=$2 AND role=$3;';

    client.query(query, [req.params.user_id, req.params.course_id, 'tutor'], (err, result) => {
      res.sendStatus(200);
    });
  };
};