import React from 'react';
import "./GradientButton.css"

class GradientButton extends React.Component {
    constructor(props) {
        super(props);
        this.clickCallback = props.clickCallback;
        this.className = props.className;
        this.children = props.children;
    }

    render()
    {
        return (
            <div className={`GradientButton ${this.className}`} onClick={this.clickCallback()}>
                {this.children}
            </div>
        )
    }
}

export default GradientButton