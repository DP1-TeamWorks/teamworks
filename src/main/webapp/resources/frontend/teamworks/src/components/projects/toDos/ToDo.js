import React, { useState } from "react";
import ToDoApiUtils from "../../../utils/api/ToDoApiUtils";
import Circle from "../tags/Circle";
import ToDoCircle from "./ToDoCircle";

const ToDo = ({ id, tagList, title , done}) => {
  const [isDone, setIsDone] = useState(done);
  const [isDoneAnimation, setIsDoneAnimation] = useState(false);

  const markAsDone = () => {
    setIsDoneAnimation(true);
    console.log("Marking the toDo as done")
    setTimeout(
      () =>
        ToDoApiUtils.markToDoAsDone(id)
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
          return <Circle key={tag.color} color={tag.color} />;
        })}
      </div>
    </div>
  );
};

export default ToDo;
