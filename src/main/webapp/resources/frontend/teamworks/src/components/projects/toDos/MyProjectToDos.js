import React, { useState } from "react";
import ToDo from "./ToDo";
import "./ToDos.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import Input from "../../forms/Input";

const MyProjectToDos = ({ myProjectToDoList }) => {
  const [newToDo, setNewToDo] = useState("");

  const addNewToDo = () => {
    const newToDoObject = {
      title: newToDo,
      tagList: [],
    };
    myProjectToDoList.push(newToDoObject);
    // TODO api call to update toDos
    console.log(myProjectToDoList);
  };

  return (
    <>
      <h3 className="SidebarSectionTitle">ToDo</h3>
      {myProjectToDoList.map((toDo) => {
        return <ToDo title={toDo.title} tagList={toDo.tagList} />;
      })}
      <button className="ToDoAdd" onClick={null}>
        <FontAwesomeIcon icon={faPlus} style={{ color: "lightgray" }} />
      </button>{" "}
      <form
        onSubmit={(event) => {
          event.preventDefault();
          console.log(newToDo);
          addNewToDo();
          event.target.reset();
          setNewToDo("");
        }}
        style={{ display: "inline-block" }}
      >
        <Input
          placeholder="Add new ToDo ..."
          styleClass="InputNewToDo"
          changeHandler={(event) => {
            setNewToDo(event.target.value);
          }}
        />
      </form>
    </>
  );
};

export default MyProjectToDos;
