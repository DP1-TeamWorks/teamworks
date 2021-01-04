import "./SubsettingContainer.css";

const SidePaneElement = ({children, selected}) =>
{
    let className = "SidePaneElement ";
    if (selected)
        className += "SidePaneElement--Selected";
    return <p className={className}>{children}</p>;
};

export default SidePaneElement;