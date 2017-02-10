export default function (client) {
  return (req, res) => {
    const query = 'SELECT * FROM courses WHERE university_id=$1;';

    client.query(query, [req.params.university_id], (err, result) => {
        res.send(JSON.stringify(result.rows));
    });
  };
};