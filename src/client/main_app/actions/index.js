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