import React, { Component } from 'react';
import { connect }          from 'react-redux'

class Selected_Course_Component extends Component {
  render() {
    const selected_course = this.props.selected_course;

    return (
      <div>
        {selected_course.department} - {selected_course.course_num}
        {selected_course.name}
        {selected_course.description}
        {selected_course.professor}
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    user: state.user,
    loading: state.loading,
    selected_course: state.selected_course
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
  }
}

const selected_course = connect(
  mapStateToProps,
  mapDispatchToProps
)(Selected_Course_Component)

export default selected_course;