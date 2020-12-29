import React, { useState } from "react";
import InboxSidebar from "../components/sidebar/InboxSidebar";
import Section from "./Section";

const Inbox = (props) => {
  return (
    <div className="Content">
      <Section>
        <InboxSidebar />
      </Section>
    </div>
  );
};

export default Inbox;
