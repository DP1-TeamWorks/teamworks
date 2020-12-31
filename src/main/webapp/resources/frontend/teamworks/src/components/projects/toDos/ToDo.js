import React, { useState } from "react";
import Circle from "../tags/Circle";
import ToDoCircle from "./ToDoCircle";

const ToDo = ({ tagList, color, title }) => {
  console.log(color);
  return (
    <div className="TagTab">
      <ToDoCircle /> <span>{title} </span>
      {tagList.map((tag) => {
        return <Circle color={color} />;
      })}
    </div>
  );
};

export default ToDo;
