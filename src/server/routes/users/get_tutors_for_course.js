// todo eventually: add in university authentication (university_id or university_name)

export default function (client) {
  return (req, res) => {
    const query = `
                  SELECT full_name, student_id
                  FROM course_instances
                  JOIN users
                    ON course_instances.student_id=users.id
                  WHERE course_id=$1
                    AND role='tutor'
                  `;

    client.query(query, [req.params.course_id], (err, result) => {
        res.send(JSON.stringify(result.rows));
    });
  };
};