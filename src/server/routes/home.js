import React              from 'react';
import { renderToString } from 'react-dom/server';

import Login         from '../views/components/login/index';
import LoginTemplate from '../views/components/login/template';

export default function (client) {
  return (req, res) => {
    client.query('SELECT * FROM users;', (err, result) => {
      const users        = result.rows;
      const initialState = { user: users[0] };
      const appString    = renderToString(<Login {...initialState} />);

      res.send(LoginTemplate({
        body: appString,
        title: 'Hello World from the server',
        initialState: JSON.stringify(initialState)
      }));
    });
  };
};