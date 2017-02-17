import axios from 'axios';
import { call, put, takeEvery, takeLatest } from 'redux-saga/effects'

const postUniPromise = function (user_id, university_id) {
  return axios.post('/user/university', {
    user_id,
    university_id
  }).then((res) => {
    return res.data;
  });
};

// worker Saga: will be fired on USER_FETCH_REQUESTED actions
function* postUserUni(action) {
   try {
      const user = yield call(postUniPromise, action.user_id, action.university_id);
      yield put({type: "USER_UPDATE_WITH_UNI_SUCCEEDED", user: user});
   } catch (e) {
      console.log(e);
      yield put({type: "USER_UPDATE_WITH_UNI_FAILED", message: e.message});
   }
}

/*
  Starts fetchUser on each dispatched `UNI_FETCH_REQUESTED` action.
  Allows concurrent fetches of user.
*/
function* user_post_saga() {
  yield takeEvery("USER_UPDATE_WITH_UNI_REQUESTED", postUserUni);
}

export default user_post_saga;