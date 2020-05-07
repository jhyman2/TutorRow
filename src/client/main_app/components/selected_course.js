import React from 'react';
import { connect } from 'react-redux'
import { useQuery, useMutation } from '@apollo/react-hooks';
import Course_Card from './course_card';

import signupForCourse from '../graphql/mutations/signupForCourse.ts';
import dropCourse from '../graphql/mutations/dropCourse.ts';
import getCourseData from '../graphql/queries/getCourseData.ts';

const Selected_Course = ({ selected_course, user }) => {
  const { loading, error, data } = useQuery(getCourseData, { variables: { id: selected_course.id }});
  const [cancelCourse] = useMutation(dropCourse, { variables: { id: selected_course.id }});
  const [signupCourse] = useMutation(signupForCourse, { variables: { id: selected_course.id }});

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
  );
}

const mapStateToProps = (state) => ({
  user: state.user,
  selected_course: state.selected_course,
});

const Connected_Selected_Course = connect(mapStateToProps)(Selected_Course);

export default Connected_Selected_Course;

/**
 * @todo
 * Add back button (might need react router)
 * Have a little icon indicating if there are tutors/students available
 */