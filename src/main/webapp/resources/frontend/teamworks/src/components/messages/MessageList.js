import React, { useState } from "react";
import MessagePreview from "./MessagePreview";
import "./Messages.css";

const MessageList = ({ messages }) => {
  const [openMessage, setOpenMessage] = useState("")
  return (
    <div className="MsgListContainer">
      {messages.map((msg) => {
        return <MessagePreview msg={msg} openMessage={openMessage} setOpenMessage={setOpenMessage} />;
      })}
    </div>
  );
};

export default MessageList;
