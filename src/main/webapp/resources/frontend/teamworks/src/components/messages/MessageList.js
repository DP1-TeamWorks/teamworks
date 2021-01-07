import React, { useState } from "react";
import MessagePreview from "./MessagePreview";
import "./Messages.css";

const MessageList = ({ messages }) => {
  return (
    <div className="MsgListContainer">
      {messages.map((msg) => {
        return <MessagePreview msg={msg} />;
      })}
    </div>
  );
};

export default MessageList;
