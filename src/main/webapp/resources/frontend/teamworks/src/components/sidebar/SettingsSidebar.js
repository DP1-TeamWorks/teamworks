import React from 'react';
import Sidebar from './Sidebar';
import GradientButton from "../buttons/GradientButton"

const SettingsSidebar = (props) =>
{
    return (
        <Sidebar>
            <GradientButton onClick={props.onAddNewUserClicked} className="MainButton">Add a new user</GradientButton>
            <hr className="Separator" />
        </Sidebar>
    )
}

export default SettingsSidebar;
