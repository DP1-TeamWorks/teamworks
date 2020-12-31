import React, { useState } from "react";
import Tag from "./Tag";
import "./Tags.css";

const ProjectTags = ({ projectId, tagColor, tagTitle }) => {
  const [tagList, setTagList] = useState([
    { title: "Planning", color: "#FFD703", noOpenedMessages: 25 },
    { title: "Planning", color: "#DDFFDD", noOpenedMessages: 12 },
    { title: "Planning", color: "#AAD7F3", noOpenedMessages: 43 },
  ]);
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
