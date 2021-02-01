import "../../FontStyles.css";
import "../../sections/Settings.css";

const SettingGroup = (props) =>
{
    let title;
    if (props.danger)
    {
        title = <h3 className="SmallTitle"><span className="DangerTitle">DANGER ZONE</span> – {props.name}</h3>
    }
    else
    {
        title = <h3 className="SmallTitle">{props.name}</h3>
    }

    let descriptionP;
    if (props.description)
    {
        descriptionP = <p className="SettingGroup__P" dangerouslySetInnerHTML={{__html: props.description}}></p>
    }
    return (
        <div className="EditableSettingGroup">
            {title}
            {descriptionP}
            {props.children}
        </div>
    )
}

export default SettingGroup;