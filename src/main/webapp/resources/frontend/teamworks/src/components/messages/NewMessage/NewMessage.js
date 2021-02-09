import React, { useState, useEffect } from "react";
import TagApiUtils from "../../../utils/api/TagApiUtils";
import ToDoApiUtils from "../../../utils/api/ToDoApiUtils";
import UserApiUtils from "../../../utils/api/UserApiUtils";
import NewMessageForm from "./NewMessageForm";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";

const NewMessage = ({ ChangeModalNewMessage }) => {
  const [mailOptions, setMailOptions] = useState([]);
  const [toDoOptions, setToDoOptions] = useState([]);
  const [tagOptions, setTagOptions] = useState([]);

  useEffect(() => {
    UserApiUtils.getUsers()
      .then((res) => {
        console.log("Getting user mail options");
        let mailList = res.map((user) => {
          return { label: user.name + " - " + user.email, value: user.email };
        });
        setMailOptions(mailList);
        console.log(mailList);
      })
      .catch((error) => {
        console.log("ERROR: cannot get the users mails");
        console.log(error);
      });
  }, [ChangeModalNewMessage]);

  useEffect(() => {
    TagApiUtils.getAllMyTags()
      .then((res) => {
        console.log("Getting user tags options");
        let taglist = Object.entries(res).map((proyecto) => {
          return proyecto[1].map((tag) => {
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
        console.log(error);
      });
  }, [ChangeModalNewMessage]);

  return (
    <div className="NewMsgModal">
      <div className="NewMsgContainer">
        <div
          onClick={ChangeModalNewMessage}
          style={{ display: "block", marginBottom: "10px" }}
        >
          <FontAwesomeIcon
            icon={faTimes}
            size={"10x"}
            style={{ float: "right", fontSize: "18" }}
          />
        </div>
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
