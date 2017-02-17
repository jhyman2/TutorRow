export default function (client) {
  return (req, res) => {
    const query = 'SELECT * FROM course_instances WHERE course_id=$1 AND role=\'student\';';

    client.query(query, [req.params.course_id], (err, result) => {
        res.send(JSON.stringify(result.rows));
    });
  };
};