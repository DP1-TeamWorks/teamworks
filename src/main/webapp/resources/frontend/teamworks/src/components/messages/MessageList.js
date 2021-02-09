import React, { useState } from "react";
import MessagePreview from "./MessagePreview";
import "./Messages.css";
import Spinner from "../spinner/Spinner";

const MessageList = ({ setReloadCounters, messages }) => {
  const [openMessage, setOpenMessage] = useState("");


  if (!messages)
  {
    return <Spinner />
  }

  let messageList = messages.map((msg) => {
    return (
      <MessagePreview
        key={msg.id}
        msg={msg}
        openMessage={openMessage}
        setOpenMessage={setOpenMessage}
        setReloadCounters={setReloadCounters}
      />
    );
  });

  return (
    <div className="MsgListContainer">
      {messageList}
    </div>
  );
};

export default MessageList;
