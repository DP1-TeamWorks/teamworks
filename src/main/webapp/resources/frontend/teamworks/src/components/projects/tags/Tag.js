import React, { useState } from "react";
import Circle from "./Circle";

const Tag = ({ color, title, noOpenedMessages }) => {
  console.log(color);
  return (
    <div className="TagTab">
      <Circle color={color} /> <span>{title} </span>{" "}
      <span style={{ float: "right" }}> {noOpenedMessages} </span>
    </div>
  );
};

export default Tag;
