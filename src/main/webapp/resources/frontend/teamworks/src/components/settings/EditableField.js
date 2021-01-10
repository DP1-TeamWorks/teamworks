import React, { useState } from "react";
import "../../FontStyles.css";
import "./EditableField.css";
import "../forms/Forms.css";
import GradientButton from "../buttons/GradientButton";

const EditableField = ({value, inputType, smaller, editable}) => {
  // TODO: Enter submit
  function onOkClicked() {
    setEditing(false);
  }

  function enterEditingMode()
  {
    if (editable !== false) // editable = true when prop is undefined
    {
      setEditing(true)
    } else
    {
      setCopied(true);
      navigator.clipboard.writeText(currentVal);
      setTimeout(() => setCopied(false), 1000);
    }
  }

  const [editing, setEditing] = useState(false);
  const [currentVal, setCurrentVal] = useState(value);
  const [copied, setCopied] = useState(false);
  if (editing) {
    return (
      <div className={`EditableField ${smaller ? "EditableField--Smaller" : ""}`}>
        <input
          className="Input EditingInput"
          type={inputType ?? "text"}
          defaultValue={currentVal}
          onChange={(e) => setCurrentVal(e.target.value)}
        />
        <GradientButton
          className="EditingOkButton"
          onClick={() => onOkClicked()}
        >
          OK
        </GradientButton>
      </div>
    );
  } else {
    let penIcon;
    if (editable !== false)
    {
      penIcon = <i className="fas fa-pen PenIcon"></i>;
    }
    return (
      <div className={`EditableField ${smaller ? "EditableField--Smaller" : ""}`} onClick={() => enterEditingMode()}>
        <h3 className={`BoldTitle ${smaller ? "BoldTitle--Smaller" : ""}`}>{currentVal}</h3>
        {penIcon}
        {copied ? <span className="CopiedSpan">Copied</span> : ""}
      </div>
    );
  }
};

export default EditableField;
