import { call, put, takeEvery, takeLatest } from 'redux-saga/effects'

const fetchUniPromise = function () {
  return fetch('/universities')
    .then((res) => {
      return res.json();
    });
};

// worker Saga: will be fired on USER_FETCH_REQUESTED actions
function* fetchUnis() {
   try {
      const universities = yield call(fetchUniPromise);
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
function* fetch_unis_saga() {
  yield takeEvery("UNI_FETCH_REQUESTED", fetchUnis);
}

export default fetch_unis_saga;