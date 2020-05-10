import React, { useContext } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useQuery } from '@apollo/react-hooks';

import Loading         from './loading';
import Course_Card     from './course_card';
import Selected_Course from './selected_course';

import UserContext from '../contexts/user.ts';
import { fetchSelectedCourse } from '../actions/';

import getUniversityCourses from '../graphql/queries/getUniversityCourses.ts';

export default function DashboardComponent() {
  const selected_course = useSelector(state => state.selected_course);
  const dispatch = useDispatch();
  const user = useContext(UserContext);
  const { loading, error, data } = useQuery(getUniversityCourses, {
    variables: { id: user.university.id },
  });

  if (loading) {
    return <Loading />;
  }

  if (error) {
    return <div>An error has occurred: {error}</div>;
  }

  if (selected_course) {
    return <Selected_Course />;
  }

  return (
    <div className="font-semibold text-lg pt-8">
      <p className="ml-8">TutorRow Dashboard</p>
      <div className="flex flex-row mx-auto container justify-around h-screen pt-8">
        <div>
          <p>All courses at {user.university.name}:</p>
          {data.university.courses.map((course) => (
            <Course_Card
              key={course.id}
              course={course}
              select={course_id => dispatch(fetchSelectedCourse(user.university.name, course_id))}
            />
          ))}
        </div>
        <div>
          <p>My courses that I am enrolled in:</p>
          <div className="pt-8">todo: build this part</div>
        </div>
      </div>
    </div>
  );
}
