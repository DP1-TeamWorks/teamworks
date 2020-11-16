import React, { useState } from "react";
import "./chat.css";
export function Chat() {
  const [collapseStatus, setCollapseStatus] = useState("");

  const collapse = () => {
    if (collapseStatus === " active") setCollapseStatus("");
    else setCollapseStatus(" active");
  };
  return (
    <React.Fragment>
      <div className={"chatBox" + collapseStatus} onClick={collapse}>
        <button onClick={collapse}></button>
        <div className={"messageBox" + collapseStatus}></div>
      </div>
    </React.Fragment>
  );
}
