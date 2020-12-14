import React from "react";
import "./SignUp.css";
import SignUpForm from "./SignUpForm"

export default function SignUp({setUserSession}) {
  return (
      <div className="Container">
        <div className="SignUpBox">
          <SignUpForm setUserSession={setUserSession} />
        </div>
      </div>
  );
}