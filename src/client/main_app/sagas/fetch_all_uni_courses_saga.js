import { call, put, takeEvery, takeLatest } from 'redux-saga/effects'

const fetchCoursesPromise = function (university_id) {
  return fetch(`/courses/${university_id}`)
    .then((res) => {
      return res.json();
    });
};

function* fetchCourses (action) {
   try {
      const courses = yield call(fetchCoursesPromise, action.university_id);
      yield put({type: "ALL_COURSES_FETCH_SUCCEEDED", courses: courses});
   } catch (e) {
      console.log(e);
      yield put({type: "ALL_COURSES_FETCH_FAILED", message: e.message});
   }
}

function* fetch_courses_saga () {
  yield takeEvery("FETCH_ALL_UNI_COURSES_REQUESTED", fetchCourses);
}

export default fetch_courses_saga;