import React, { useState } from "react";
import MessageList from "../components/messages/MessageList";
import NewMessage from "../components/messages/NewMessage";
import InboxSidebar from "../components/sidebar/InboxSidebar";
import Section from "./Section";

const Inbox = (props) => {
  const [messages, setMessages] = useState([
    {
      sender: { name: "Johnny Depp", teamname: "Pearson" },
      subject: "Welcome to TeamWorks",
      date: "02/12/2020",
      time: "19:23",
      tags: [
        { title: "Planning", color: "#FFD703" },
        { title: "Planning", color: "#DDFFDD" },
      ],
    },
    {
      sender: { name: "Johnny Depp", teamname: "Pearson" },
      subject: "Welcome to TeamWorks",
      date: "02/12/2020",
      time: "19:23",
      tags: [
        { title: "Planning", color: "#FFD703" },
        { title: "Planning", color: "#DDFFDD" },
      ],
    },
    {
      sender: { name: "Johnny Depp", teamname: "Pearson" },
      subject: "Welcome to TeamWorks",
      date: "02/12/2020",
      time: "19:23",
      tags: [
        { title: "Planning", color: "#FFD703" },
        { title: "Planning", color: "#DDFFDD" },
      ],
    },
    {
      sender: { name: "Johnny Depp", teamname: "Pearson" },
      subject: "Welcome to TeamWorks",
      date: "02/12/2020",
      time: "19:23",
      tags: [
        { title: "Planning", color: "#FFD703" },
        { title: "Planning", color: "#DDFFDD" },
      ],
    },
  ]);

  const [modalNewMessage, setModalNewMessage] = useState(false);

  return (
    <div className="Content">
      <InboxSidebar modalNewMessage = {modalNewMessage} setModalNewMessage= {setModalNewMessage}/>
      <Section className="Section--Lighter">
        {modalNewMessage && <NewMessage ChangeModalNewMessage={()=> setModalNewMessage(!modalNewMessage)}/>}
        <MessageList messages={messages} />
      </Section>
    </div>
  );
};

export default Inbox;
