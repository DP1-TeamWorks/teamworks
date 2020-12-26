import React from "react";
import "./forms.css";
import SubmitError from "./SubmitError";
import GradientButton from "../buttons/GradientButton";

export default function SubmitButton({ text, requestError, hasErrors }) {
  return (
    <>
      <br />
      <GradientButton
        className="SubmitButton"
        type="submit"
        disabled={hasErrors}
        style={{
          backgroundColor: hasErrors ? "#696969" : "#b4dd63",
          border: hasErrors ? "1px solid rgba(22, 22, 22, 0.8)" : "",
          cursor: hasErrors ? "auto" : "",
        }}
      >{text}</GradientButton>

      <SubmitError error={requestError !== "" && requestError} />
    </>
  );
}
