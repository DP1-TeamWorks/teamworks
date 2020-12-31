import React from "react";

const SidebarSection = (props) => {
  return (
    <div className="SidebarSection">
      <SidebarSectionTitle title={props.title} />
    </div>
  );
};

export default SidebarSection;
