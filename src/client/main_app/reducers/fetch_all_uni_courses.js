import { call, put, takeEvery, takeLatest } from 'redux-saga/effects'

const fetchCoursesPromise = function (university_id) {
  return fetch(`/courses/${university_id}`)
    .then((res) => {
      return res.json();
    });
};

// worker Saga: will be fired on USER_FETCH_REQUESTED actions
function* fetchCourses (action) {
   try {
      const courses = yield call(fetchCoursesPromise, action.university_id);
      yield put({type: "ALL_COURSES_FETCH_SUCCEEDED", courses: courses});
   } catch (e) {
      console.log(e);
      yield put({type: "ALL_COURSES_FETCH_FAILED", message: e.message});
   }
}

/*
  Starts fetchUser on each dispatched `UNI_FETCH_REQUESTED` action.
  Allows concurrent fetches of user.
*/
function* fetch_courses_saga () {
  yield takeEvery("FETCH_ALL_UNI_COURSES_REQUESTED", fetchCourses);
}

export default fetch_courses_saga;