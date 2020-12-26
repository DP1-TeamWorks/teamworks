import React from "react";
import "./SignUp.css";
import SignUpForm from "./SignUpForm";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";

export default function SignUp() {
  return (
    <div className="Container">
      <div className="SignUpBox">
        <div className="GoBack">
          <Link to="/login" className="GoBackText">
            <FontAwesomeIcon icon={faArrowLeft} style={{ color: "#A6CE56" }} className="BackArrow" />
            {"      "} GO BACK
          </Link>
        </div>

        <SignUpForm />
      </div>
    </div>
  );
}
