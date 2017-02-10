import React, { Component } from 'react';
import { connect } from 'react-redux'

import Loading from './loading';

import { updateUserWithUni, fetchCoursesForUni } from '../actions/';

class DashboardComponent extends Component {

  componentWillMount() {
    if (this.props.uni_courses && !this.props.uni_courses.length) {
      this.props.fetchCoursesForUni(this.props.user.university_id);
    }
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
          <p style={courseTitleStyles}>{course.department} {course.course_num} - {course.name}</p>
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
    uni_courses: state.uni_courses
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    fetchCoursesForUni (university_id) {
      dispatch(fetchCoursesForUni(university_id));
    },
    updateUserWithUni (user_id, university_id) {
      dispatch(updateUserWithUni(user_id, university_id));
    }
  }
}

const dashboard = connect(
  mapStateToProps,
  mapDispatchToProps
)(DashboardComponent)

export default dashboard;