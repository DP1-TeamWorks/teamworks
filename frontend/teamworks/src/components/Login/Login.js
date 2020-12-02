import React from "react";
import "./login.css";
import LoginForm from "./LoginForm"
export function Login({setUserSession}) {
  return (
    <React.Fragment>
      <div className="container">
        <div className="loginBox">
          <span className="teamWord">TEAM</span><span className="worksWord">WORKS</span>
          <LoginForm setUserSession={setUserSession} />
        </div>
      </div>
    </React.Fragment>
  );
}
