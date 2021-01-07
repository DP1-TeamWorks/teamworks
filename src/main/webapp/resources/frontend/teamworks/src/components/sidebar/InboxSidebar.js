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
    id:"1",
    tagList: [
      { title: "Planning", color: "#FFD703", noOpenedMessages: 25 },
      { title: "Cleaning", color: "#DDFFDD", noOpenedMessages: 12 },
      { title: "MockUp", color: "#AAD7F3", noOpenedMessages: 43 },
    ]
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
      <MyProjectToDos projectId={pickedProject.id} />
    </Sidebar>
  );
};

export default InboxSidebar;
