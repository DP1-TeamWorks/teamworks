import React, { useState } from "react";
import Circle from "../projects/tags/Circle";

const MessagePreview = ({ msg }) => {
  return (
    <div className="MsgPreviewContainer">
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
  );
};

export default MessagePreview;
