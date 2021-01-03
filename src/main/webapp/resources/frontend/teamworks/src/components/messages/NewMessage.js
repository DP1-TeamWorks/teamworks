import React, { useState } from "react";

const NewMessage = () => {
    return (
        /*añadir el div solo a section lighter*/
        <div>
            <div className="NewMsgContainer">
                <div className="NewMsgTo">
                </div>
                <div className="SecondLineFlex">
                    <div className="NewMsgDepartment">
                    </div>
                    <div className="NewMsgProject"></div>
                    <div className="NewMsgTags"></div>
                </div>
                <div className="NewMsgSubject"></div>
                <div className="NewMsgBody"></div>
                send button and upload
            </div>
            <div className="NewMsgBackground">
            </div>
        </div>
    );
  };
  


export default NewMessage;