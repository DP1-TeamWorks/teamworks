import React, { useState } from "react";
import "../../FontStyles.css";
import "./EditableField.css";
import "../forms/Forms.css";
import GradientButton from "../buttons/GradientButton";
import { useEffect } from "react";

const EditableField = ({value, inputType, smaller, editable, postFunction, fieldName, onUpdated}) => {
  function onOkClicked() {
    if (!postFunction || !fieldName || actualVal == currentVal)
    {
      setEditing(false);
      return;
    }
    var postObj = {};
    postObj[fieldName] = currentVal;
    postFunction(postObj)
    .catch(() =>
    {
      setErrored(true);
      setActualVal(actualVal); // set the old values back
      setCurrentVal(actualVal);
      if (onUpdated)
        onUpdated(actualVal);
      setTimeout(() => setErrored(false), 5000);
    });
    // Set values inmediately after update, dont wait for promise
    setActualVal(actualVal);
    setEditing(false);
    if (onUpdated)
      onUpdated(currentVal);
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
  const [actualVal, setActualVal] = useState(value);
  const [currentVal, setCurrentVal] = useState(value);
  const [loading, setLoading] = useState(!Boolean(value));
  const [copied, setCopied] = useState(false);
  const [errored, setErrored] = useState(null);

  let textInput;
  useEffect(() =>
  {
    if (editing)
      textInput.focus();
  }, [editing, textInput]);

  useEffect(() =>
  {
    if (value)
    {
      setCurrentVal(value);
      setActualVal(value);
      setLoading(false);
    }
  }, [value]);

  

  if (editing) {
    return (
      <div className={`EditableField EditableField--Editing ${smaller ? "EditableField--Smaller" : ""}`}>
        <input
          className="Input EditingInput EditingInput--MinusButton"
          type={inputType ?? "text"}
          defaultValue={currentVal}
          onChange={(e) => setCurrentVal(e.target.value)}
          onKeyDown={(e) => { if (e.key === "Enter") onOkClicked() }}
          ref={(input) => textInput = input}
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
