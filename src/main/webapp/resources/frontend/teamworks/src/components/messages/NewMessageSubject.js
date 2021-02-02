import React, { useState } from "react";
import Input from "../forms/Input";
import NewMessageMultiSelect from "./NewMessageMultiSelect";

export default function NewMessageSubject({ name }) {
  return (
    <div className="NewMsgHeadLabel">
      <span className="NewMsgHeadText">{name}</span>
      <Input
        styleClass="Input NewMsgHead InputNewMsg"
        placeholder="How are you?"
      ></Input>
      <NewMessageMultiSelect name="todos" placeholder="ToDos"></NewMessageMultiSelect>
    </div>
  );
}
