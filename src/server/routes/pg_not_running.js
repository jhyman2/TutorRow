import ErrorTemplate from '../views/components/error/template';

const ERROR = 'Error 500 - Database is not running';
const INSTRUCTIONS = 'Please start PGAdmin and the tutorrow database. Then, restart the server and refresh this page.';

export default function () {
  return (req, res) => {
    res.send(ErrorTemplate({
      title: 'Database not running',
      initialState: {
        error: ERROR,
        instructions: INSTRUCTIONS,
      },
    }));
  }
};
