import React, { useContext } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useMutation, useQuery } from '@apollo/react-hooks';

import Loading         from './loading';
import Course_Card     from './course_card';
import Selected_Course from './selected_course';

import { fetchSelectedCourse } from '../actions/';

import GET_UNIVERSITY_COURSES from '../graphql/queries/getUniversityCourses.ts';
import UNENROLL_STUDENT_FROM_UNIVERSITY from '../graphql/mutations/unenrollStudentFromUniversity.ts';

export default function DashboardComponent() {
  const selected_course = useSelector(state => state.selected_course);
  const dispatch = useDispatch();
  const [unenroll] = useMutation(UNENROLL_STUDENT_FROM_UNIVERSITY,
    {
      refetchQueries: () => ['getUserUniversity']
    }
  );
  const { loading, error, data } = useQuery(GET_UNIVERSITY_COURSES);

  if (loading) {
    return <Loading />;
  }

  if (error) {
    return <div>An error has occurred: {error}</div>;
  }

  if (selected_course) {
    return <Selected_Course user={data.user} />;
  }

  return (
    <div className="font-semibold text-lg pt-8">
      <p className="ml-8">TutorRow Dashboard</p>
      <div className="flex flex-row mx-auto container justify-around h-screen pt-8">
        <div>
          <p>All courses at {data.user.university.name}:</p>
          {data.user.university.courses.map((course) => (
            <Course_Card
              key={course.id}
              course={course}
              select={course_id => dispatch(fetchSelectedCourse(data.user.university.name, course_id))}
            />
          ))}
        </div>
        <div>
        <button
          className="bg-white hover:bg-gray-100 text-gray-800 font-normal text-base py-2 px-4 border border-gray-400 rounded shadow"
          onClick={unenroll}
        >
          {`Withdraw from ${data.user.university.name}`}
        </button>
          <p className="py-2">My courses that I am enrolled in:</p>
          <div className="pt-8">
            <p>Courses I am Tutoring</p>
            {data.user.coursesTutoring.map(({ course_num, department, id, name}) => (
              <p className="pl-8" key={id}>{`${department} ${course_num} -- ${name}`}</p>
            ))}
          </div>
          <div className="pt-8">
            <p>Courses I am Studenting</p>
            {data.user.coursesStudenting.map(({ course_num, department, id, name}) => (
              <p className="pl-8" key={id}>{`${department} ${course_num} -- ${name}`}</p>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
