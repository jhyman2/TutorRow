import React from 'react';
import { useQuery } from '@apollo/react-hooks';
// @ts-ignore
import GET_USER from './graphql/queries/getUser.ts';
// @ts-ignore
import userQueryVariables from './graphql/userQueryVariables.ts';

import SelectUni from './components/selectUni';
import Dashboard from './components/dashboard';
import Loading from './components/loading';

// @ts-ignore
import UserContext from './contexts/user.ts';

const Main_App = () => {
  const { loading, error, data } = useQuery(GET_USER, userQueryVariables);

  if (loading) {
    return <Loading />;
  }

  if (error) {
    return <div>error</div>;
  }

  if (data.student && !data.student.university) {
    return <SelectUni user={data.student} />;
  }

  if (!data.student) {
    return <p>Please go back and log in until we figure out how to store cookies</p>;
  }

  return (
    <UserContext.Provider value={data.student}>
      <Dashboard />
    </UserContext.Provider>
  );
};

export default Main_App;