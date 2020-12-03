import React from "react";
import "../login.css";
export default function InputError({ error }) {
  return <p className="error"> {error}</p>;
}
