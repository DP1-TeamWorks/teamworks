import React from "react"
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

  return (
    <div className={className} onClick={() => setSelectedTab(text)}>
      {children}
    </div>
  );
};

export default InboxSidebarTab;
