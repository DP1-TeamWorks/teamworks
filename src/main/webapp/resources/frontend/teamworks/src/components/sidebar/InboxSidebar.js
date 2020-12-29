import React from 'react';
import Sidebar from '../sidebar/Sidebar';
import GradientButton from "../buttons/GradientButton";

const InboxSidebar = (props) => {
    return (
        <Sidebar>
            <GradientButton onClick={props.onAddNewUserClicked} className="Sidebar__PaddedElement Sidebar__PaddedElement--Top">Add a new user</GradientButton>
        </Sidebar>
    )
}

export default InboxSidebar;