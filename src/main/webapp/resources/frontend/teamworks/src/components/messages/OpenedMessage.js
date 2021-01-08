import React from "react";
import GradientButton from "../buttons/GradientButton";

const OpenedMessage = ({ msg, reply, forward }) => {
  return (
    <>
      <div className="MsgContentSentTo">
        <span style={{ fontWeight: "bold" }}>
          Sent to{" "}
          {msg.recipients.map((r) => {
            return <b> {r.name} </b>; // TODO: habría q controlar que seamos nosotros mismos el recipient
          })}
        </span>
      </div>
      <div className="MsgContentText">
        <text>{msg.text}</text>
      </div>
      <div className="MsgContentButtons">
        <GradientButton onClick={null} className="GradientButton--Spaced">
          Reply
        </GradientButton>
        <GradientButton onClick={null} className="GradientButton--Spaced">
          Forward
        </GradientButton>
      </div>
    </>
  );
};

export default OpenedMessage;
