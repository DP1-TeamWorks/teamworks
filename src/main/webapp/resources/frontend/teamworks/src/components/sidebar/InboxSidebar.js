import React from "react";
import Sidebar from "./Sidebar";
import Inboxes from "../inboxes/Inboxes";
import ProjectPicker from "../projects/ProjectPicker";
import MyProjectToDos from "../projects/toDos/MyProjectToDos";
import ProjectTags from "../projects/tags/ProjectTags";
import GradientButton from "../buttons/GradientButton";
import SidebarSection from "./SidebarSection";
import NewMessage from "../messages/NewMessage";

const InboxSidebar = ({setModalNewMessage, modalNewMessage}) => {

  const ChangeModalNewMessage = () =>{
    setModalNewMessage(!modalNewMessage) /*&& document.getElementById('ModalBackground').style.filter == 'blur(5px)';*/
  }

  return (
    <Sidebar>
      <GradientButton /*onClick={props.createNewMessage}*/ onClick={ChangeModalNewMessage} className="MainButton">
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
