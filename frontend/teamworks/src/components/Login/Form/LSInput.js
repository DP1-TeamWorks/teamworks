import React from "react";
import "../login.css";
import InputError from "./InputError";
export default function LSInput({
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
