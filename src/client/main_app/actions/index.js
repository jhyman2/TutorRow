export const fetchUnis = () => {
  return {
    type: 'UNI_FETCH_REQUESTED'
  };
};

export const updateUserWithUni = (user_id, university_id) => {
  return {
    type: 'USER_UPDATE_WITH_UNI_REQUESTED',
    user_id,
    university_id
  };
};

export const fetchCoursesForUni = (university_id) => {
  return {
    type: 'FETCH_ALL_UNI_COURSES_REQUESTED',
    university_id
  };
};

export const fetchSelectedCourse = (university_name, course_id) => {
  return {
    type: 'FETCH_SELECTED_COURSE_REQUESTED',
    university_name,
    course_id
  }
};

export const fetchStudentsForCourse = (course_id) => {
  return {
    type: 'FETCH_STUDENTS_FOR_COURSE_REQUESTED',
    course_id
  }
};

export const fetchTutorsForCourse = (course_id) => {
  return {
    type: 'FETCH_TUTORS_FOR_COURSE_REQUESTED',
    course_id
  }
};

export const cancelStudenting = (user_id, course_id) => {
  return {
    type: 'CANCEL_STUDENTING_FOR_COURSE_REQUESTED',
    course_id,
    user_id
  };
};

export const cancelTutoring = (user_id, course_id) => {
  return {
    type: 'CANCEL_TUTORING_FOR_COURSE_REQUESTED',
    course_id,
    user_id
  };
};

export const signUpStudenting = (user_id, course_id) => {
  return {
    type: 'SIGNUP_STUDENTING_FOR_COURSE_REQUESTED',
    course_id,
    user_id
  };
};

export const signUpTutoring = (user_id, course_id) => {
  return {
    type: 'SIGNUP_TUTORING_FOR_COURSE_REQUESTED',
    course_id,
    user_id
  };
}