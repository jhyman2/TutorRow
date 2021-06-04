import React, { useContext } from 'react';
import { useDispatch, useSelector } from 'react-redux'
import { useQuery, useMutation } from '@apollo/react-hooks';
import Course_Card from './course_card.tsx';
import TRButton from '../../controls/TRButton.tsx';

import SIGNUP_FOR_COURSE from '../graphql/mutations/signupForCourse.ts';
import DROP_COURSE from '../graphql/mutations/dropCourse.ts';
import GET_COURSE_DATA from '../graphql/queries/getCourseData.ts';

// using refetchQueries here will run the query used in dashboard to refetch the data needed there
const Selected_Course = ({ user }) => {
  const selected_course = useSelector(state => state.selected_course);
  const { loading, error, data } = useQuery(GET_COURSE_DATA, { variables: { id: selected_course.id }});
  const [dropCourse] = useMutation(DROP_COURSE,
    {
      refetchQueries: ['getUniversityCourses'],
      variables: { id: selected_course.id }
    }
  );
  const [signupForCourse] = useMutation(SIGNUP_FOR_COURSE,
    {
      refetchQueries: () => ['getUniversityCourses'],
      variables: { id: selected_course.id }
    }
  );
  const dispatch = useDispatch();

  if (loading) {
    return <div>loading...</div>;
  }

  if (error) {
    console.log('error', error);
    return <div>error</div>;
  }


  const { course } = data;
  const studentIsTakingClass = course.students.find(student => student.id === user.id);
  const studentIsTutoringClass = course.tutors.find(tutor => tutor.id === user.id);

  return (
    <>
      <TRButton
        className="mt-8 ml-8 my-1"
        onClick={() => dispatch({ type: 'SELECTED_COURSE_RESET' })}
      >
        Return to dashboard
      </TRButton>
      <div className="mx-auto flex-col container justify-center h-screen flex items-center p-5">
        <Course_Card
          allowStudentRemoveSignup={studentIsTakingClass}
          allowStudentSignup={!studentIsTakingClass && !studentIsTutoringClass}
          allowTutorRemoveSignup={studentIsTutoringClass}
          allowTutorSignup={!studentIsTutoringClass && !studentIsTakingClass}
          course={course}
          onStudentAction={() => {
            if (studentIsTakingClass) {
              return dropCourse();
            }
            signupForCourse({ variables: { role: 'student' }});
          }}
          onTutorAction={() => {
            if (studentIsTutoringClass) {
              return dropCourse();
            }
            signupForCourse({ variables: { role: 'tutor' }});
          }}
          showStudents
          showTutors
          students={course.students}
          tutors={course.tutors}
        />
      </div>
    </>
  );
}

export default Selected_Course;
