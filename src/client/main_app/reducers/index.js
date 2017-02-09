import { combineReducers } from 'redux'

const user = (state = '', action) => {
  switch (action.type) {
    case 'CHANGE_USER':
      return state;
    case 'USER_UPDATE_WITH_UNI_REQUESTED':
      return state;
    case 'USER_UPDATE_WITH_UNI_SUCCEEDED':
      return action.user;
    case 'USER_UPDATE_WITH_UNI_FAILED':
      return -1;
    default:
      return state;
  }
}

const universities = (state = [], action) => {
  switch (action.type) {
    case 'UNI_FETCH_REQUESTED':
      return ['FETCHING'];
    case 'UNI_FETCH_SUCCEEDED':
      return action.universities;
    case 'UNI_FETCH_FAILED':
      return ['Err fetching'];
    default:
      return state;
  }
}

const loading = (state = false, action) => {
  switch (action.type) {
    case 'UNI_FETCH_REQUESTED':
      return true;
    case 'UNI_FETCH_SUCCEEDED':
      return false;
    default:
      return state;
  }
}


const todoApp = combineReducers({
  user,
  universities,
  loading
});

export default todoApp;