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
    return (
        <div className="EditableSettingGroup">
            {title}
            <p className="SettingGroup__P" dangerouslySetInnerHTML={{__html: props.description}}></p>
            {props.children}
        </div>
    )
}

export default SettingGroup;