import React, { useState } from "react";
import Input from "../forms/Input";

const NewMessage = () => {
  return (
    /*añadir el div solo a section lighter*/
      <div className="NewMsgModal">
        <div className="NewMsgContainer">
          <Input placeholder="Users" styleClass="Input"></Input>
          <div className="SecondLineFlex">
            <Input placeholder="DPT" styleClass="Input"></Input>
            <Input placeholder="Project" styleClass="Input"></Input>
            <Input placeholder="Tags" styleClass="Input"></Input>
          </div>
          <div className="NewMsg">
          <Input styleClass="Input NewMsgHead" placeholder="How are you?"></Input> /*usar label para el ::before */
          <Input styleClass="Input NewMsgBody"></Input>
          </div>
        </div>
      </div>
  );
};

export default NewMessage;
