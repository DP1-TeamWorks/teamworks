import React, { useState } from "react";
import "../../FontStyles.css";
import "./EditableField.css";
import "../forms/Forms.css";
import GradientButton from "../buttons/GradientButton";
import { useEffect } from "react";

const EditableField = ({value, inputType, smaller, editable, apiFunction, fieldName}) => {
  function onOkClicked() {
    if (!apiFunction || !fieldName)
      return;
    var postObj = {};
    postObj[fieldName] = currentVal;
    apiFunction(postObj).catch(() =>
    {
      setErrored(true);
      setActualVal(actualVal); // set the old values back
      setCurrentVal(actualVal);
      setTimeout(() => setErrored(false), 3000);
    });
    setActualVal(actualVal);
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

  useEffect(() =>
  {
    if (value)
    {
      setCurrentVal(value);
      setActualVal(value);
      setLoading(false);
    }
  }, [value]);

  const [editing, setEditing] = useState(false);
  const [actualVal, setActualVal] = useState(value);
  const [currentVal, setCurrentVal] = useState(value);
  const [loading, setLoading] = useState(true);
  const [copied, setCopied] = useState(false);
  const [errored, setErrored] = useState(null);

  if (editing) {
    return (
      <div className={`EditableField EditableField--Editing ${smaller ? "EditableField--Smaller" : ""}`}>
        <input
          className="Input EditingInput"
          type={inputType ?? "text"}
          defaultValue={currentVal}
          onChange={(e) => setCurrentVal(e.target.value)}
          onKeyDown={(e) => { if (e.key == "Enter") onOkClicked() }}
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
        <h3 className={`BoldTitle ${smaller ? "BoldTitle--Smaller" : ""} ${loading ? "LoadingPlaceholder" : ""}`}>{currentVal}</h3>
        {penIcon}
        {copied ? <span className="CopiedSpan">Copied</span> : ""}
        {errored ? <span className="CopiedSpan CopiedSpan--Error">Something went wrong updating the value. Try again?</span> : ""}
      </div>
    );
  }
};

export default EditableField;
