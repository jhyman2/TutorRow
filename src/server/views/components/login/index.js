import React from 'react';

// import homeIcon from './../../images/main_page.jpg';

const styles = {
  wrapper: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
};

function Login() {
  return (
    <div style={styles.wrapper}>
      <h1>Welcome to TutorRow</h1>
      <p>Login or Register with:</p>
      <a href="/auth/facebook">Facebook</a>
    </div>
  );
}

export default Login;