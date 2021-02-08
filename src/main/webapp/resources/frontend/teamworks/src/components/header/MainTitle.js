import React from "react";
import { withRouter } from "react-router-dom";
import "../Auth/Login/Login";
import "./Header.css";

const MainTitle = ({history}) => {
  return (
    <div className="HeaderTitle" onClick={ () => history.push("/") }>
        <span className="TeamWord">TEAM</span>
        <span className="WorksWord">WORKS</span>
      </div>
  );
};

export default withRouter(MainTitle);
