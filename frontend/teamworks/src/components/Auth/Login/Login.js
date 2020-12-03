import React from "react";
import "./login.css";
import LoginForm from "./LoginForm"

export default function Login({setUserSession}) {
  return (
      <div className="container">
        <div className="loginBox">
          <span className="TeamWord">TEAM</span><span className="WorksWord">WORKS</span>
          <LoginForm setUserSession={setUserSession} />
          <p className="newTeam">Create your own team</p>
        </div>
      </div>
  );
}
