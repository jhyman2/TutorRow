// @TODO: USE UNIVERSITIES ON LINE 17!!!!

import { call, put, takeEvery, takeLatest } from 'redux-saga/effects'

const promise = function () {
  return fetch('/universities')
    .then((res) => {
      return res.json()
    })
};

// worker Saga: will be fired on USER_FETCH_REQUESTED actions
function* fetchUnis() {
   try {
      const universities = yield call(promise);
      yield put({type: "UNI_FETCH_SUCCEEDED", universities: universities});
   } catch (e) {
      console.log(e);
      yield put({type: "UNI_FETCH_FAILED", message: e.message});
   }
}

/*
  Starts fetchUser on each dispatched `UNI_FETCH_REQUESTED` action.
  Allows concurrent fetches of user.
*/
function* mySaga() {
  yield takeEvery("UNI_FETCH_REQUESTED", fetchUnis);
}

/*
  Alternatively you may use takeLatest.

  Does not allow concurrent fetches of user. If "UNI_FETCH_REQUESTED" gets
  dispatched while a fetch is already pending, that pending fetch is cancelled
  and only the latest one will be run.
*/
function* mySaga() {
  yield takeLatest("UNI_FETCH_REQUESTED", fetchUnis);
}

export default mySaga;