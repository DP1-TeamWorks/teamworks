import React from "react";
import Circle from "./Circle";

const Tag = ({
  color,
  title,
  selectedTab,
  setSelectedTab,
  noOpenedMessages,
}) => {
  return (
    <>
      <Circle color={color} /> <span>{title} </span>{" "}
      <span style={{ float: "right" }}> {noOpenedMessages} </span>
    </>
  );
};

export default Tag;
