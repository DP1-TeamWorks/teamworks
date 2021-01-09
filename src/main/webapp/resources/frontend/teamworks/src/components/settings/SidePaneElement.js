import "./SubsettingContainer.css";

const SidePaneElement = ({children, selected, highlighted}) =>
{
    let className = "SidePaneElement ";
    if (selected)
        className += "SidePaneElement--Selected";
    if (highlighted)
        className += "SidePaneElement--Highlighted";
    return <p className={className}>{children}</p>;
};

export default SidePaneElement;