import React, { useState } from "react";
import Input from "../forms/Input";

export default function NewMessageText({name}) {
  return (
    <div className="NewMsgTextLabel">
              <span className="NewMsgBodyText">{name}</span>
              <textarea
                className="Input NewMsgBody InputNewMsg"
                form="NewMsgForm"
              ></textarea>
            </div>
  );
};