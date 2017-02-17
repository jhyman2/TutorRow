import { call, put, takeEvery, takeLatest } from 'redux-saga/effects'

const fetchSelectedCoursePromise = function (university_name, course_id) {
  return fetch(`/course/${university_name}/${course_id}`)
    .then((res) => {
      return res.json();
    });
};

function* fetchSelectedCourse (action) {
   try {
      const course = yield call(fetchSelectedCoursePromise, action.university_name, action.course_id);
      yield put({type: "FETCH_SELECTED_COURSE_SUCCEEDED", course: course});
   } catch (e) {
      console.log(e);
      yield put({type: "FETCH_SELECTED_COURSE_FAILED", message: e.message});
   }
}

function* fetch_selected_course_saga () {
  yield takeEvery("FETCH_SELECTED_COURSE_REQUESTED", fetchSelectedCourse);
}

export default fetch_selected_course_saga;