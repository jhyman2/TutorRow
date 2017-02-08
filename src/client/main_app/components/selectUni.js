import React, { Component } from 'react';

export default class SelectUni extends Component {

  prepareUnis () {
    return this.props.unis.map((uni) => {
      return <option key={`${uni.id}`} value={`${uni.id}`}>{uni.name}</option>
    });
  }

  render() {
    return (
      <div>
        <p>Welcome to Tutorrow, {this.props.user.full_name}!</p>
        <p>Please select the university that you belong to:</p>
        <select>
          {this.prepareUnis()}
        </select>
      </div>
    );
  }
}