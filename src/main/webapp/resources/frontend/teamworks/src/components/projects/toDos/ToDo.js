import React, { useState, useEffect } from "react";
import Circle from "../tags/Circle";
import ToDoCircle from "./ToDoCircle";

const ToDo = ({ tagList, title }) => {
  const [isDone, setIsDone] = useState(false);
  const [isDoneAnimation, setIsDoneAnimation] = useState(false);

  const markAsDone = () => {
    setIsDoneAnimation(true);
    // TODO: fix the animation
    setTimeout(() => setIsDone(true), 700);
    // TODO: call API request to mark as done the real TODO
  };

  return (
    <div className="ToDoTab" onClick={markAsDone} hidden={isDone}>
      <ToDoCircle isDone={isDoneAnimation} markAsDone={markAsDone} />{" "}
      <span
        style={{ textDecoration: isDoneAnimation ? "line-through" : "none" }}
      >
        {title}{" "}
      </span>
      <div style={{ float: "right" }}>
        {tagList.map((tag) => {
          return <Circle color={tag.color} />;
        })}
      </div>
    </div>
  );
};

export default ToDo;
