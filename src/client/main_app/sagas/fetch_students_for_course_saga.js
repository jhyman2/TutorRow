import { call, put, takeEvery, takeLatest } from 'redux-saga/effects'

const fetchStudentsPromise = function (course_id) {
  return fetch(`/students/${course_id}`)
    .then((res) => {
      return res.json();
    });
};

function* fetchStudents (action) {
   try {
      const students = yield call(fetchStudentsPromise, action.course_id);
      yield put({type: "STUDENTS_FOR_COURSE_FETCH_SUCCEEDED", students: students});
   } catch (e) {
      console.log(e);
      yield put({type: "STUDENTS_FOR_COURSE_FETCH_FAILED", message: e.message});
   }
}

function* fetch_students_saga () {
  yield takeEvery("FETCH_STUDENTS_FOR_COURSE_REQUESTED", fetchStudents);
}

export default fetch_students_saga;