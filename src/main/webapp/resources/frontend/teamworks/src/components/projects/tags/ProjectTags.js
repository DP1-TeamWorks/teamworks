import React, { useState } from "react";
import Tag from "./Tag";
import InboxSidebarTab from "../../sidebar/InboxSidebarTab";

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
      <h3
        style={{ display: tagList <= 0 ? "none" : "inline-block" }}
        className="SidebarSectionTitle"
      >
        Tags
      </h3>
      {tagList.map((tag) => {
        return (
          <InboxSidebarTab
            key={tag.id}
            text={tag.id}
            selectedTab={selectedTab}
            setSelectedTab={setSelectedTab}
            isTag={true}
          >
            <Tag
              key={tag.id}
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
