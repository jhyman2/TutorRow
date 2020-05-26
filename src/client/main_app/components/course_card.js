import React from 'react';
import studentImg from '../../zondicons/user.svg';
import tutorImg from '../../zondicons/badge.svg';
import TRButton from '../../controls/TRButton.tsx';

function Course_Card ({
  allowStudentSignup = false,
  allowStudentRemoveSignup = false,
  allowTutorSignup = false,
  allowTutorRemoveSignup = false,
  course,
  onStudentAction,
  onTutorAction,
  select = () => {},
  showStudents = false,
  showTutors = false,
  students = [],
  tutors = [],
}) {

  const Students = () => {
    if (!students.length) {
      return <p>No students signed up</p>;
    }

    return students.map(student => (
      <p key={student.student_id || student.id}>
        {student.full_name}
      </p>
    ));
  };

  const Tutors = () => {
    if (!tutors.length) {
      return <p>No tutors signed up</p>;
    }

    return tutors.map(tutor => (
      <p key={tutor.student_id || tutor.id}>
        {tutor.full_name}
      </p>
    ));
  }

  return (
    <div className="max-w-sm rounded overflow-hidden shadow-lg" onClick={() => select(course.id)}>
      <div className="px-6 py-4">
        <div className="font-bold text-xl mb-2">
          {`${course.department} ${course.course_num} - ${course.name}`}
        </div>
        <div className="text-gray-700 text-base">
          <p>{course.description}</p>
          <p>{course.num_credits} credits</p>
          <p className="text-gray-900 leading-none">{course.professor}</p>
        </div>
      </div>
      {showTutors && (
        <div className="flex flex-row items-center px-6 py-1">
          <img className="h-4 w-4" src={tutorImg} />
          <Tutors />
          {(allowTutorSignup || allowTutorRemoveSignup) && (
            <TRButton className="ml-auto" onClick={onTutorAction}>
              {allowTutorSignup && 'Sign up to tutor!'}
              {allowTutorRemoveSignup && 'I dont want to tutor'}
            </TRButton>
          )}
        </div>
      )}
      {showStudents && (
        <div className="flex flex-row items-center px-6 py-1">
          <img className="h-4 w-4" src={studentImg} />
          <Students />
          {(allowStudentSignup || allowStudentRemoveSignup) && (
            <TRButton className="ml-auto" onClick={onStudentAction}>
              {allowStudentSignup && 'Tutor me!'}
              {allowStudentRemoveSignup && 'I dont need tutoring'}
            </TRButton>
          )}
        </div>
      )}
    </div>
  );
}

export default Course_Card;
