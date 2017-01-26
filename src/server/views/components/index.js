import React, { Component } from 'react';

export default class App extends Component {
  render() {
    return (
      <ul>
        {this.props.users.map((user) => {
           return <li key={user.id}>{user.last_name}, {user.first_name}</li>
        })}
      </ul>
    );
  }
}