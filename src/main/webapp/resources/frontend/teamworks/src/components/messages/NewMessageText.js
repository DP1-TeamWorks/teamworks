import React, { useState } from "react";
import Input from "../forms/Input";

export default function NewMessageText({name, changeHandler}) {
  return (
    <div className="NewMsgTextLabel">
              <span className="NewMsgBodyText">{name}</span>
              <textarea
                name={name}
                className="Input NewMsgBody InputNewMsg"
                form="NewMsgForm"
                onChange={(e) => changeHandler(name, e.target.value)}
              ></textarea>
            </div>
  );
};