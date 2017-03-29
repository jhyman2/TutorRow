import React           from 'react';
import MainAppTemplate from '../views/components/main_app/template';

export default function (client) {
  return (req, res) => {
    client.query('SELECT * FROM users WHERE facebook_id=$1;', [req.user], (err, result) => {
      const user = result ? result.rows[0] : null;

        if (user && user.university_id) {
          client.query('SELECT * FROM universities WHERE id=$1;', [user.university_id], (err, result) => {
            user.university_name = result.rows[0].name;

            const initialState = { user };
            res.send(MainAppTemplate({
              title: 'Main App',
              initialState: JSON.stringify(initialState)
            }));
          });
        } else {
          const initialState = { user };
          res.send(MainAppTemplate({
            title: 'Main App',
            initialState: JSON.stringify(initialState)
          }));
        }
    });
  };
};