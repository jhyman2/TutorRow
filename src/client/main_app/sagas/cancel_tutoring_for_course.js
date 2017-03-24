import { call, put, takeEvery, takeLatest } from 'redux-saga/effects'

const cancelTutoringPromise = function (user_id, course_id) {
  console.log('hmmm', user_id, course_id);
  return fetch(`/course/cancel/${user_id.id}/tutoring/${course_id}`, {
    method: 'DELETE'
  })
};

function* cancelTutoring (action) {
   try {
      console.log('action', `/course/cancel/${action.user_id.id}/tutoring/${action.course_id}`);
      const courses = yield call(cancelTutoringPromise, action.user_id, action.course_id);
      yield put({type: "CANCEL_TUTORING_FOR_COURSE_SUCCEEDED", user_id: action.user_id, course_id: action.course_id});
   } catch (e) {
      console.log(e);
      yield put({type: "CANCEL_TUTORING_FOR_COURSE_FAILED", message: e.message});
   }
}

function* cancel_tutoring_saga () {
  yield takeEvery("CANCEL_TUTORING_FOR_COURSE_REQUESTED", cancelTutoring);
}

export default cancel_tutoring_saga;