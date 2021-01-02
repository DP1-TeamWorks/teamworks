import React, { useState } from "react";
import Circle from "../projects/tags/Circle";
import OpenedMessage from "./OpenedMessage";
const MessagePreview = ({ msg }) => {
  const [collapsed, setCollapsed] = useState(true);
  const collapseMessage = () => {
    setCollapsed(!collapsed);
  };
  return (
    <>
      <div className="MsgPreviewContainer" onClick={collapseMessage}>
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
        className="MsgContent"
        style={{ maxHeight: collapsed ? "0px" : "none" }}
      >
        <OpenedMessage msg={msg} />
      </div>
    </>
  );
};

export default MessagePreview;
