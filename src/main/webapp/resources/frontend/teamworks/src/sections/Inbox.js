import React, { useState, useEffect } from "react";
import MessageList from "../components/messages/MessageList";
import NewMessage from "../components/messages/NewMessage/NewMessage";
import InboxSidebar from "../components/sidebar/InboxSidebar";
import MessageApiUtils from "../utils/api/MessageApiUtils";
import Section from "./Section";

const Inbox = ({ search, setSearch }) => {
  const [selectedTab, setSelectedTab] = useState("Inbox");
  const [selectedMessages, setSelectedMessages] = useState([]);
  const [nInboxMessages, setNInboxMessages] = useState([]);
  const [pickedProject, setPickedProject] = useState(
    localStorage.getItem("pickedProject")
      ? localStorage.getItem("pickedProject")
      : {
          name: "Any",
          id: 9999,
          tags: [],
        }
  );
  const [reloadCounters, setReloadCounters] = useState(true);

  useEffect(() => {
    if (reloadCounters)
      MessageApiUtils.getNumberOfNotReadMessages()
        .then((res) => {
          setNInboxMessages(res);
        })
        .catch((error) => {
          console.log("ERROR: cannot get number of inbox messages");
        });
    setReloadCounters(false);
  }, [reloadCounters]);

  useEffect(() => {
    if (search !== "") {
      console.log("Searching messages by keywords: " + search);
      setSelectedTab("Search");
      MessageApiUtils.getMyMessagesBySearch(search)
        .then((res) => {
          console.log("Getting searched Messages");
          setSelectedMessages(res);
        })
        .catch((error) => {
          console.log("ERROR: cannot get the searched messages");
        });
      setSearch("");
    } else {
      switch (selectedTab) {
        case "Inbox":
          MessageApiUtils.getMyInboxMessages()
            .then((res) => {
              console.log("Getting inbox Messages");
              setSelectedMessages(res);
            })
            .catch((error) => {
              console.log("ERROR: cannot get inbox messages");
            });
          break;
        case "Sent":
          MessageApiUtils.getMySentMessages()
            .then((res) => {
              console.log("Getting sent Messages");
              setSelectedMessages(res);
            })
            .catch((error) => {
              console.log("ERROR: cannot get sent messages");
            });
          break;
        default:
          MessageApiUtils.getMyMessagesByTag(selectedTab)
            .then((res) => {
              console.log("Getting Tag Messages");
              setSelectedMessages(res);
            })
            .catch((error) => {
              console.log("ERROR: cannot get tag messages");
            });
      }
    }
  }, [selectedTab, search, reloadCounters]);

  const [modalNewMessage, setModalNewMessage] = useState(false);

  return (
    <div className="Content">
      <InboxSidebar
        numberOfInboxMessages={nInboxMessages}
        numberOfSentMessages={""}
        reloadCounters={reloadCounters}
        setReloadCounters={setReloadCounters}
        selectedTab={selectedTab}
        setSelectedTab={setSelectedTab}
        modalNewMessage={modalNewMessage}
        setModalNewMessage={setModalNewMessage}
        pickedProject={pickedProject}
        setPickedProject={setPickedProject}
      />
      <Section className="Section--Lighter">
        {modalNewMessage && (
          <NewMessage
            ChangeModalNewMessage={() => setModalNewMessage(!modalNewMessage)}
          />
        )}
        <MessageList
          setReloadCounters={setReloadCounters}
          messages={selectedMessages}
        />
      </Section>
    </div>
  );
};

export default Inbox;
