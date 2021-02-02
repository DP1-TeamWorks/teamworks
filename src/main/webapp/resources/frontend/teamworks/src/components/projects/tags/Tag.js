import React, { useEffect, useState } from "react";
import MessageApiUtils from "../../../utils/api/MessageApiUtils";
import Circle from "./Circle";

const Tag = ({ id, color, title }) => {
  const [noOpenedMessages, setNoOpenedMessages] = useState(123);
  // TODO: actualizar cuando cambie
  useEffect(() => {
    MessageApiUtils.getNumberOfNoReadMessagesByTag(id)
      .then((res) => {
        setNoOpenedMessages(res);
      })
      .catch((error) => {
        console.log("ERROR: cannot get the number of no opened messages");
      });
  }, [id]);
  return (
    <>
      <Circle color={color} /> <span>{title} </span>{" "}
      <span style={{ float: "right" }}> {noOpenedMessages} </span>
    </>
  );
};

export default Tag;
