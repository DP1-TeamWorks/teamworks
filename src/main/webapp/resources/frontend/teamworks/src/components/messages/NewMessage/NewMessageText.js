import React, { useState } from "react";
import InputError from "../../forms/InputError";

export default function NewMessageText({ name, changeHandler, error }) {
  return (
    <div className="NewMsgTextLabel">
      <span className="NewMsgBodyText">{name}</span>
      <textarea
        name={name}
        className="Input NewMsgBody InputNewMsg"
        form="NewMsgForm"
        onChange={(e) => changeHandler(name, e.target.value)}
      ></textarea>
      <InputError error={error} />
    </div>
  );
}
