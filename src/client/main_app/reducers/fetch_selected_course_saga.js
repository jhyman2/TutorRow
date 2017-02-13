import { call, put, takeEvery, takeLatest } from 'redux-saga/effects'

const fetchSelectedCoursePromise = function (university_name, course_id) {
  return fetch(`/course/${university_name}/${course_id}`)
    .then((res) => {
      return res.json();
    });
};

// worker Saga: will be fired on USER_FETCH_REQUESTED actions
function* fetchSelectedCourse (action) {
   try {
      const course = yield call(fetchSelectedCoursePromise, action.university_name, action.course_id);
      yield put({type: "FETCH_SELECTED_COURSE_SUCCEEDED", course: course});
   } catch (e) {
      console.log(e);
      yield put({type: "FETCH_SELECTED_COURSE_FAILED", message: e.message});
   }
}

/*
  Starts fetchUser on each dispatched `UNI_FETCH_REQUESTED` action.
  Allows concurrent fetches of user.
*/
function* fetch_selected_course_saga () {
  yield takeEvery("FETCH_SELECTED_COURSE_REQUESTED", fetchSelectedCourse);
}

export default fetch_selected_course_saga;