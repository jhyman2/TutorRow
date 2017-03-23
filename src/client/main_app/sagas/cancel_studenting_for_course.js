import { call, put, takeEvery, takeLatest } from 'redux-saga/effects'

const cancelStudentingPromise = function (user_id, course_id) {
  return fetch(`/course/cancel/studenting/${course_id}`, {
    method: 'DELETE'
  })
    .then((res) => {
      return res.json();
    });
};

function* cancelStudenting (action) {
   try {
      const courses = yield call(cancelStudentingPromise, action.user_id, action.course_id);
      yield put({type: "CANCEL_STUDENTING_FOR_COURSE_SUCCEEDED", user_id: action.user_id, course_id: action.course_id});
   } catch (e) {
      console.log(e);
      yield put({type: "CANCEL_STUDENTING_FOR_COURSE_FAILED", message: e.message});
   }
}

function* cancel_studenting_saga () {
  yield takeEvery("CANCEL_STUDENTING_FOR_COURSE_REQUESTED", cancelStudenting);
}

export default cancel_studenting_saga;