import React from 'react';

const styles = {
  courseContainer: {
    display: 'inline-block',
    padding: '10px',
    margin: '10px',
    border: '1px solid lightgrey',
  },
  courseDescription: {
    display: 'block',
    marginLeft: '20px',
  },
  courseTitle: {
    margin: '0 0 10px 0', 
  },
}

function Course_Card ({ course, select }) {
  return (
    <div
      key={`${course.department}_${course.course_num}`}
      style={styles.courseContainer}
      onClick={() => select(course.id)}
    >
      <p style={styles.courseTitle}>
        {course.department} {course.course_num} - {course.name}
      </p>
      <span style={styles.courseDescription}>
        Number of credits: {course.num_credits}
      </span>
      <span style={styles.courseDescription}>
        Professor: {course.professor}
      </span>
      <p>
        Course summary: {course.description}
      </p>
    </div>
  );
}

export default Course_Card;
