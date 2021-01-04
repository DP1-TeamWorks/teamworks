import React, { useState } from "react";
import Sidebar from "./Sidebar";
import Inboxes from "../inboxes/Inboxes";
import ProjectPicker from "../projects/ProjectPicker";
import MyProjectToDos from "../projects/toDos/MyProjectToDos";
import ProjectTags from "../projects/tags/ProjectTags";
import GradientButton from "../buttons/GradientButton";
import SidebarSection from "./SidebarSection";
import NewMessage from "../messages/NewMessage";

const InboxSidebar = ({ setModalNewMessage, modalNewMessage }) => {
  const [pickedProject, setPickedProject] = useState({
    title: "TeamWorks1",
    tagList: [
      { title: "Planning", color: "#FFD703", noOpenedMessages: 25 },
      { title: "Planning", color: "#DDFFDD", noOpenedMessages: 12 },
      { title: "Planning", color: "#AAD7F3", noOpenedMessages: 43 },
    ],
    myProjectToDoList: [
      {
        title: "Plan a meeting",
        tagList: [{ title: "Planning", color: "#FFD703" }],
      },
      {
        title: "Go to have breakfast",
        tagList: [
          { title: "Planning", color: "#FFD703" },
          { title: "Planning", color: "#DDFFDD" },
        ],
      },
      {
        title: "Work with my Team",
        tagList: [],
      },
    ],
  });

  const ChangeModalNewMessage = () => {
    setModalNewMessage(!modalNewMessage);
  };

  return (
    <Sidebar>
      <GradientButton
        /*onClick={props.createNewMessage}*/ onClick={ChangeModalNewMessage}
        className="MainButton"
      >
        New Message
      </GradientButton>

      <hr className="Separator" />

      <Inboxes />
      <ProjectPicker
        pickedProject={pickedProject}
        setPickedProject={setPickedProject}
      />
      <ProjectTags tagList={pickedProject.tagList} />
      <MyProjectToDos myProjectToDoList={pickedProject.myProjectToDoList} />
    </Sidebar>
  );
};

export default InboxSidebar;
