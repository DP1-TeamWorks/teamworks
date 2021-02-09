import React, { useContext } from "react";
import Sidebar from "./Sidebar";
import LinkButton from "../buttons/LinkButton";
import SidebarTab from "./SidebarTab";
import "./SettingsSidebar.css";
import UserCredentials from "../../context/UserCredentials";

const SettingsSidebar = () =>
{

  const credentials = useContext(UserCredentials);

  if (credentials.isTeamManager === undefined)
  {
    return <Sidebar></Sidebar>
  }

  return (
    <Sidebar>
      {credentials.isTeamManager ? (<>
        <LinkButton gradient className="MainButton" path="/settings/users">Add a new user</LinkButton>
        <hr className="Separator" />
      </>) : ""}
      <h3 className="SettingSidebarTitle">Team Settings</h3>
      {credentials.isTeamManager ? <SidebarTab text="Team" path="/settings/team" /> : ""}
      <SidebarTab text="Departments" path="/settings/departments" />
      <SidebarTab text="Projects" path="/settings/projects" />
      <SidebarTab text="Users" path="/settings/users" />
    </Sidebar>
  );
};

export default SettingsSidebar;
