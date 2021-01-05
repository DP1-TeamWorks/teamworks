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
  const [selectedTab, setSelectedTab] = useState("Inbox");
  const [pickedProject, setPickedProject] = useState({
    title: "TeamWorks1",
    tagList: [
      { title: "Planning", color: "#FFD703", noOpenedMessages: 25 },
      { title: "Cleaning", color: "#DDFFDD", noOpenedMessages: 12 },
      { title: "MockUp", color: "#AAD7F3", noOpenedMessages: 43 },
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
      <GradientButton onClick={ChangeModalNewMessage} className="MainButton">
        New message
      </GradientButton>

      <hr className="Separator" />

      <Inboxes selectedTab={selectedTab} setSelectedTab={setSelectedTab} />
      <ProjectPicker
        pickedProject={pickedProject}
        setPickedProject={setPickedProject}
      />
      <ProjectTags
        tagList={pickedProject.tagList}
        selectedTab={selectedTab}
        setSelectedTab={setSelectedTab}
      />
      <MyProjectToDos myProjectToDoList={pickedProject.myProjectToDoList} />
    </Sidebar>
  );
};

export default InboxSidebar;
