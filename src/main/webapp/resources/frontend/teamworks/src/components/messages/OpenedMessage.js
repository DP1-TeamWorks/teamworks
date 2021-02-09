import React from "react";
import { useEffect, useState } from "react/cjs/react.development";
import UserApiUtils from "../../utils/api/UserApiUtils";
import GradientButton from "../buttons/GradientButton";
import ForwardForm from "./ForwardForm";
import ReplyForm from "./ReplyForm";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faReply } from "@fortawesome/free-solid-svg-icons";
import { faForward } from "@fortawesome/free-solid-svg-icons";
import ToDo from "../projects/toDos/ToDo";
import FilePreview from "./FilePreview";

const OpenedMessage = ({ msg, setOpenMessage }) => {
  const [forwardOptions, setForwardOptions] = useState([]);
  const [messageOptions, setMessageOptions] = useState("");
  useEffect(() => {
    if (messageOptions === "Forward")
      UserApiUtils.getUsers()
        .then((res) => {
          console.log("Getting user mails");
          let mailList = res.map((user) => {
            return { label: user.name + " - " + user.email, value: user.email };
          });
          setForwardOptions(mailList);
          console.log(mailList);
        })
        .catch((error) => {
          console.log("ERROR: cannot get the users mails");
        });
  }, [messageOptions]);

  return (
    <>
      <div className="MsgContentSentTo">
        <span style={{ fontWeight: "bold" }}>
          Sent to{" "}
          {msg.strippedRecipients.map((r) => {
            return <b key={r.name}> {r.name} </b>;
          })}
        </span>
      </div>
      <div
        className="MsgContentText"
        dangerouslySetInnerHTML={{ __html: msg.text.replace(/\n/g, "<br>") }}
      ></div>
      <div className="MsgContentButtons">
        <GradientButton
          onClick={() => {
            messageOptions === "Reply"
              ? setMessageOptions("")
              : setMessageOptions("Reply");
          }}
          className="GradientButton--ReFor "
        >
          <FontAwesomeIcon icon={faReply} />
        </GradientButton>
        <GradientButton
          onClick={() => {
            messageOptions === "Forward"
              ? setMessageOptions("")
              : setMessageOptions("Forward");
          }}
          className="GradientButton--ReFor"
        >
          <FontAwesomeIcon icon={faForward} />
        </GradientButton>
      </div>
      <div className="MsgContentToDos">
        {msg.toDos.map((toDo) => {
          return (
            <ToDo
              key={toDo.id}
              id={toDo.id}
              tagList={toDo.tags}
              title={toDo.title}
              done={toDo.done}
              blocked={true}
            />
          );
        })}
      </div>
      <div className="MsgContentFiles">
        {msg.attatchments.map((file) => {
          return <FilePreview key={file.id} id={file.id} url={file.url} />;
        })}
      </div>
      {messageOptions === "Reply" && (
        <ReplyForm repliedMessage={msg} setOpenMessage={setOpenMessage} />
      )}
      {messageOptions === "Forward" && (
        <ForwardForm
          key={JSON.stringify(forwardOptions)}
          forwardOptions={forwardOptions}
          forwardedMessage={msg}
          setOpenMessage={setOpenMessage}
        />
      )}
    </>
  );
};

export default OpenedMessage;
