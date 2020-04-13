import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useQuery } from '@apollo/react-hooks';
import { gql } from 'apollo-boost';

import Loading   from './components/loading';
import SelectUni from './components/selectUni';
import Dashboard from './components/dashboard';

import { updateUserWithUni, fetchUnis } from './actions';

type University = {
  id: Number
  name: String
}

type User = {
  university_id: Number
}

interface Root_State {
  loading: Boolean
  fetchUnis: Function
  user: User
  universities: [University]
  updateUserWithUni: Function
}

interface Main_App_Props {
  loading: Boolean
  fetchUnis: Function
  user: User
  universities: [University]
  updateUserWithUni: Function
}

const UNIVERSITIES = gql`
  {
    universities {
      name
      id
    }
  }
`;

const Main_App = (props:Main_App_Props) => {
  const { user } = useSelector((state: Root_State) => ({ user: state.user }));
  const { loading, error, data } = useQuery(UNIVERSITIES);
  const dispatch = useDispatch();

  if (loading) {
    return <Loading />;
  }

  if (user && !user.university_id) {
    return (
      <SelectUni
        user={user}
        unis={data.universities}
        updateUserWithUni={(id: number, university_id: number) => {
          dispatch(updateUserWithUni(id, university_id));
        }}
      />
    );
  }

  if (!user) {
    return <p>Please go back and log in until we figure out how to store cookies</p>;
  }

  return <Dashboard />;
};

export default Main_App;