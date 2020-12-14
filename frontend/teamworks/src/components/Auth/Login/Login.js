import React from "react";
import "./Login.css";
import LoginForm from "./LoginForm"

export default function Login({setSession}) {
  return (
      <div className="Container">
        <div className="LoginBox">
          <span className="TeamWord">TEAM</span><span className="WorksWord">WORKS</span>
          <LoginForm setSession={setSession} />
          <p className="NewTeam">Create your own team</p>
        </div>
      </div>
  );
}
