import React from 'react';

function Course_Card ({ course, select }) {
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
    </div>
  );
}

export default Course_Card;
