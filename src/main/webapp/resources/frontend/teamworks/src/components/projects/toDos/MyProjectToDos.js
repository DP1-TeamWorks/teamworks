import React, { useEffect, useState } from "react";
import ToDo from "./ToDo";
import "./ToDos.css";
import AddToDoForm from "./AddToDoForm";
import ToDoApiUtils from "../../../utils/api/ToDoApiUtils";
import MilestoneApiUtils from "../../../utils/api/MilestoneApiUtils";

const MyProjectToDos = ({ projectId }) => {
  const [milestone, setMilestone] = useState({
    date: "13/12/2010",
  });
  const [toDoList, setToDoList] = useState([
    {
      id: 1,
      title: "Plan a meeting",
      tags: [{ title: "Planning", color: "#FFD703" }],
    },
    {
      id: 2,
      title: "Go to have breakfast",
      tags: [
        { title: "Planning", color: "#FFD703" },
        { title: "Planning", color: "#DDFFDD" },
      ],
    },
    {
      id: 3,
      title: "Work with my Team",
      tags: [],
    },
  ]);
  const [reloadToDos, setReloadToDos] = useState(true);

  useEffect(() => {
    console.log("GETTING NEXT MILESTONE");
    MilestoneApiUtils.getNextMilestone(projectId)
      .then((res) => {
        setMilestone(res.data);
      })
      .catch((error) => {
        console.log("ERROR: cannot get the next milestone");
        console.log(error);
      });
  }, [projectId]);

  useEffect(() => {
    if (reloadToDos && milestone.id) {
      console.log("GETTING TODOs");
      console.log(milestone);
      ToDoApiUtils.getMyToDos(milestone.id)
        .then((res) => {
          console.log(res);
          setToDoList(res.data);
        })
        .catch((error) => {
          console.log("ERROR: Cannot get myToDos");
          console.log(error);
        });

      setReloadToDos(false);
    }
  }, [milestone, reloadToDos]);

  return (
    <>
      <h3 className="SidebarSectionTitle" style={{ display: "inline-block" }}>
        ToDo
      </h3>
      <span className="MilestoneDate"> for {milestone.dueFor} </span>
      {toDoList.map((toDo) => {
        return <ToDo id={toDo.id} title={toDo.title} tagList={toDo.tags} />;
      })}
      <AddToDoForm milestoneId={milestone.id} setReloadToDos={setReloadToDos} />
    </>
  );
};

export default MyProjectToDos;
