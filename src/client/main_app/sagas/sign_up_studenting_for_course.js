import { call, put, takeEvery, takeLatest } from 'redux-saga/effects'

const signUpStudentingPromise = function (user_id, course_id) {
  return fetch(`/courses/signup/studenting/${course_id}`, {
    method: 'POST'
  })
    .then((res) => {
      return res.json();
    });
};

function* signUpStudenting (action) {
   try {
      const courses = yield call(signUpStudentingPromise, action.user_id, action.course_id);
      yield put({type: "SIGNUP_STUDENTING_FOR_COURSE_SUCCEEDED", user_id: action.user_id, course_id: action.course_id});
   } catch (e) {
      console.log(e);
      yield put({type: "SIGNUP_STUDENTING_FOR_COURSE_FAILED", message: e.message});
   }
}

function* sign_up_studenting_saga () {
  yield takeEvery("SIGNUP_STUDENTING_FOR_COURSE_REQUESTED", signUpStudenting);
}

export default sign_up_studenting_saga;