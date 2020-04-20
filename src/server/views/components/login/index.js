import React from 'react';
import cn from 'classnames';

// import homeIcon from './../../images/main_page.jpg';

function Login() {
  return (
    <div className="mx-auto container justify-center h-screen flex items-center">
      <div className="mx-auto max-w-sm mx-auto flex-col p-6 bg-white rounded-lg shadow-xl">
        <h1 className="text-center text-2xl mb-4">Welcome to TutorRow</h1>
        <p className="text-center">Login or Register with:</p>
        <div className="text-center">
          <a
            className={cn(
              'inline-block',
              'text-sm',
              'px-4',
              'py-2',
              'leading-none',
              'border',
              'rounded',
              'text-blue',
              'border-blue',
              'hover:border-transparent',
              'hover:text-teal-500',
              'hover:bg-blue',
              'mt-4 lg:mt-0',
            )}
            href="/auth/facebook"
          >
            Facebook
          </a>
        </div>
      </div>
    </div>
  );
}

export default Login;