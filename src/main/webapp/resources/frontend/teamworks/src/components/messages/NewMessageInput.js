import React, { useState } from "react";
import Input from "../forms/Input";

export default function NewMessageInput({name, placeholder}) {
  return (
    <div className="InputContainer">
        <p className="TitleNewMsg">{name}</p>
        <Input placeholder={placeholder} styleClass="Input InputNewMsg"></Input>
    </div>
  );
};
