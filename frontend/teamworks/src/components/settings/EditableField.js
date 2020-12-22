import "../../FontStyles.css";
import "../../sections/Settings.css";

const EditableField = (props) =>
{
    return (
        <div className="EditableField">
            <h3 className="BoldTitle">{props.value}</h3>
            <i className="fas fa-pen PenIcon"></i>
        </div>
    )
}

export default EditableField;