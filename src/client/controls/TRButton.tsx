import React, { FunctionComponent } from 'react';
import cn from 'classnames';

type TRButtonProps = {
  className: string,
  onClick: Function,
}

const TRButton: FunctionComponent<TRButtonProps> = ({
  children = null,
  className = '',
  onClick = () => {},
}) => (
  <button
    className={cn(
      'bg-white',
      'hover:bg-gray-100',
      'text-gray-800',
      'font-semibold',
      'py-2',
      'px-4',
      'border',
      'border-gray-400',
      'rounded',
      'shadow',
      className,
    )}
    onClick={(e) => onClick(e)}
  >
    {children}
  </button>
);

export default TRButton;