import "./SubsettingContainer.css";

const SidePaneElement = ({children, selected, highlighted, elementDiv, reducedpadding, onClick}) =>
{
    let className = "SidePaneElement ";
    if (selected)
        className += "SidePaneElement--Selected";
    if (highlighted)
        className += "SidePaneElement--Highlighted";
    if (reducedpadding)
        className += "SidePaneElement--ReducedPadding";
    
    if (elementDiv)
        return <div className={className} onClick={onClick}>{children}</div>;
    else
        return <p className={className} onClick={onClick}>{children}</p>;
};

export default SidePaneElement;