import React, { useState } from "react";
import InboxSidebarTab from "../sidebar/InboxSidebarTab";

const Inboxes = ({ selectedTab, setSelectedTab }) => {
  const [inboxMessages, setInboxMessages] = useState(25);
  const [sentMessages, setSentMessages] = useState(12);

  console.log(selectedTab);
  return (
    <>
      <h3 className="SidebarSectionTitle">Inboxes</h3>
      <InboxSidebarTab
        text="Inbox"
        selectedTab={selectedTab}
        setSelectedTab={setSelectedTab}
        isTag={false} /*noOpenedMessages={inboxMessages}*/
      >
        Inbox
      </InboxSidebarTab>
      <InboxSidebarTab
        text="Sent"
        selectedTab={selectedTab}
        setSelectedTab={setSelectedTab}
        isTag={false} /*noOpenedMessages={sentMessages}*/
      >
        Sent
      </InboxSidebarTab>
    </>
  );
};

export default Inboxes;
