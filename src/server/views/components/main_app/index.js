import React, { Component } from 'react';
import axios from 'axios';

export default class Main_App extends Component {
  render() {
    return (
      <div>
        Welcome {this.props.user.first_name} {this.props.user.last_name}!

        What school do you go to?
        <ul>
        {this.props.universities.map((university) => {
            return (
              <li key={university.id}>{university.name}</li>
            )
          })
        }
        </ul>
      </div>
    );
  }
}