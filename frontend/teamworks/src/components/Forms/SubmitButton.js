import React from "react";
import "./forms.css";
export default function InputError({ value, hasErrors }) {
  return (
    <>
      <br />
      <input
        className={"SubmitButton"}
        type="submit"
        value={value}
        disabled={hasErrors}
        style={{background: hasErrors ? "linear-gradient(91.13deg, #696969 0%, #505050 100%)" : "",
         border: hasErrors ? "1px solid rgba(22, 22, 22, 0.8)" :"",
          cursor: hasErrors ? "auto": ""}}
      />
    </>
  );
}
