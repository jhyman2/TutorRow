import React, { Component } from 'react';
import { connect } from 'react-redux'

import Loading from './components/loading';
import SelectUni from './components/selectUni';

import { updateUserWithUni, fetchUnis } from './actions';

class Main_App extends Component {

  componentWillMount() {
    // fetch universities if user does not have a university
    if (!this.props.user.university_id) {
      this.props.fetchUnis();
    }
  }

  render() {
    let toDisplay;

    if (this.props.loading) {
      toDisplay = <Loading />;
    } else if (this.props.user && !this.props.user.university_id) {
      toDisplay = <SelectUni
                    user={this.props.user}
                    unis={this.props.universities}
                    updateUserWithUni={this.props.updateUserWithUni}
                  />;
    } else if (!this.props.user) {
      toDisplay = <p>Please go back and log in until we figure out how to store cookies</p>
    } else {
      // @todo: fetch courses for university
      toDisplay = <p>WELCOME...i will now fetch user's courses</p>
    }

    return (
      <div>
        {toDisplay}
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    user: state.user,
    universities: state.universities,
    loading: state.loading
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    fetchUnis () {
      dispatch(fetchUnis());
    },
    updateUserWithUni (user_id, university_id) {
      dispatch(updateUserWithUni(user_id, university_id));
    }
  }
}

const test_app = connect(
  mapStateToProps,
  mapDispatchToProps
)(Main_App)

export default test_app;