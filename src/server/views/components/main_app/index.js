import React, { Component } from 'react';
import axios from 'axios';

export default class Main_App extends Component {
  render() {
    return (
      <div>
        Welcome to Tutorrow, {this.props.user.name}!
        Your Facebook ID is {this.props.user.id}
      </div>
    );
  }
}