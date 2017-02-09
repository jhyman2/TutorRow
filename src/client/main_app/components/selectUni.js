import React, { Component } from 'react';

export default class SelectUni extends Component {

  updateUserWithUni () {
    const selectBox     = this.refs.uni_select_box;
    const university_id = selectBox[selectBox.selectedIndex].value

    this.props.updateUserWithUni(this.props.user.id, university_id);
  }

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
        <select ref="uni_select_box">
          {this.prepareUnis()}
        </select>
        <button onClick={(e) => this.updateUserWithUni()}>Go!</button>
      </div>
    );
  }
}