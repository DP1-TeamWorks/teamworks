import React from "react";
import "./Forms.css";
import InputError from "./InputError";
export default function Input({
  name,
  type,
  placeholder,
  styleClass,
  value,
  error,
  changeHandler,
}) {
  return (
    <>
      <input
        placeholder={placeholder}
        className={styleClass}
        id={name}
        type={type}
        name={name}
        value={value}
        onChange={(e) => changeHandler(name, e.target.value, type)}
        style={{
          border: error && error !== "" ? "1px solid #e32d2d" : "",
        }}
      />
      <InputError error={error} />
    </>
  );
}
