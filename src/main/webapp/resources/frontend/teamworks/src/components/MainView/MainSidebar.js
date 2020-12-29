import React from 'react';
import Sidebar from '../sidebar/Sidebar';
import './MainSidebar.css';

const Sidebar = (props) => {
    return (
        <div className="Sidebar">
            {props.children}
        </div>
    )
}

export default Sidebar;
