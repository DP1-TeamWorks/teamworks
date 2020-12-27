import React from 'react';
import "./Button.css";

const Button = (props) =>
{
    return (
        <button className={`Button ${props.className}`} onClick={props.onClick} action="none">
            {props.children}
        </button>
    )
}

export default Button