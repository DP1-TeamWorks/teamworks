import React, { useState } from "react";
import MessagePreview from "./MessagePreview";
import "./Messages.css";

const MessageList = ({ setReloadCounters, messages }) => {
  const [openMessage, setOpenMessage] = useState("");
  return (
    <div className="MsgListContainer">
      {messages.map((msg) => {
        return (
          <MessagePreview
            key={msg.id}
            msg={msg}
            openMessage={openMessage}
            setOpenMessage={setOpenMessage}
            setReloadCounters={setReloadCounters}
          />
        );
      })}
    </div>
  );
};

export default MessageList;
