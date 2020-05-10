import React, { useContext } from 'react';
import { useDispatch, useSelector } from 'react-redux'
import { useQuery, useMutation } from '@apollo/react-hooks';
import Course_Card from './course_card';

import signupForCourse from '../graphql/mutations/signupForCourse.ts';
import dropCourse from '../graphql/mutations/dropCourse.ts';
import getCourseData from '../graphql/queries/getCourseData.ts';

import UserContext from '../contexts/user.ts';

const Selected_Course = () => {
  const selected_course = useSelector(state => state.selected_course);
  const { loading, error, data } = useQuery(getCourseData, { variables: { id: selected_course.id }});
  const [cancelCourse] = useMutation(dropCourse, { variables: { id: selected_course.id }});
  const [signupCourse] = useMutation(signupForCourse, { variables: { id: selected_course.id }});
  const user = useContext(UserContext);
  const dispatch = useDispatch();

  if (loading) {
    return <div>loading...</div>;
  }

  if (error) {
    return <div>error</div>;
  }

  const { course } = data;

  const studentIsTakingClass = course.students.find(student => student.id === user.id);
  const studentIsTutoringClass = course.tutors.find(tutor => tutor.id === user.id);

  return (
    <>
      <button
        className="mt-8 ml-8 bg-white hover:bg-gray-100 text-gray-800 font-semibold py-2 px-4 border border-gray-400 rounded shadow my-1"
        onClick={() => dispatch({ type: 'SELECTED_COURSE_RESET'})}
      >
        Return to dashboard
      </button>
      <div className="mx-auto flex-col container justify-center h-screen flex items-center p-5">
        <Course_Card
          allowStudentRemoveSignup={studentIsTakingClass}
          allowStudentSignup={!studentIsTakingClass && !studentIsTutoringClass}
          allowTutorRemoveSignup={studentIsTutoringClass}
          allowTutorSignup={!studentIsTutoringClass && !studentIsTakingClass}
          course={course}
          onStudentAction={() => {
            if (studentIsTakingClass) {
              return cancelCourse();
            }
            signupCourse({ variables: { role: 'student' }});
          }}
          onTutorAction={() => {
            if (studentIsTutoringClass) {
              return cancelCourse();
            }
            signupCourse({ variables: { role: 'tutor' }});
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

/**
 * @todo
 * Add back button (might need react router)
 * Have a little icon indicating if there are tutors/students available
 */