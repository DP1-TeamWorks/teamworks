import React, { useState } from "react";
import Tag from "./Tag";
import "./Tags.css";

const ProjectTags = ({ tagList}) => {
  return (
    <>
      <h3 className="SidebarSectionTitle">Tags</h3>
      {tagList.map((tag) => {
        return (
          <Tag
            title={tag.title}
            color={tag.color}
            noOpenedMessages={tag.noOpenedMessages}
          />
        );
      })}
    </>
  );
};

export default ProjectTags;
