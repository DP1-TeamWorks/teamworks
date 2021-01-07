import React, { useState } from "react";
import Sidebar from "./Sidebar";
import Inboxes from "../inboxes/Inboxes";
import ProjectPicker from "../projects/ProjectPicker";
import MyProjectToDos from "../projects/toDos/MyProjectToDos";
import ProjectTags from "../projects/tags/ProjectTags";
import GradientButton from "../buttons/GradientButton";

const InboxSidebar = ({
  numberOfInboxMessages,
  numberOfSentMessages,
  selectedTab,
  setSelectedTab,
  setModalNewMessage,
  modalNewMessage,
}) => {
  const [pickedProject, setPickedProject] = useState({
    name: "Pick a project",
    id: null,
    tags: [
    ]});

  const ChangeModalNewMessage = () => {
    setModalNewMessage(!modalNewMessage);
  };

  return (
    <Sidebar>
      <GradientButton onClick={ChangeModalNewMessage} className="MainButton">
        New message
      </GradientButton>

      <hr className="Separator" />

      <Inboxes
        numberOfInboxMessages={numberOfInboxMessages}
        numberOfSentMessages={numberOfSentMessages}
        selectedTab={selectedTab}
        setSelectedTab={setSelectedTab}
      />
      <ProjectPicker
        pickedProject={pickedProject}
        setPickedProject={setPickedProject}
      />
      <ProjectTags
        tagList={pickedProject.tags}
        selectedTab={selectedTab}
        setSelectedTab={setSelectedTab}
      />
      <MyProjectToDos projectId={pickedProject.id} />
    </Sidebar>
  );
};

export default InboxSidebar;
