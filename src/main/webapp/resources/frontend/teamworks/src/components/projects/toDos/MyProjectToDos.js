import React, { useState } from "react";
import ToDo from "./ToDo";
import "./ToDos.css";

const MyProjectToDos = ({ projectId, tagColor, tagTitle }) => {
  const [toDoList, setToDoList] = useState([
    {
      title: "Plan a meeting",
      tagList: [
        { title: "Planning", color: "#FFD703" },
        { title: "Planning", color: "#DDFFDD" },
      ],
    },
    {
      title: "Go to have breakfast",
      tagList: [
        { title: "Planning", color: "#FFD703" },
        { title: "Planning", color: "#DDFFDD" },
      ],
    },
    {
      title: "Work with my Team",
      tagList: [
        { title: "Planning", color: "#FFD703" },
        { title: "Planning", color: "#DDFFDD" },
      ],
    },
  ]);
  return (
    <>
      <h3 className="SidebarSectionTitle">ToDo</h3>
      {toDoList.map((toDo) => {
        return <ToDo title={toDo.title} tagList={toDo.tagList} />;
      })}
    </>
  );
};

export default MyProjectToDos;
