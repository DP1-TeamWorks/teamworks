import React, { useState, useEffect } from "react";
import ProjectApiUtils from "../../utils/api/ProjectApiUtils";
import TagApiUtils from "../../utils/api/TagApiUtils";
import ToDoApiUtils from "../../utils/api/ToDoApiUtils";
import UserApiUtils from "../../utils/api/UserApiUtils";
import NewMessageForm from "./NewMessageForm";

//UseEffect que traiga usuarios
//pickeddepartment useState
//pickedProject useState

const NewMessage = ({ ChangeModalNewMessage }) => {
  const [mailOptions, setMailOptions] = useState([{}]);
  const [toDoOptions, setToDoOptions] = useState([{}]);
  const [tagOptions, setTagOptions] = useState([{}]);

  useEffect(() => {
    UserApiUtils.getMyTeamUsers()
      .then((res) => {
        console.log("Getting user mails");
        let mailList = res.map((user) => {
          console.log(user);
          return { label: user.name + " - " + user.email, value: user.email };
        });
        setMailOptions(mailList);
      })
      .catch((error) => {
        console.log("ERROR: cannot get the users mails");
      });
  }, [ChangeModalNewMessage]);

  useEffect(() => {
    TagApiUtils.getAllMyTags()
      .then((res) => {
        console.log("Getting user tags");
        console.log(res);
        let taglist = Object.entries(res).map((proyecto) => {
          console.log(proyecto);
          return proyecto[1].map((tag) => {
            console.log(tag);
            return {
              label: tag.title + ": (" + proyecto[0] + ")",
              value: tag.id,
            };
          });
        });
        console.log(taglist[0]);
        setTagOptions(taglist[0]);
      })
      .catch((error) => {
        console.log("ERROR: cannot get the user's tags");
        console.log(error);
      });
  }, [ChangeModalNewMessage]);

  useEffect(() => {
    ToDoApiUtils.getAllMyToDos()
      .then((res) => {
        console.log("Getting user toDos");
        let toDoList = Object.entries(res).map((proyecto) => {
          console.log(proyecto);
          return proyecto[1].map((toDo) => {
            return {
              label: toDo.title + ": (" + proyecto[0] + ")",
              value: toDo.id,
            };
          });
        });
        console.log(toDoList[0]);
        setToDoOptions(toDoList[0]);
      })
      .catch((error) => {
        console.log("ERROR: cannot get the user's todos");
      });
  }, [ChangeModalNewMessage]);

  return (
    <div className="NewMsgModal">
      <div className="NewMsgContainer">
        <img
          className="CloseNewMsg"
          onClick={ChangeModalNewMessage}
          alt=""
          src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDMyOS4yNjkzMyAzMjkiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTIiIHhtbDpzcGFjZT0icHJlc2VydmUiIGNsYXNzPSIiPjxnPjxwYXRoIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgZD0ibTE5NC44MDA3ODEgMTY0Ljc2OTUzMSAxMjguMjEwOTM4LTEyOC4yMTQ4NDNjOC4zNDM3NS04LjMzOTg0NCA4LjM0Mzc1LTIxLjgyNDIxOSAwLTMwLjE2NDA2My04LjMzOTg0NC04LjMzOTg0NC0yMS44MjQyMTktOC4zMzk4NDQtMzAuMTY0MDYzIDBsLTEyOC4yMTQ4NDQgMTI4LjIxNDg0NC0xMjguMjEwOTM3LTEyOC4yMTQ4NDRjLTguMzQzNzUtOC4zMzk4NDQtMjEuODI0MjE5LTguMzM5ODQ0LTMwLjE2NDA2MyAwLTguMzQzNzUgOC4zMzk4NDQtOC4zNDM3NSAyMS44MjQyMTkgMCAzMC4xNjQwNjNsMTI4LjIxMDkzOCAxMjguMjE0ODQzLTEyOC4yMTA5MzggMTI4LjIxNDg0NGMtOC4zNDM3NSA4LjMzOTg0NC04LjM0Mzc1IDIxLjgyNDIxOSAwIDMwLjE2NDA2MyA0LjE1NjI1IDQuMTYwMTU2IDkuNjIxMDk0IDYuMjUgMTUuMDgyMDMyIDYuMjUgNS40NjA5MzcgMCAxMC45MjE4NzUtMi4wODk4NDQgMTUuMDgyMDMxLTYuMjVsMTI4LjIxMDkzNy0xMjguMjE0ODQ0IDEyOC4yMTQ4NDQgMTI4LjIxNDg0NGM0LjE2MDE1NiA0LjE2MDE1NiA5LjYyMTA5NCA2LjI1IDE1LjA4MjAzMiA2LjI1IDUuNDYwOTM3IDAgMTAuOTIxODc0LTIuMDg5ODQ0IDE1LjA4MjAzMS02LjI1IDguMzQzNzUtOC4zMzk4NDQgOC4zNDM3NS0yMS44MjQyMTkgMC0zMC4xNjQwNjN6bTAgMCIgZmlsbD0iI2ZmZmZmZiIgZGF0YS1vcmlnaW5hbD0iIzAwMDAwMCIgc3R5bGU9IiIgY2xhc3M9IiI+PC9wYXRoPjwvZz48L3N2Zz4="
        />
        <NewMessageForm
          key={JSON.stringify(mailOptions + toDoOptions + tagOptions)}
          mailOptions={mailOptions}
          toDoOptions={toDoOptions}
          tagOptions={tagOptions}
          ChangeModalNewMessage={ChangeModalNewMessage}
        />
      </div>
    </div>
  );
};

export default NewMessage;
