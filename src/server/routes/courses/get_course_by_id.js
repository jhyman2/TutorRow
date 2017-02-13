export default function (client) {
  return (req, res) => {
    const query = 'SELECT * FROM universities WHERE UPPER(name)=UPPER($1);';
    client.query(query, [req.params.university_name], (err, result) => {
      const query = 'SELECT * FROM courses WHERE university_id=$1 AND id=$2;'

      client.query(query, [result.rows[0].id, req.params.course_id], (err, result) => {
        res.send(JSON.stringify(result.rows[0]));
      });
    });
  };
};