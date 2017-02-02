import React, { Component } from 'react';
import axios from 'axios';

export default class App extends Component {

  render() {
    return (
      <div>
        Please choose your university, {this.props.user.full_name}
        <select>
          {this.props.universities.map((university) => {
            return <option key={university.id} value={`${university.id}`}>{university.name}</option>;
          })}
        </select>
      </div>
    );
  }
}