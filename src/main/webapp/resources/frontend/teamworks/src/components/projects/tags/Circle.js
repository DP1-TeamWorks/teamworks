import React from "react";
import "../tags/Tags.css";

const Circle = ({ color }) => {
  return (
    <>
      <div className="Circle" style={{ background: color }} />
    </>
  );
};

export default Circle;
