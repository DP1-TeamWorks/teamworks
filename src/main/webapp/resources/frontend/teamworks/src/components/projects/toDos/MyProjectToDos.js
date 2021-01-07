import React, { useEffect, useState } from "react";
import ProjectApiUtils from "../../../utils/api/ProjectApiUtils";
import ToDo from "./ToDo";
import "./ToDos.css";
import AddToDoForm from "./AddToDoForm";

const MyProjectToDos = ({ projectId }) => {
  const [toDoList, setToDoList] = useState([
    {
      id: 1,
      title: "Plan a meeting",
      tagList: [{ title: "Planning", color: "#FFD703" }],
    },
    {
      id: 2,
      title: "Go to have breakfast",
      tagList: [
        { title: "Planning", color: "#FFD703" },
        { title: "Planning", color: "#DDFFDD" },
      ],
    },
    {
      id: 3,
      title: "Work with my Team",
      tagList: [],
    },
  ]);
  const [reloadToDos, setReloadToDos] = useState(true);

  useEffect(() => {
    if (reloadToDos) {
      console.log("todo request");
      ProjectApiUtils.getMyToDos(projectId)
        .then((res) => {
          setToDoList(res.data);
        })
        .catch(console.log("ERROR: Cannot get myToDos from the API"));
    }

    setReloadToDos(false);
  }, [projectId, reloadToDos]);

  return (
    <>
      <h3 className="SidebarSectionTitle">ToDo</h3>
      {toDoList.map((toDo) => {
        return <ToDo id={toDo.id} title={toDo.title} tagList={toDo.tagList} />;
      })}
      <AddToDoForm setReloadToDos={setReloadToDos} />
    </>
  );
};

export default MyProjectToDos;
