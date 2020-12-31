import React, { useState } from "react";

const Circle = ({ color }) => {
  return (
    <>
      <div className="Circle" style={{ background: color }} />
    </>
  );
};

export default Circle;
