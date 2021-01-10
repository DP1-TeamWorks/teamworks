import React, { useState } from "react";
import MessageApiUtils from "../../utils/api/MessageApiUtils";
import Circle from "../projects/tags/Circle";
import OpenedMessage from "./OpenedMessage";
const MessagePreview = ({ msg, openMessage, setOpenMessage }) => {
  const [read, setRead] = useState(msg.read);

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
    if (!msg.read) {
      setRead(true);
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
          {msg.sender.name} - {msg.sender.teamname}
        </h4>
        <h5 className="MsgSubject"> {msg.subject} </h5>
        <div style={{ float: "right" }}>
          {msg.tags.forEach((tag) => {
            console.log(tag);
            return <Circle color={tag.color} />;
          })}
        </div>
        <h5 className="MsgDateTime" style={{ float: "right" }}>
          {msg.date} - {msg.time}
        </h5>
      </div>
      <div
        className={isOpen() ? "MsgContent" : "MsgContent MsgContent--Collapsed"}
      >
        <OpenedMessage msg={msg} reply={reply} forward={forward} />
      </div>
    </>
  );
};

export default MessagePreview;
