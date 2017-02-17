import React, { Component } from 'react';

export default class Course_Card extends Component {

  handleClick (course_id) {
    if (this.props.select) {
      this.props.select(course_id);
    }
  }

  render () {
    const course = this.props.course;

    const courseContainerStyles = {
      display: 'inline-block',
      padding: '10px',
      margin: '10px',
      border: '1px solid lightgrey'
    };

    const courseTitleStyles = {
      margin: '0 0 10px 0'
    };

    const courseDescripStyles = {
      display: 'block',
      marginLeft: '20px'
    };

    const summaryStyles = {
      maxWidth: '300px'
    };

    return (
      <div
        key={`${course.department}_${course.course_num}`}
        style={courseContainerStyles}
        onClick={this.handleClick.bind(this, course.id)}
      >
        <p style={courseTitleStyles}>{course.department} {course.course_num} - {course.name}</p>
        <span style={courseDescripStyles}>Number of credits: {course.num_credits}</span>
        <span style={courseDescripStyles}>Professor: {course.professor}</span>
        <p>Course summary: {course.description}</p>
      </div>
    );
  }
}