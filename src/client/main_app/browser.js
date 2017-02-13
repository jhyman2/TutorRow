import React                            from 'react';
import { render }                       from 'react-dom';
import { Provider }                     from 'react-redux';
import createSagaMiddleware             from 'redux-saga';
import { createStore, applyMiddleware } from 'redux';

// reducers and sagas
import reducers                   from './reducers';
import fetch_unis_saga            from './reducers/fetch_unis_saga';
import user_post_saga             from './reducers/user_post_saga';
import fetch_all_courses_saga     from './reducers/fetch_all_uni_courses';
import fetch_selected_course_saga from './reducers/fetch_selected_course_saga';

// main app view starting point
import App from './index';

const sagaMiddleware = createSagaMiddleware()

let store = createStore(
  reducers,
  window.__APP_INITIAL_STATE__,
  applyMiddleware(sagaMiddleware)
);

sagaMiddleware.run(fetch_unis_saga);
sagaMiddleware.run(user_post_saga);
sagaMiddleware.run(fetch_all_courses_saga);
sagaMiddleware.run(fetch_selected_course_saga);

render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById('root')
);