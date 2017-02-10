export const fetchUnis = () => {
  return {
    type: 'UNI_FETCH_REQUESTED'
  };
}

export const updateUserWithUni = (user_id, university_id) => {
  return {
    type: 'USER_UPDATE_WITH_UNI_REQUESTED',
    user_id,
    university_id
  };
}

export const fetchCoursesForUni = (university_id) => {
  return {
    type: 'FETCH_ALL_UNI_COURSES_REQUESTED',
    university_id
  };
}