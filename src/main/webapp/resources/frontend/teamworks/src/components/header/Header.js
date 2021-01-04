import React from "react";
import { Link } from "react-router-dom";
import "../Auth/Login/Login";
import "./Header.css";
import ProfilePic from "../profile/ProfilePic";
import LinkButton from "../buttons/LinkButton";
import MainTitle from "./MainTitle";

const Header = ({nosearchbar}) => { 

  let content;
  if (nosearchbar)
  {
    content = <LinkButton className="Button--green HeaderButton" path="/">Go to Inbox</LinkButton>
  } else
  {
    content = <>
    <label className="HeaderSearchbar">
      <input className='SearchInput' placeholder="Search for messages"></input>
    </label>
    <LinkButton className="Button--green HeaderButton" path="/settings/team">Manage team</LinkButton>
  </>;
  }
  return (
    <div className="Header">
      <MainTitle />
      {content}
      <ProfilePic small src="/default_pfp.png" />
    </div>
    
  );
};

export default Header;
