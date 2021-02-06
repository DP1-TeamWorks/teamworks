import React from "react";
import { useEffect, useState } from "react/cjs/react.development";
import UserApiUtils from "../../utils/api/UserApiUtils";
import GradientButton from "../buttons/GradientButton";
import ForwardForm from "./ForwardForm";
import ReplyForm from "./ReplyForm";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faReply } from "@fortawesome/free-solid-svg-icons";
import { faForward } from "@fortawesome/free-solid-svg-icons";

const OpenedMessage = ({ msg }) => {
  const [forwardOptions, setForwardOptions] = useState({});
  const [messageOptions, setMessageOptions] = useState("");
  useEffect(() => {
    UserApiUtils.getMyTeamUsers()
      .then((res) => {
        console.log("Getting user mails");
        let mailList = res.map((user) => {
          return { label: user.name + " - " + user.email, value: user.email };
        });
        setForwardOptions(mailList);
      })
      .catch((error) => {
        console.log("ERROR: cannot get the users mails");
      });
  }, [msg]);

  return (
    <>
      <div className="MsgContentSentTo">
        <span style={{ fontWeight: "bold" }}>
          Sent to{" "}
          {msg.recipients.map((r) => {
            return <b key={r.name}> {r.name} </b>;
          })}
        </span>
      </div>
      <div className="MsgContentText">{msg.text.replace("\n", "<br>")}</div>
      <div className="MsgContentButtons">
        <GradientButton
          onClick={() => {
            messageOptions === "Reply"
              ? setMessageOptions("")
              : setMessageOptions("Reply");
          }}
          className="GradientButton--ReFor "
        >
          <FontAwesomeIcon icon={faReply} />
        </GradientButton>
        <GradientButton
          onClick={() => {
            messageOptions === "Forward"
              ? setMessageOptions("")
              : setMessageOptions("Forward");
          }}
          className="GradientButton--ReFor"
        >
          <FontAwesomeIcon icon={faForward} />
        </GradientButton>
      </div>
      {messageOptions === "Reply" && <ReplyForm repliedMessage={msg} />}
      {messageOptions === "Forward" && (
        <ForwardForm forwardOptions={forwardOptions} forwardedMessage={msg} />
      )}
    </>
  );
};

export default OpenedMessage;
