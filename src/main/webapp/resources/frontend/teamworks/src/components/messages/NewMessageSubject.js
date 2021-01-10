import React, { useState } from "react";
import Input from "../forms/Input";
import NewMessageInput from "./NewMessageInput";

export default function NewMessageSubject({ name }) {
  return (
    <div className="NewMsgHeadLabel">
      <span className="NewMsgHeadText">{name}</span>
      <Input
        styleClass="Input NewMsgHead InputNewMsg"
        placeholder="How are you?"
      ></Input>
      <NewMessageInput name="ToDo" placeholder="ToDos"></NewMessageInput>
    </div>
  );
}
