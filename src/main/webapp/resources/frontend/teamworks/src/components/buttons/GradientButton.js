import React from 'react';
import "./GradientButton.css";

const GradientButton = ({children, disabled, onClick, className}) =>
{
    return <button className={`GradientButton ${className}`} onClick={onClick} action="none" disabled={disabled}>{children} </button>
}

export default GradientButton;