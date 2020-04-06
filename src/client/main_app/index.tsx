import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';

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

const Main_App = (props:Main_App_Props) => {
  const {
    loading,
    user,
    universities,
  } = useSelector((state: Root_State) => ({
    loading: state.loading,
    user: state.user,
    universities: state.universities,
  }));
  const dispatch = useDispatch();

  useEffect(() => {
    if (!user.university_id) {
      dispatch(fetchUnis());
    }
  });

  if (loading) {
    return <Loading />;
  }

  if (user && !user.university_id) {
    return (
      <SelectUni
        user={user}
        unis={universities}
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