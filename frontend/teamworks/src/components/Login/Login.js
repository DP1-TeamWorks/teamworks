import React from "react";
import "./login.css";
import LoginForm from "./LoginForm"
import {login} from "../../utils/api/authApiUtils"
export function Login() {
  return (
    <React.Fragment>
      <div className="container">
        <div className="loginBox">
          <span className="teamWord">TEAM</span><span className="worksWord">WORKS</span>
          <LoginForm />
        </div>
      </div>
    </React.Fragment>
  );
}
