import React from 'react';
import Sidebar from './Sidebar';
import GradientButton from "../buttons/GradientButton"
import SidebarTab from "./SidebarTab";
import "./SettingsSidebar.css";

const SettingsSidebar = ({onAddNewUserClicked}) =>
{
    return (
        <Sidebar>
            <GradientButton onClick={onAddNewUserClicked} className="MainButton">Add a new user</GradientButton>
            <hr className="Separator" />
            <h3 className="SettingSidebarTitle">Team Settings</h3>
            <SidebarTab text="Team" path="/settings/team"></SidebarTab>
            <SidebarTab text="Departments" path="/settings/departments"></SidebarTab>
            <SidebarTab text="Projects" path="/settings/projects"></SidebarTab>
            <SidebarTab text="Users" path="/settings/users"></SidebarTab>

        </Sidebar>
    )
}

export default SettingsSidebar;
