import React, { useEffect, useState } from "react";
import ToDo from "./ToDo";
import "./ToDos.css";
import AddToDoForm from "./AddToDoForm";
import ToDoApiUtils from "../../../utils/api/ToDoApiUtils";
import MilestoneApiUtils from "../../../utils/api/MilestoneApiUtils";

const MyProjectToDos = ({ projectId }) => {
  const [milestone, setMilestone] = useState({
    date: new Date(),
  });
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
    MilestoneApiUtils.getNextMilestone(projectId)
      .then((res) => {
        setMilestone(res.data);
      })
      .catch((error) => {
        console.log("ERROR: cannot get the next milestone");
      });
  }, [projectId]);

  useEffect(() => {
    if (reloadToDos) {
      console.log("todo request");
      ToDoApiUtils.getMyToDos(milestone.id)
        .then((res) => {
          setToDoList(res.data);
        })
        .catch(console.log("ERROR: Cannot get myToDos from the API"));
    }

    setReloadToDos(false);
  }, [milestone, reloadToDos]);

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
