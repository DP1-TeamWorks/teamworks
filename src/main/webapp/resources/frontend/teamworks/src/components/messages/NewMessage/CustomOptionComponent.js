import TagTab from "../../settings/milestone/TagTab"

const CustomOptionComponent = ({ value, label, tagColor, project }, {context}) =>
{
    let isValue = context === "value";
    let color = isValue ? "#222" : "#a6ce56";
    if (tagColor)
    {
        let text = label;
        if (project)
            text += `– ${project}`;
        return (
            <div style={{ display: "flex" }}>
                <div><TagTab light={isValue} inline unselectable color={tagColor}>{text}</TagTab></div>
            </div>
        );
    } else
    {
        return (
            <div style={{ display: "flex" }}>
                <div style={{ color }}>{label}</div>
            </div>
        );
    }

}

export default CustomOptionComponent;