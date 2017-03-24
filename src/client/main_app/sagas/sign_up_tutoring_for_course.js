import { call, put, takeEvery, takeLatest } from 'redux-saga/effects'

const signUpTutoringPromise = function (user_id, course_id) {
  return fetch(`/course/signup/${user_id.id}/tutoring/${course_id}`, {
    method: 'POST'
  })
};

function* signUpTutoring (action) {
   try {
      const courses = yield call(signUpTutoringPromise, action.user_id, action.course_id);
      yield put({type: "SIGNUP_TUTORING_FOR_COURSE_SUCCEEDED", user_id: action.user_id, course_id: action.course_id});
   } catch (e) {
      console.log(e);
      yield put({type: "SIGNUP_TUTORING_FOR_COURSE_FAILED", message: e.message});
   }
}

function* sign_up_tutoring_saga () {
  yield takeEvery("SIGNUP_TUTORING_FOR_COURSE_REQUESTED", signUpTutoring);
}

export default sign_up_tutoring_saga;