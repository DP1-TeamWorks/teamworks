import React from "react";
import "../login.css";
export default function InputError({ value, hasErrors }) {
  return (
    <>
      <br />
      <input
        className={hasErrors ? "SubmitButton Disabled" : "SubmitButton"}
        type="submit"
        value={value}
        disabled={hasErrors}
      />
    </>
  );
}
