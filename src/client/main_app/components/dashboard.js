import React, { Component } from 'react';
import { connect }          from 'react-redux'

import Loading from './loading';
import Course_Card     from './course_card';
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
    return courses.map((course) => {
      return <Course_Card key={course.id} course={course} select={this.openCourseInfo.bind(this)} />
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