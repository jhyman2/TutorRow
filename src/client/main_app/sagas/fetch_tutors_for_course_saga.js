import { call, put, takeEvery, takeLatest } from 'redux-saga/effects'

const fetchTutorsPromise = function (course_id) {
  return fetch(`/tutors/${course_id}`)
    .then((res) => {
      return res.json();
    });
};

// worker Saga: will be fired on FETCH_ALL_UNI_COURSES_REQUESTED actions
function* fetchTutors (action) {
   try {
      const tutors = yield call(fetchTutorsPromise, action.course_id);
      console.log('tutors', tutors);
      yield put({type: "TUTORS_FOR_COURSE_FETCH_SUCCEEDED", tutors: tutors});
   } catch (e) {
      console.log(e);
      yield put({type: "TUTORS_FOR_COURSE_FETCH_FAILED", message: e.message});
   }
}

/*
  Starts fetchUser on each dispatched `UNI_FETCH_REQUESTED` action.
  Allows concurrent fetches of tutors.
*/
function* fetch_tutors_saga () {
  yield takeEvery("FETCH_TUTORS_FOR_COURSE_REQUESTED", fetchTutors);
}

export default fetch_tutors_saga;