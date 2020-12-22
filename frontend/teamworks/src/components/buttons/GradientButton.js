import React from 'react';
import "./GradientButton.css"

const GradientButton = (props) =>
{
    return (
        <div className={`GradientButton ${props.className}`} onClick={props.onClick}>
            {props.children}
        </div>
    )
}

export default GradientButton