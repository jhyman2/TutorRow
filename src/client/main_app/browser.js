import React                            from 'react';
import { render }                       from 'react-dom';
import { Provider }                     from 'react-redux';
import createSagaMiddleware             from 'redux-saga';
import { createStore, applyMiddleware } from 'redux';
import { composeWithDevTools }          from 'redux-devtools-extension';

import reducers from './reducers';
import sagas    from './sagas';

// main app view starting point
import App from './index.tsx';

const sagaMiddleware = createSagaMiddleware()

let store = createStore(
  reducers,
  window.__APP_INITIAL_STATE__,
  composeWithDevTools(applyMiddleware(sagaMiddleware)),
);

sagaMiddleware.run(sagas);

render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById('root')
);