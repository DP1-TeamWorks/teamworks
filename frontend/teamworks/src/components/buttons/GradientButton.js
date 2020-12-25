import React from 'react';
import "./GradientButton.css";

const GradientButton = (props) =>
{
    return <button className={`GradientButton ${props.className}`} onClick={props.onClick} action="none">{props.children}</button>
}

export default GradientButton;