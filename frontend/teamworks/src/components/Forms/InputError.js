import React from "react";
import "./forms.css";
export default function InputError({ error }) {
  return <p className="error"> {error}</p>;
}
