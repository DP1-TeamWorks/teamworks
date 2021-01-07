import React, { useEffect, useState } from "react";
import MessageApiUtils from "../../../utils/api/MessageApiUtils";
import Circle from "./Circle";

const Tag = ({ id, color, title }) => {
  const [noOpenedMessages, setNoOpenedMessages] = useState(123);
  // TODO: añadir estado a los mensajes para ver si estan leidos y actualizar cuando cambie
  useEffect(() => {
    MessageApiUtils.getNumberOfNoOpenedMessages(id)
      .then((res) => {
        setNoOpenedMessages(res.data);
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
