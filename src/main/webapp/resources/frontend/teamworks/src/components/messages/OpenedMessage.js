import React, { useState } from "react";

const OpenedMessage = ({ msg }) => {
  return (
    <div className="MessageContent">
      <p>{msg.content}</p>
    </div>
  );
};

export default OpenedMessage;
