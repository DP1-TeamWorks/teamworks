import React from "react";
import "../buttons/GradientButton.css";
import "./forms.css";
import SubmitError from "./SubmitError";

export default function SubmitButton({ value, requestError, hasErrors }) {
  return (
    <>
      <br />
      <input
        className="SubmitButton GradientButton"
        type="submit"
        value={value}
        disabled={hasErrors}
        style={{
          backgroundColor: hasErrors ? "#696969" : "#b4dd63",
          border: hasErrors ? "1px solid rgba(22, 22, 22, 0.8)" : "",
          cursor: hasErrors ? "auto" : "",
        }}
      />

      <SubmitError error={requestError !== "" && requestError} />
    </>
  );
}
