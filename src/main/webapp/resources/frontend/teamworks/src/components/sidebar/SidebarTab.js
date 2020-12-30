import { useLocation, withRouter } from "react-router-dom";
import "./SidebarTab.css";

const SidebarTab = ({text, path, history, to}) =>
{
    const location = useLocation();
    
    let className = "SidebarTab";
    if (location.pathname === path)
    {
        className += " SidebarTab--Selected";
    }

    return <div className={className} onClick={() => history.push(path)}>{text}</div>
}

export default withRouter(SidebarTab);