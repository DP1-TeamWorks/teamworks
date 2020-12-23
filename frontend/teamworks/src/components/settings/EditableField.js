import React, {useState} from "react";
import "../../FontStyles.css";
import "../../sections/Settings.css";
import "../Forms/forms.css";
import GradientButton from "../buttons/GradientButton";


const EditableField = (props) => // TODO: Enter submit
{
    function onOkClicked()
    {
        setEditing(false);
    }

    const [editing, setEditing] = useState(false);
    const [currentVal, setCurrentVal] = useState(props.value);
    if (editing)
    {
        return (
            <div className="EditingField">
                <input className="Input EditingInput" type={props.inputType ?? "text"} defaultValue={currentVal} onChange={e => setCurrentVal(e.target.value)} />
                <GradientButton className="EditingOkButton" onClick={() => onOkClicked()}>OK</GradientButton>
            </div>
        );
    }
    else
    {
        return (
            <div className="EditableField" onClick={() => setEditing(true)}>
                <h3 className="BoldTitle">{currentVal}</h3>
                <i className="fas fa-pen PenIcon"></i>
            </div>
        );
    }

    
}

export default EditableField;