import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useQuery } from '@apollo/react-hooks';

import Loading from './loading';
import Course_Card     from './course_card';
import Selected_Course from './selected_course';

import { fetchSelectedCourse } from '../actions/';

import getUniversityCourses from '../graphql/queries/getUniversityCourses.ts';

export default function DashboardComponent() {
  const {
    selected_course,
    user,
  } = useSelector(state => ({
    user: state.user,
    selected_course: state.selected_course,
  }));
  const dispatch = useDispatch();
  const { loading, error, data } = useQuery(getUniversityCourses, { variables: { id: user.university_id }});

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
    <div>
      <p>All courses at {user.university_name}:</p>
      {data.university.courses.map((course) => (
        <Course_Card
          key={course.id}
          course={course}
          select={course_id => dispatch(fetchSelectedCourse(user.university_name.trim(), course_id))}
        />
      ))}
    </div>
  );
}
