import React, { Component } from 'react';
import axios from 'axios';

export default class App extends Component {

  // handler for deleting a student
  deleteStudent (full_name) {
    axios.post('/delete', {
      full_name: full_name
    })
    .then(function (response) {
      window.location = '/';
    })
    .catch(function (error) {
      console.log(error);
    });
  }

  render() {
    return (
      <div>
        <ul>
          {this.props.users.map((user) => {
            return (
              <li key={user.id}>
                {user.full_name}
                <button onClick={() => this.deleteStudent(user.full_name)}>
                  <img width="16" src="https://cdn1.iconfinder.com/data/icons/toolbar-signs/512/cancel-128.png" />
                </button>
              </li>
            )
          })}
        </ul>
        <form action="/add" method="post">
          Add student:
          <input type="text" name="first_name" placeholder="First name" />
          <input type="text" name="last_name" placeholder="Last name" />
          <input type="submit" value="Submit" />
        </form>
        <form action="/login" method="post">
          Login test:
          <input type="text" name="first_name" placeholder="First name" />
          <input type="text" name="last_name" placeholder="Last name" />
          <input type="submit" value="Submit" />
        </form>
        <p>Login or Register with:</p>

        <a href="/auth/facebook">Facebook</a>
      </div>
    );
  }
}