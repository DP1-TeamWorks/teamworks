import React, { useState } from "react";
import "../../FontStyles.css";
import "./EditableField.css";
import "../forms/Forms.css";
import GradientButton from "../buttons/GradientButton";

const EditableField = ({value, inputType, smaller}) => {
  // TODO: Enter submit
  function onOkClicked() {
    setEditing(false);
  }

  const [editing, setEditing] = useState(false);
  const [currentVal, setCurrentVal] = useState(value);
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
    return (
      <div className={`EditableField ${smaller ? "EditableField--Smaller" : ""}`} onClick={() => setEditing(true)}>
        <h3 className={`BoldTitle ${smaller ? "BoldTitle--Smaller" : ""}`}>{currentVal}</h3>
        <i className="fas fa-pen PenIcon"></i>
      </div>
    );
  }
};

export default EditableField;
