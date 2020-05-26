import React, { useRef } from 'react';
import { useMutation, useQuery } from '@apollo/react-hooks';
import GET_UNIVERSITIES from '../graphql/queries/getUniversities.ts';
import ENROLL_STUDENT_IN_UNIVERSITY from '../graphql/mutations/enrollStudentInUniversity.ts';
import Loading from '../components/loading';
import TRButton from '../../controls/TRButton.tsx';

const SelectUni = ({ user }) => {
  const selectRef = useRef(null);
  const { loading, error, data } = useQuery(GET_UNIVERSITIES);
  const [enrollStudentInUniversity] = useMutation(ENROLL_STUDENT_IN_UNIVERSITY);

  if (loading) {
    return <Loading />;
  }

  if (error) {
    return <div>error</div>;
  }

  return (
    <div className="mx-auto container justify-center h-screen flex items-center">
      <div className="mx-auto max-w-m mx-auto flex-col p-6 bg-white rounded-lg shadow-xl">
        <h1 className="text-center text-2xl mb-4">Welcome to TutorRow, {user.full_name}</h1>
        <p className="text-center">Please select your university</p>
        <div className="text-center">
          <select ref={selectRef} className="block appearance-none w-full bg-gray-200 border border-gray-200 text-gray-700 py-3 px-4 pr-8 rounded leading-tight focus:outline-none focus:bg-white focus:border-gray-500">
            {data.universities.map(uni => (
              <option key={`${uni.id}`} value={`${uni.id}`}>
                {uni.name}
              </option>
            ))}
          </select>
          <TRButton
            className="ml-auto my-1"
            onClick={() => {
              const university_id = selectRef.current[selectRef.current.selectedIndex].value;
              enrollStudentInUniversity({ variables: { id: parseInt(university_id) }});
            }}
          >
            Go!
          </TRButton>
        </div>
      </div>
    </div>
  );
};

export default SelectUni;
