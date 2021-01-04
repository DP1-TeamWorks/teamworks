import React, { useState } from "react";
import ToDo from "./ToDo";
import "./ToDos.css";

const MyProjectToDos = ({ myProjectToDoList }) => {
  return (
    <>
      <h3 className="SidebarSectionTitle">ToDo</h3>
      {myProjectToDoList.map((toDo) => {
        return <ToDo title={toDo.title} tagList={toDo.tagList} />;
      })}
    </>
  );
};

export default MyProjectToDos;
