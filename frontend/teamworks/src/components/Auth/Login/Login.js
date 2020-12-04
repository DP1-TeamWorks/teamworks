import React from "react";
import "./login.css";
import LoginForm from "./LoginForm"

export default function Login({setUserSession}) {
  return (
      <div className="Container">
        <div className="LoginBox">
          <span className="TeamWord">TEAM</span><span className="WorksWord">WORKS</span>
          <LoginForm setUserSession={setUserSession} />
          <p className="NewTeam">Create your own team</p>
        </div>
      </div>
  );
}
