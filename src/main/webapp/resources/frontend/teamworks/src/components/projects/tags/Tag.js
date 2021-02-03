import React, { useEffect, useState } from "react";
import MessageApiUtils from "../../../utils/api/MessageApiUtils";
import Circle from "./Circle";

const Tag = ({ id, color, title, reloadCounters, setReloadCounters }) => {
  const [noOpenedMessages, setNoOpenedMessages] = useState(0);
  useEffect(() => {
    MessageApiUtils.getNumberOfNoReadMessagesByTag(id)
      .then((res) => {
        setNoOpenedMessages(res);
      })
      .catch((error) => {
        console.log("ERROR: cannot get the number of no opened messages");
      });
    setReloadCounters(false);
  }, [id, reloadCounters]);
  return (
    <>
      <Circle color={color} /> <span>{title} </span>{" "}
      <span style={{ float: "right" }}> {noOpenedMessages} </span>
    </>
  );
};

export default Tag;
