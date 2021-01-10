import React from "react";
import "./SignUp.css";
import SignUpForm from "./SignUpForm";
import GoBackButton from "../../buttons/GoBackButton";


export default function SignUp() {
  return (
    <div className="Container">
      <div className="SignUpBox">
        <GoBackButton path="/login" />
        <SignUpForm />
      </div>
    </div>
  );
}
