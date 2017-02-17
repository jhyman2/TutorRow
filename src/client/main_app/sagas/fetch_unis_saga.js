import { call, put, takeEvery, takeLatest } from 'redux-saga/effects'

const fetchUniPromise = function () {
  return fetch('/universities')
    .then((res) => {
      return res.json();
    });
};

function* fetchUnis() {
   try {
      const universities = yield call(fetchUniPromise);
      yield put({type: "UNI_FETCH_SUCCEEDED", universities: universities});
   } catch (e) {
      console.log(e);
      yield put({type: "UNI_FETCH_FAILED", message: e.message});
   }
}

function* fetch_unis_saga() {
  yield takeEvery("UNI_FETCH_REQUESTED", fetchUnis);
}

export default fetch_unis_saga;