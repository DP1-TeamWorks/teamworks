import React, { useState, useEffect } from "react";
import ProjectApiUtils from "../../../utils/api/ProjectApiUtils";
import Circle from "../tags/Circle";
import ToDoCircle from "./ToDoCircle";

const ToDo = ({ id, tagList, title }) => {
  const [isDone, setIsDone] = useState(false);
  const [isDoneAnimation, setIsDoneAnimation] = useState(false);

  const markAsDone = () => {
    setIsDoneAnimation(true);
    // TODO: fix the animation
    setTimeout(
      () =>
        ProjectApiUtils.markToDoAsDone(id)
          .then(() => {
            setIsDone(true);
          })
          .catch((error) => {
            console.log("ERROR: cannot mark the todo as done");
            setIsDoneAnimation(false);
          }),
      700
    );
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
