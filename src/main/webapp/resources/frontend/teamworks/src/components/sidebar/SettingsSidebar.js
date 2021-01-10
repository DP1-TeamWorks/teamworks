import React from "react";
import Sidebar from "./Sidebar";
import LinkButton from "../buttons/LinkButton";
import SidebarTab from "./SidebarTab";
import "./SettingsSidebar.css";

const SettingsSidebar = ({ onAddNewUserClicked }) => {
  return (
    <Sidebar>
      <LinkButton gradient onClick={onAddNewUserClicked} className="MainButton" path="/settings/users">Add a new user</LinkButton>
      <hr className="Separator" />
      <h3 className="SettingSidebarTitle">Team Settings</h3>
      <SidebarTab text="Team" path="/settings/team"></SidebarTab>
      <SidebarTab text="Departments" path="/settings/departments"></SidebarTab>
      <SidebarTab text="Projects" path="/settings/projects"></SidebarTab>
      <SidebarTab text="Users" path="/settings/users"></SidebarTab>
    </Sidebar>
  );
};

export default SettingsSidebar;
