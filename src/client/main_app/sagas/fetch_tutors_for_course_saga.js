import { call, put, takeEvery, takeLatest } from 'redux-saga/effects'

const fetchTutorsPromise = function (course_id) {
  return fetch(`/tutors/${course_id}`)
    .then((res) => {
      return res.json();
    });
};

function* fetchTutors (action) {
   try {
      const tutors = yield call(fetchTutorsPromise, action.course_id);
      yield put({type: "TUTORS_FOR_COURSE_FETCH_SUCCEEDED", tutors: tutors});
   } catch (e) {
      console.log(e);
      yield put({type: "TUTORS_FOR_COURSE_FETCH_FAILED", message: e.message});
   }
}

function* fetch_tutors_saga () {
  yield takeEvery("FETCH_TUTORS_FOR_COURSE_REQUESTED", fetchTutors);
}

export default fetch_tutors_saga;