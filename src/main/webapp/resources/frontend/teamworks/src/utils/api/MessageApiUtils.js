import axios from "axios";
import { API_URL } from "../../config/config";
const MESSAGE_URL = "/message";

const MessageApiUtils = {
  /*Inboxes*/
  getMyInboxMessages: () => axios.get(API_URL + MESSAGE_URL + "/inbox"),
  getMySentMessages: () => axios.get(API_URL + MESSAGE_URL + "/sent"),

  getNumberOfNoOpenedMessages: (tagId) =>
    axios.get(API_URL + MESSAGE_URL + "/noOpened?tagId=" + tagId),

  /*Messages*/
  newMessage: (message) => axios.post(API_URL + MESSAGE_URL, message),
  replyMessage: (message, repliedMessageId) =>
    axios.post(
      API_URL + MESSAGE_URL + "?repliedMessageId=" + repliedMessageId,
      message
    ),
  forwardMessage: (forwardList, repliedMessageId) =>
    axios.post(
      API_URL +
        MESSAGE_URL +
        "?repliedMessageId=" +
        repliedMessageId +
        "&forward=true",
      forwardList
    ),
  markMessageAsRead: (messageId) =>
    axios.post(API_URL + MESSAGE_URL + "/markAsRead?messageId=" + messageId),
};

export default MessageApiUtils;
