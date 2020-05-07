import React                            from 'react';
import { render }                       from 'react-dom';
import { Provider }                     from 'react-redux';
import createSagaMiddleware             from 'redux-saga';
import { createStore, applyMiddleware } from 'redux';
import { composeWithDevTools }          from 'redux-devtools-extension';
import ApolloClient from 'apollo-boost';
import { ApolloProvider } from '@apollo/react-hooks';
import { InMemoryCache } from 'apollo-cache-inmemory';

import reducers from './reducers';
import sagas    from './sagas';

// main app view starting point
import App from './index.tsx';
import './app.css';

const cache =  new InMemoryCache();
const client = new ApolloClient({
  cache,
  uri: 'http://localhost:4000',
  headers: {
    authorization: window.__APP_INITIAL_STATE__.user.id,
  }, 
});

cache.writeData({ data: window.__APP_INITIAL_STATE__ });

const sagaMiddleware = createSagaMiddleware()

let store = createStore(
  reducers,
  window.__APP_INITIAL_STATE__,
  composeWithDevTools(applyMiddleware(sagaMiddleware)),
);

sagaMiddleware.run(sagas);

render(
  <ApolloProvider client={client}>
	  <Provider store={store}>
	    <App />
	  </Provider>,
   </ApolloProvider>,
  document.getElementById('root')
);
