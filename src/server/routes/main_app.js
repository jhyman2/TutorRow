import MainAppTemplate from '../views/components/main_app/template';

const title = 'TutorRow';

export default function (client) {
  return async (req, res) => {
    const { rows: users } = await client.query('SELECT * FROM users WHERE facebook_id=$1;', [req.user]);
    const user = users[0];
    res.send(MainAppTemplate({
      title,
      initialState: user.id
    }));
  };
};