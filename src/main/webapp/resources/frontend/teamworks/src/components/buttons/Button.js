import React from 'react';
import "./Button.css";

const Button = ({children, className, onClick}) =>
{
    return (
        <button className={`Button ${className}`} onClick={onClick} action="none">
            {children}
        </button>
    )
}

export default Button