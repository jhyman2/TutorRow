export default function (client) {
  return (req, res) => {
    const { course_id, user_id } = req.params;
    const query = 'DELETE FROM course_instances WHERE student_id=$1 AND course_id=$2 AND role=$3;';

    client.query(query, [user_id, course_id, 'tutor'], (err, result) => {
      res.sendStatus(200);
    });
  };
};