import React from 'react';
import { useQuery } from '@apollo/react-hooks';
// @ts-ignore
import GET_USER_UNIVERSITY from 'graphql/queries/getUserUniversity.ts';

import SelectUni from 'components/selectUni';
import Dashboard from 'components/dashboard';
import Loading from 'components/loading';

const Main_App = () => {
  const { loading, error, data } = useQuery(GET_USER_UNIVERSITY);

  if (loading) return <Loading />;
  if (error) return <div>error</div>;

  if (data.user && !data.user.university) {
    return <SelectUni user={data.user} />;
  }

  if (!data.user) {
    return <p>Please go back and log in until we figure out how to store cookies</p>;
  }

  return <Dashboard />;
};

export default Main_App;