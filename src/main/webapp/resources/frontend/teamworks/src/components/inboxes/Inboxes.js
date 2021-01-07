import React from "react";
import MessageApiUtils from "../../utils/api/MessageApiUtils";
import InboxSidebarTab from "../sidebar/InboxSidebarTab";

const Inboxes = ({
  numberOfInboxMessages,
  numberOfSentMessages,
  selectedTab,
  setSelectedTab,
}) => {
  return (
    <>
      <h3 className="SidebarSectionTitle">Inboxes</h3>
      <InboxSidebarTab
        text="Inbox"
        selectedTab={selectedTab}
        setSelectedTab={setSelectedTab}
        isTag={false}
      >
        Inbox
        <span style={{ float: "right" }}>{numberOfInboxMessages}</span>
      </InboxSidebarTab>
      <InboxSidebarTab
        text="Sent"
        selectedTab={selectedTab}
        setSelectedTab={setSelectedTab}
        isTag={false}
      >
        Sent
        <span style={{ float: "right" }}>{numberOfSentMessages}</span>
      </InboxSidebarTab>
    </>
  );
};

export default Inboxes;
