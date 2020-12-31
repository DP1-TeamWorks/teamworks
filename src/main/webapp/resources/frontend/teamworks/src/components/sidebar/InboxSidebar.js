import React from "react";
import Sidebar from "./Sidebar";
import Inboxes from "../inboxes/Inboxes";
import ProjectPicker from "../projects/ProjectPicker";
import MyProjectToDos from "../projects/toDos/MyProjectToDos";
import ProjectTags from "../projects/tags/ProjectTags";
import GradientButton from "../buttons/GradientButton";
import SidebarSection from "./SidebarSection";

const InboxSidebar = (props) => {
  return (
    <Sidebar>
      <GradientButton onClick={props.createNewMessage} className="MainButton">
        NEW MESSAGE
      </GradientButton>

      <hr className="Separator" />

      <Inboxes />
      <ProjectPicker />
      <ProjectTags />
      <MyProjectToDos />
    </Sidebar>
  );
};

export default InboxSidebar;
