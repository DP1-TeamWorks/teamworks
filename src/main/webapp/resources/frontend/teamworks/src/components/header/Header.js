import React from "react";
import "./Header.css";
import "../Auth/Login/Login.css";

const Header = () => {
  return (
    <div className="Header">
      <div className="HeaderTitle">
        <span className="TeamWord">TEAM</span>
        <span className="WorksWord">WORKS</span>
      </div>
      <input className='SearchInput'></input>
    </div>
  );
};

export default Header;
