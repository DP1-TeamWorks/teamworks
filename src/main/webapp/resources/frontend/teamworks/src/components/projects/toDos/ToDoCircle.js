import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck } from "@fortawesome/free-solid-svg-icons";

const ToDoCircle = ({ isDone, markAsDone }) => {
  console.log("isDone=" + isDone);

  return (
    <>
      <button className="ToDoCircle" onClick={markAsDone}>
        {isDone && <FontAwesomeIcon icon={faCheck} />}
      </button>
    </>
  );
};

export default ToDoCircle;
