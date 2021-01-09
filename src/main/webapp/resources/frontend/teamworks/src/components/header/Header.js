import React from "react";
import { Link } from "react-router-dom";
import "../Auth/Login/Login";
import Button from '../buttons/Button';
import ProfileHeader from '../profile/ProfileHeader';
import "./Header.css";


const Header = () => {
  return (
    <div className="Header">
      <Link to="/" className="LogoLink">
      <div className="HeaderTitle">
        <span className="TeamWord">TEAM</span>
        <span className="WorksWord">WORKS</span>
      </div>
      </Link>
      <label className="HeaderSearchbar">
      <input className='SearchInput' placeholder="Search for messages"></input>
      </label>
      <Link to="/settings/team">
        <Button className='Button--green HeaderButton'>Manage team</Button>
      </Link>
      <ProfileHeader src="https://avatars0.githubusercontent.com/u/1417708?s=460&u=b8dd20ef775f23c845a418513e48617181de734a&v=4"/>
    </div>
    
  );
};

export default Header;
