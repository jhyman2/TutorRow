import React      from 'react';
import ReactDOM   from 'react-dom';

class Test extends React.Component {
  render () {
    return (
      <p>This be a hhhh</p>
    )
  }
}

ReactDOM.render(<Test />, document.getElementById('app-container'));