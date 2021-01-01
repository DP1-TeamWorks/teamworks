import React, { useState } from "react";
import Circle from "../tags/Circle";
import ToDoCircle from "./ToDoCircle";

const ToDo = ({ tagList, title }) => {
  const [isDone, setIsDone] = useState(false);

  return (
    <div className="TagTab">
      <ToDoCircle isDone={isDone} setIsDone={setIsDone} /> <span>{title} </span>
      <div style={{ float: "right" }}>
        {tagList.map((tag) => {
          return <Circle color={tag.color} />;
        })}
      </div>
    </div>
  );
};

export default ToDo;
