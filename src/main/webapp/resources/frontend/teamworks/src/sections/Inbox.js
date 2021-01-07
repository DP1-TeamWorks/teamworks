import React, { useState } from "react";
import MessageList from "../components/messages/MessageList";
import NewMessage from "../components/messages/NewMessage";
import InboxSidebar from "../components/sidebar/InboxSidebar";
import MessageApiUtils from "../utils/api/MessageApiUtils";
import Section from "./Section";

const Inbox = (props) => {
  const [selectedTab, setSelectedTab] = useState("Inbox");
  const [inboxMessages, setInboxMessages] = useState([
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
  const [sentMessages, setSentMessages] = useState([
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

  useEffect(() => {
    MessageApiUtils.getMyInboxMessages()
      .then((res) => {
        setInboxMessages(res.data);
      })
      .catch((error) => {
        console.log("ERROR: cannot get inbox messages");
      });

    MessageApiUtils.getMySentMessages()
      .then((res) => {
        setSentMessages(res.data);
      })
      .catch((error) => {
        console.log("ERROR: cannot get sent messages");
      });
  }, []);

  const [modalNewMessage, setModalNewMessage] = useState(false);

  const selectedMessages = () => {
    switch (selectedTab) {
      case "Inbox":
        return inboxMessages;
      case "Sent":
        return sentMessages;
      default:
        return inboxMessages.filter((msg) => {
          /*TODO:Filter the messages by tag}*/
        });
    }
  };

  return (
    <div className="Content">
      <InboxSidebar
        inboxMessages={inboxMessages}
        sentMessages={sentMessages}
        modalNewMessage={modalNewMessage}
        setModalNewMessage={setModalNewMessage}
      />
      <Section className="Section--Lighter">
        {modalNewMessage && <NewMessage />}
        <MessageList messages={} />
      </Section>
    </div>
  );
};

export default Inbox;
