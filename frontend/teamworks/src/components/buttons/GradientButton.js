import React from 'react';
import Button from "./Button";
import "./GradientButton.css";

const GradientButton = (props) =>
{
    return <Button className={`GradientButton ${props.className}`} onClick={props.onClick}>{props.children}</Button>
}

export default GradientButton