import React from "react";
import "../Auth/Login/Login";
import "./Header.css";
import ProfilePic from "../profile/ProfilePic";
import LinkButton from "../buttons/LinkButton";
import { withRouter } from "react-router-dom";

const MainTitle = ({history}) => {
  return (
    <div className="HeaderTitle" onClick={ () => history.push("/") }>
        <span className="TeamWord">TEAM</span>
        <span className="WorksWord">WORKS</span>
      </div>
  );
};

export default withRouter(MainTitle);
