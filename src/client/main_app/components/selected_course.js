import React, { Component } from 'react';
import { connect }          from 'react-redux'

// actions
import { fetchStudentsForCourse, fetchTutorsForCourse } from '../actions/';

// components
import Course_Card from './course_card';

class Selected_Course_Component extends Component {

  componentWillMount() {
    this.props.fetchStudentsForCourse(this.props.selected_course.id);
    this.props.fetchTutorsForCourse(this.props.selected_course.id);
  }

  prepareTutors (tutors) {
    let ui_tutors = [];

    tutors.forEach((tutor) => {
      ui_tutors.push(<p key={tutor.student_id}>{tutor.full_name}</p>);
    });

    return ui_tutors;
  }

  prepareStudents (students) {
    const ui_students = [];

    students.forEach((student) => {
      ui_students.push(<p key={student.student_id}>{student.full_name}</p>);
    });

    return ui_students;
  }

  render() {
    const course = this.props.selected_course;

    return (
      <div>
        <Course_Card course={course} />
        <p>Available tutors:</p>
        {this.prepareTutors(this.props.tutors)}
        <p>Students wanting to learn:</p>
        {this.prepareStudents(this.props.students)}
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    user: state.user,
    loading: state.loading,
    selected_course: state.selected_course,
    students: state.students,
    tutors: state.tutors
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    fetchStudentsForCourse (course_id) {
      dispatch(fetchStudentsForCourse(course_id));
    },
    fetchTutorsForCourse (course_id) {
      dispatch(fetchTutorsForCourse(course_id));
    }
  }
}

const selected_course = connect(
  mapStateToProps,
  mapDispatchToProps
)(Selected_Course_Component)

export default selected_course;


/**
 * @todo
 * Add in functionality for this page (add it to user's courses for tutoring, learning)
 * Display all course information
 * Add back button (might need react router)
 * Have a little icon indicating if there are tutors/students available
 */