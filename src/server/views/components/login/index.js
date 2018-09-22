import React, { Component } from 'react';
import axios from 'axios';

// import homeIcon from './../../images/main_page.jpg';

export default class App extends Component {

  render() {
    return (
      <div>
        <h1>Welcome to TutorRow</h1>
        <p>Login or Register with:</p>

        <a href="/auth/facebook">Facebook</a>
      </div>
    );
  }
}