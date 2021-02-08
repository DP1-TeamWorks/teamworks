import React, { useEffect, useState } from "react";
import ToDo from "./ToDo";
import "./ToDos.css";
import AddToDoForm from "./AddToDoForm";
import ToDoApiUtils from "../../../utils/api/ToDoApiUtils";
import MilestoneApiUtils from "../../../utils/api/MilestoneApiUtils";

const MyProjectToDos = ({ projectId }) => {
  const [milestone, setMilestone] = useState({});
  const [toDoList, setToDoList] = useState([]);
  const [reloadToDos, setReloadToDos] = useState(false);

  useEffect(() => {
    console.log("GETTING NEXT MILESTONE");
    MilestoneApiUtils.getNextMilestone(projectId)
      .then((res) => {
        setMilestone(res);
        console.log("GETTING TODOs");
        ToDoApiUtils.getMyToDos(res.id)
          .then((res) => {
            console.log(res);
            setToDoList(res);
          })
          .catch((error) => {
            console.log("ERROR: Cannot get myToDos");
            console.log(error);
            setToDoList([]);
          });
      })
      .catch((error) => {
        console.log("ERROR: cannot get the next milestone");
        console.log(error);
        setMilestone({});
        setToDoList([]);
      });
  }, [projectId]);

  useEffect(() => {
    if (reloadToDos && milestone.id) {
      console.log("GETTING TODOs");
      ToDoApiUtils.getMyToDos(milestone.id)
        .then((res) => {
          console.log(res);
          setToDoList(res);
        })
        .catch((error) => {
          console.log("ERROR: Cannot get myToDos");
          console.log(error);
          setToDoList([]);
        });

      setReloadToDos(false);
    }
  }, [milestone, reloadToDos]);

  return (
    <div style={{ display: Object.keys(milestone).length === 0 ? "none" : "" }}>
      <h3 className="SidebarSectionTitle" style={{ display: "inline-block" }}>
        ToDo
      </h3>
      <span className="MilestoneDate"> for {milestone.dueFor} </span>
      {toDoList.map((toDo) => {
        return (
          <ToDo
            key={toDo.id}
            id={toDo.id}
            title={toDo.title}
            tagList={toDo.tags}
            done={toDo.done}
          />
        );
      })}
      <AddToDoForm milestoneId={milestone.id} setReloadToDos={setReloadToDos} />
    </div>
  );
};

export default MyProjectToDos;
