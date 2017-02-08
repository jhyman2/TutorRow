export default function (client) {
  return (req, res) => {
    const query = 'SELECT * FROM universities;';

    client.query(query, (err, result) => {
        res.send(JSON.stringify(result.rows));
    });
  };
};