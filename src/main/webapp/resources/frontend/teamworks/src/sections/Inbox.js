import React, { useState, useEffect } from "react";
import MessageList from "../components/messages/MessageList";
import NewMessage from "../components/messages/NewMessage";
import InboxSidebar from "../components/sidebar/InboxSidebar";
import MessageApiUtils from "../utils/api/MessageApiUtils";
import Section from "./Section";

const Inbox = (props) => {
  const [selectedTab, setSelectedTab] = useState("Inbox");
  const [inboxMessages, setInboxMessages] = useState([]);
  const [sentMessages, setSentMessages] = useState([]);
  const [tagMessages, setTagMessages] = useState([]);

  useEffect(() => {
    MessageApiUtils.getMyInboxMessages()
      .then((res) => {
        console.log("Getting Inbox Messages");
        setInboxMessages(res);
      })
      .catch((error) => {
        console.log("ERROR: cannot get inbox messages");
      });

    MessageApiUtils.getMySentMessages()
      .then((res) => {
        console.log("Getting sent Messages");
        setSentMessages(res);
      })
      .catch((error) => {
        console.log("ERROR: cannot get sent messages");
      });
  }, []);

  useEffect(() => {
    MessageApiUtils.getMyMessagesByTag(selectedTab)
      .then((res) => {
        console.log("Getting Tag Messages");
        setTagMessages(res);
      })
      .catch((error) => {
        console.log("ERROR: cannot get tag messages");
      });
  }, [selectedTab]);

  const [modalNewMessage, setModalNewMessage] = useState(false);
  const selectedMessages = () => {
    switch (selectedTab) {
      case "Inbox":
        return inboxMessages;
      case "Sent":
        return sentMessages;
      default:
        return tagMessages;
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
        {modalNewMessage && (
          <NewMessage
            ChangeModalNewMessage={() => setModalNewMessage(!modalNewMessage)}
          />
        )}
        <MessageList messages={selectedMessages()} />
      </Section>
    </div>
  );
};

export default Inbox;
