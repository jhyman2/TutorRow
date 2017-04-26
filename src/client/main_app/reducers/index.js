import { combineReducers } from 'redux'

const user = (state = '', action) => {
  switch (action.type) {
    case 'CHANGE_USER':
      return state;
    case 'USER_UPDATE_WITH_UNI_REQUESTED':
      return state;
    case 'USER_UPDATE_WITH_UNI_SUCCEEDED':
      return action.user;
    case 'USER_UPDATE_WITH_UNI_FAILED':
      return -1;
    default:
      return state;
  }
}

const universities = (state = [], action) => {
  switch (action.type) {
    case 'UNI_FETCH_REQUESTED':
      return ['FETCHING'];
    case 'UNI_FETCH_SUCCEEDED':
      return action.universities;
    case 'UNI_FETCH_FAILED':
      return ['Err fetching'];
    default:
      return state;
  }
}

const loading = (state = false, action) => {
  switch (action.type) {
    case 'FETCH_ALL_UNI_COURSES_REQUESTED':
    case 'UNI_FETCH_REQUESTED':
    case 'FETCH_SELECTED_COURSE_REQUESTED':
      return true;
    case 'ALL_COURSES_FETCH_SUCCEEDED':
    case 'ALL_COURSES_FETCH_FAILED':
    case 'UNI_FETCH_SUCCEEDED':
    case 'UNI_FETCH_FAILED':
    case 'FETCH_SELECTED_COURSE_SUCCEEDED':
    case 'FETCH_SELECTED_COURSE_FAILED':
      return false;
    default:
      return state;
  }
}

const uni_courses = (state = [], action) => {
  switch (action.type) {
    case 'FETCH_ALL_UNI_COURSES_REQUESTED':
      return [];
    case 'ALL_COURSES_FETCH_SUCCEEDED':
      return action.courses;
    case 'ALL_COURSES_FETCH_FAILED':
      return ['Err fetching'];
    default:
      return state;
  }
}

const selected_course = (state = null, action) => {
  switch (action.type) {
    case 'FETCH_SELECTED_COURSE_REQUESTED':
      return null;
    case 'FETCH_SELECTED_COURSE_SUCCEEDED':
      return action.course;
    case 'FETCH_SELECTED_COURSE_FAILED':
      return {err: 'error fetching course'};
    default:
      return state;
  }
}

const students = (state = [], action) => {
  switch (action.type) {
    case 'FETCH_STUDENTS_FOR_COURSE_REQUESTED':
      return [];
    case 'STUDENTS_FOR_COURSE_FETCH_SUCCEEDED':
      return action.students;
    case 'SIGNUP_STUDENTING_FOR_COURSE_SUCCEEDED':
      // for now, this will take a user.id and assign it to a student_id to match comparisons between users and course_instances
      let user = Object.assign({}, action.user_id);
      user.student_id = user.id;

      return state.concat(user);
    case 'CANCEL_STUDENTING_FOR_COURSE_SUCCEEDED':
      const index = state.findIndex((student) => student.student_id === action.user_id.id);

      if (index > -1) {
        return state.slice(0, index).concat(state.slice(index + 1, state.length));
      }

      return state;
    case 'STUDENTS_FOR_COURSE_FETCH_FAILED':
      return ['Err fetching'];
    default:
      return state;
  }
}

const tutors = (state = [], action) => {
  switch (action.type) {
    case 'FETCH_TUTORS_FOR_COURSE_REQUESTED':
      return [];
    case 'TUTORS_FOR_COURSE_FETCH_SUCCEEDED':
      return action.tutors;
    case 'SIGNUP_TUTORING_FOR_COURSE_SUCCEEDED':
      // for now, this will take a user.id and assign it to a student_id to match comparisons between users and course_instances
      let user = Object.assign({}, action.user_id);
      user.student_id = user.id;

      return state.concat(user);
    case 'CANCEL_TUTORING_FOR_COURSE_SUCCEEDED':
      const index = state.findIndex((student) => student.student_id === action.user_id.id);
      if (index > -1) {
        return state.slice(0, index).concat(state.slice(index + 1, state.length));
      }

      return state;
    case 'TUTORS_FOR_COURSE_FETCH_FAILED':
      return ['Err fetching'];
    default:
      return state;
  }
}

const reducers = combineReducers({
  user,
  universities,
  uni_courses,
  loading,
  selected_course,
  students,
  tutors
});

export default reducers;