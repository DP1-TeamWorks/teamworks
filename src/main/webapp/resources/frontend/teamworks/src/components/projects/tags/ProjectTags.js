import React, { useState } from "react";
import Tag from "./Tag";
import InboxSidebarTab from "../../sidebar/InboxSidebarTab";

import "./Tags.css";

const ProjectTags = ({ tagList, selectedTab, setSelectedTab }) => {
  return (
    <>
      <h3 className="SidebarSectionTitle">Tags</h3>
      {tagList.map((tag) => {
        return (
          <InboxSidebarTab
            text={tag.id}
            selectedTab={selectedTab}
            setSelectedTab={setSelectedTab}
            isTag={true}
          >
            <Tag
              title={tag.title}
              color={tag.color}
              selectedTab={selectedTab}
              setSelectedTab={setSelectedTab}
              noOpenedMessages={tag.noOpenedMessages}
            />
          </InboxSidebarTab>
        );
      })}
    </>
  );
};

export default ProjectTags;
