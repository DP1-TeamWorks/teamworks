import React from 'react';
import Sidebar from './Sidebar';
import GradientButton from "../buttons/GradientButton"

const SettingsSidebar = (props) =>
{
    return (
        <Sidebar>
            <GradientButton onClick={props.onAddNewUserClicked} className="Sidebar__PaddedElement Sidebar__PaddedElement--Top">Add a new user</GradientButton>
        </Sidebar>
    )
}

export default SettingsSidebar;
