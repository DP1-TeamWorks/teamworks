import React, { useEffect, useState } from "react";
import MessageApiUtils from "../../utils/api/MessageApiUtils";
import Circle from "../projects/tags/Circle";
import OpenedMessage from "./OpenedMessage";
const MessagePreview = ({
  msg,
  openMessage,
  setOpenMessage,
  setReloadCounters,
}) => {
  const [read, setRead] = useState();

  useEffect(() => {
    setRead(msg.read);
  }, [msg]);

  const isOpen = () => {
    return openMessage === msg.id;
  };

  const reply = () => {
    // TODO: Open the modal with predefined recipient
  };

  const forward = () => {
    // TODO: Open the modal with predifined text message
  };

  const collapseMessage = () => {
    isOpen() ? setOpenMessage("") : setOpenMessage(msg.id);
    setReloadCounters(true);
    if (!read) {
      MessageApiUtils.markMessageAsRead(msg.id)
        .then((res) => {
          setRead(true);
        })
        .catch((error) => {
          console.log("ERROR: cannot mark the message as read");
        });
    }
  };

  return (
    <>
      <div
        className={
          isOpen()
            ? "MsgPreviewContainer MsgPreviewContainer--Active"
            : read
            ? "MsgPreviewContainer MsgPreviewContainer--Read"
            : "MsgPreviewContainer"
        }
        onClick={collapseMessage}
      >
        <div className="SelectBox" />
        <h4 className="MsgTitle">
          {msg.sender.name ? msg.sender.name : "Usuario"}{" "}
          {msg.sender.lastname ? msg.sender.lastname : "Eliminado"} -{" "}
          {msg.sender.email ? msg.sender.email : "deleted@user"}
        </h4>
        <h5 className="MsgSubject"> {msg.subject} </h5>

        <h5 className="MsgDateTime" style={{ float: "right" }}>
          {msg.timestamp}
        </h5>
        <div className="MsgTags" style={{ float: "right" }}>
          {msg.tags.map((tag) => {
            return <Circle key={tag.id} color={tag.color} />;
          })}
        </div>
      </div>
      <div
        className={isOpen() ? "MsgContent" : "MsgContent MsgContent--Collapsed"}
      >
        <OpenedMessage key={msg.id} msg={msg} reply={reply} forward={forward} />
      </div>
    </>
  );
};

export default MessagePreview;
