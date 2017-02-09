export default function (client) {
  return (req, res) => {
    const query    = 'UPDATE users SET university_id=$1 WHERE id=$2;';
    const dbParams = [req.body.university_id, req.body.user_id];

    client.query(query, dbParams, (err, result) => {
      client.query('SELECT * FROM users WHERE id=$1', [req.body.user_id], (err, result) => {
        res.send(JSON.stringify(result.rows[0]));
      })
    });
  };
};