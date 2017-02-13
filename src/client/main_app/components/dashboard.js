import React, { Component } from 'react';
import { connect }          from 'react-redux'

import Loading from './loading';
import Selected_Course from './selected_course';

import { fetchSelectedCourse, fetchCoursesForUni } from '../actions/';

class DashboardComponent extends Component {

  componentWillMount() {
    if (this.props.uni_courses && !this.props.uni_courses.length) {
      this.props.fetchCoursesForUni(this.props.user.university_id);
    }
  }

  openCourseInfo (course_id) {
    this.props.fetchSelectedCourse(this.props.user.university_name.trim(), course_id);
  }

  prepareCourses (courses) {
    const courseContainerStyles = {
      display: 'inline-block',
      padding: '10px',
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

    return courses.map((course) => {
      return (
        <div key={`${course.department}_${course.course_num}`} style={courseContainerStyles}>
          <p onClick={this.openCourseInfo.bind(this, course.id)} style={courseTitleStyles}>{course.department} {course.course_num} - {course.name}</p>
          <span style={courseDescripStyles}>Number of credits: {course.num_credits}</span>
          <span style={courseDescripStyles}>Professor: {course.professor}</span>
          <p>Course summary: {course.description}</p>
        </div>
      );
    });
  }

  render() {
    let toDisplay;

    if (this.props.loading) {
      toDisplay = <Loading />;
    } else if (this.props.selected_course) {
      toDisplay = <Selected_Course />;
    } else {
      toDisplay = <div>
                    <p>All courses at {this.props.user.university_name}:</p>
                    {this.prepareCourses(this.props.uni_courses)}
                  </div>
    }

    return (
      <div>
        {toDisplay}
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    user: state.user,
    loading: state.loading,
    uni_courses: state.uni_courses,
    selected_course: state.selected_course
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    fetchCoursesForUni (university_id) {
      dispatch(fetchCoursesForUni(university_id));
    },
    fetchSelectedCourse (university_name, course_id) {
      dispatch(fetchSelectedCourse(university_name, course_id));
    }
  }
}

const dashboard = connect(
  mapStateToProps,
  mapDispatchToProps
)(DashboardComponent)

export default dashboard;