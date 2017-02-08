export default function (client) {
  return (req, res) => {
    const query = 'SELECT * FROM universities WHERE UPPER(name)=UPPER($1);';

    client.query(query, [req.params.name], (err, result) => {
      const query = 'SELECT * FROM users WHERE university_id=$1;'
      const university = result.rows[0];

      if (!err && university) {
        client.query(query, [university.id], (err, result) => {
          res.send(JSON.stringify(result.rows));
        });
      } else {
        res.send('No students for this university');
      }
    });
  };
};