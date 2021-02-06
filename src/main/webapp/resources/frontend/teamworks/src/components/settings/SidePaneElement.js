import "./SubsettingContainer.css";

const SidePaneElement = ({children, selected, highlighted, onClick}) =>
{
    let className = "SidePaneElement ";
    if (selected)
        className += "SidePaneElement--Selected";
    if (highlighted)
        className += "SidePaneElement--Highlighted";
    return <p className={className} onClick={onClick}>{children}</p>;
};

export default SidePaneElement;