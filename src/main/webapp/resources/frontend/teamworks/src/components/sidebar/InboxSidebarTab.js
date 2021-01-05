import { useLocation, withRouter } from "react-router-dom";
import "./SidebarTab.css";

const InboxSidebarTab = ({
  text,
  selectedTab,
  setSelectedTab,
  isTag,
  children,
}) => {
  console.log(selectedTab);
  let className = isTag ? "SidebarTab SidebarTab--Tag" : "SidebarTab";
  if (selectedTab === text) {
    className += " SidebarTab--Selected";
  }
  console.log("HELLO" + selectedTab + isTag + text);

  return (
    <div className={className} onClick={() => setSelectedTab(text)}>
      {children}
    </div>
  );
};

export default InboxSidebarTab;
