import React from "react";
import { Link } from "react-router-dom";
import "./Login.css";
import LoginForm from "./LoginForm"

export default function Login({setSession}) {
  return (
      <div className="Container">
        <div className="LoginBox">
          <span className="TeamWord">TEAM</span><span className="WorksWord">WORKS</span>
          <LoginForm setSession={setSession} />
          <Link to="/signup" className="NewTeam">Create your own team</Link>
        </div>
      </div>
  );
}
