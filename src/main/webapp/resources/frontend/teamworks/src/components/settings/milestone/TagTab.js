import Circle from "../../projects/tags/Circle";

const TagTab = ({ children, color, selected, onClick, inline, unselectable, light }) =>
{
    let styleClass = `TagTab ${light ? "TagTab--Light" : ""} ${selected ? "TagTab--Selected" : ""} ${inline ? "TagTab--Inline" : ""} ${unselectable ? "TagTab--Unselectable" : ""}`;
    if (color)
    {
        return (
            <div
                className={styleClass}
                onClick={onClick}>
                <Circle
                    className="TagTab__Circle"
                    color={selected ? "#20290f" : color} />
                {children}
            </div>
        );
    } else 
    {
        return (
            <div
                className={styleClass}
                onClick={onClick}>
                {children}
            </div>
        );
    }
}

export default TagTab;