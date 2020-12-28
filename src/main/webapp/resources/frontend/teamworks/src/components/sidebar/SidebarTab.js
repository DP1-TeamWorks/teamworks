import { useLocation } from "react-router-dom";
import "./SidebarTab.css";

const SidebarTab = ({text, path}) =>
{
    const location = useLocation();
    
    let className = "SidebarTab";
    if (location.pathname === path)
    {
        className += " SidebarTab--Selected";
    }

    return <div className={className}>{text}</div>
}

export default SidebarTab;