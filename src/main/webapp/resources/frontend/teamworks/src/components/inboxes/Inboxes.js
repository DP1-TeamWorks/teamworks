import React, { useState } from "react";
import SidebarTab from "../sidebar/SidebarTab";

const Inboxes = (props) => {
  const [inboxMessages, setInboxMessages] = useState(25);
  const [sentMessages, setSentMessages] = useState(12);

  return (
    <>
      <h3 className="SidebarSectionTitle">Inboxes</h3>
      <SidebarTab text="Inbox" /*noOpenedMessages={inboxMessages}*/ />
      <SidebarTab text="Sent" /*noOpenedMessages={sentMessages}*/ />
    </>
  );
};

export default Inboxes;
