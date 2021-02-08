import React from "react";
import "../tags/Tags.css";

const Circle = ({ color, className }) => {
  return (
    <>
      <div className={`Circle ${className}`} style={{ background: color }} />
    </>
  );
};

export default Circle;
