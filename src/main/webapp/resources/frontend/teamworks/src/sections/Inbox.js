import React, { useState, useEffect } from "react";
import MessageList from "../components/messages/MessageList";
import NewMessage from "../components/messages/NewMessage";
import InboxSidebar from "../components/sidebar/InboxSidebar";
import MessageApiUtils from "../utils/api/MessageApiUtils";
import Section from "./Section";

const Inbox = (props) => {
  const [selectedTab, setSelectedTab] = useState("Inbox");
  const [inboxMessages, setInboxMessages] = useState([
    {
      id: 9,
      sender: { name: "Johnny Depp", teamname: "Pearson" },
      subject: "Welcome to TeamWorks",
      date: "02/12/2020",
      time: "19:23",
      recipients: [{ name: "Mark" }, { name: "Adam" }, { name: "John" }],
      tags: [
        { title: "Planning", color: "#FFD703" },
        { title: "Planning", color: "#DDFFDD" },
      ],
      text:
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
    },
    {
      id: 4,
      sender: { name: "Johnny Depp", teamname: "Pearson" },
      subject: "Welcome to TeamWorks",
      date: "02/12/2020",
      time: "19:23",
      recipients: [{ name: "Mark" }, { name: "Adam" }, { name: "John" }],
      tags: [
        { title: "Planning", color: "#FFD703" },
        { title: "Planning", color: "#DDFFDD" },
      ],
      text:
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
    },
    {
      id: 2,
      sender: { name: "Johnny Depp", teamname: "Pearson" },
      subject: "Welcome to TeamWorks",
      date: "02/12/2020",
      time: "19:23",
      recipients: [{ name: "Mark" }, { name: "Adam" }, { name: "John" }],
      tags: [
        { title: "Planning", color: "#FFD703" },
        { title: "Planning", color: "#DDFFDD" },
      ],
      text:
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
    },
    {
      id: 1,
      sender: { name: "Johnny Depp", teamname: "Pearson" },
      subject: "Welcome to TeamWorks",
      date: "02/12/2020",
      time: "19:23",
      recipients: [{ name: "Mark" }, { name: "Adam" }, { name: "John" }],
      tags: [
        { title: "Planning", color: "#FFD703" },
        { title: "Planning", color: "#DDFFDD" },
      ],
      text:
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
    },
  ]);
  const [sentMessages, setSentMessages] = useState([
    {
      id: 4,
      sender: { name: "Kevin Depp", teamname: "Stark" },
      subject: "Welcome to TeamWorks",
      date: "02/12/2020",
      time: "19:23",
      recipients: [{ name: "Mark" }, { name: "Adam" }, { name: "John" }],
      tags: [
        { title: "Planning", color: "#FFD703" },
        { title: "Planning", color: "#DDFFDD" },
      ],
    },
    {
      id: 4,
      sender: { name: "Kevin Depp", teamname: "Stark" },
      subject: "Welcome to TeamWorks",
      date: "02/12/2020",
      time: "19:23",
      recipients: [{ name: "Mark" }, { name: "Adam" }, { name: "John" }],
      tags: [
        { title: "Planning", color: "#FFD703" },
        { title: "Planning", color: "#DDFFDD" },
      ],
    },
    {
      id: 4,
      sender: { name: "Kevin Depp", teamname: "Stark" },
      subject: "Welcome to TeamWorks",
      date: "02/12/2020",
      time: "19:23",
      recipients: [{ name: "Mark" }, { name: "Adam" }, { name: "John" }],
      tags: [
        { title: "Planning", color: "#FFD703" },
        { title: "Planning", color: "#DDFFDD" },
      ],
    },
    {
      id: 4,
      sender: { name: "Kevin Depp", teamname: "Stark" },
      subject: "Welcome to TeamWorks",
      date: "02/12/2020",
      time: "19:23",
      recipients: [{ name: "Mark" }, { name: "Adam" }, { name: "John" }],
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
        numberOfInboxMessages={inboxMessages.filter((msg) => !msg.read).length}
        numberOfSentMessages={sentMessages.filter((msg) => !msg.read).length}
        selectedTab={selectedTab}
        setSelectedTab={setSelectedTab}
        modalNewMessage={modalNewMessage}
        setModalNewMessage={setModalNewMessage}
      />
      <Section className="Section--Lighter">
        {modalNewMessage && <NewMessage />}
        <MessageList messages={selectedMessages()} />
      </Section>
    </div>
  );
};

export default Inbox;
