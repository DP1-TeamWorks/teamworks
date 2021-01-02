import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck } from "@fortawesome/free-solid-svg-icons";

const ToDoCircle = ({ isDone, setIsDone }) => {
  console.log("isDone=" + isDone);

  const checkToDo = () => {
    setIsDone(true);
  };

  return (
    <>
      <button className="ToDoCircle" onClick={checkToDo}>
        {isDone && <FontAwesomeIcon icon={faCheck} />}
      </button>
    </>
  );
};

export default ToDoCircle;
