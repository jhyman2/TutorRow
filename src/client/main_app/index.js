import React, { Component } from 'react';
import { connect } from 'react-redux'

import Loading from './components/loading';
import SelectUni from './components/selectUni';

import { testAction, fetchUnis } from './actions';

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
      toDisplay = <SelectUni user={this.props.user} unis={this.props.universities} />;
    } else {
      toDisplay = <p>WELCOME</p>
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
    }
  }
}

const test_app = connect(
  mapStateToProps,
  mapDispatchToProps
)(Main_App)

export default test_app;