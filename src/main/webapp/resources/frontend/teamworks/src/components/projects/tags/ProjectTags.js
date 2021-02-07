import React from "react";
import InboxSidebarTab from "../../sidebar/InboxSidebarTab";
import Tag from "./Tag";
import "./Tags.css";


const ProjectTags = ({
  tagList,
  selectedTab,
  setSelectedTab,
  reloadCounters,
  setReloadCounters,
}) => {
  return (
    <>
      <h3 style={{ display: tagList<=0 ? "none": "inline-block" }} className="SidebarSectionTitle">Tags</h3>
      {tagList.map((tag) => {
        return (
          <InboxSidebarTab
            text={tag.id}
            selectedTab={selectedTab}
            setSelectedTab={setSelectedTab}
            isTag={true}
          >
            <Tag
              id={tag.id}
              title={tag.title}
              color={tag.color}
              reloadCounters={reloadCounters}
              setReloadCounters={setReloadCounters}
            />
          </InboxSidebarTab>
        );
      })}
    </>
  );
};

export default ProjectTags;
