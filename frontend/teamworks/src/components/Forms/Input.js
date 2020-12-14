import React from "react";
import "./forms.css";
import InputError from "./InputError";
export default function Input({
  name,
  type,
  placeholder,
  styleClass,
  error,
  changeHandler,
}) {
  return (
    <>
      <br />
      <input
        placeholder={placeholder}
        className={styleClass}
        id={name}
        type={type}
        name={name}
        onChange={changeHandler}
        style={{
          border: error && error !== "" ? "1px solid #e32d2d" : "",
        }}
      />
      <InputError error={error} />
    </>
  );
}
