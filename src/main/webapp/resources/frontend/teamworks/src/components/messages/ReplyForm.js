import React from "react";
import Input from "../forms/Input";
import MessageApiUtils from "../../utils/api/MessageApiUtils";
import GradientButton from "../buttons/GradientButton";

class ReplyForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      inputs: {
        text: "",
      },
      errors: {},
      requestError: "",
    };
    this.repliedMessage = props.repliedMessage;
    this.setOpenMessage = props.setOpenMessage;
  }

  hasErrors = () => {
    const errors = Object.values(this.state.errors);
    const nInputs = Object.keys(this.state.inputs).length;
    let b =
      errors.length < nInputs
        ? true
        : errors.some((e) => {
            return e !== "";
          });
    return b;
  };

  validateAll = () => {
    Object.entries(this.state.inputs).forEach((e) => {
      const [k, v] = e;
      this.validate(k, v);
    });
  };

  validate = (field, value) => {
    this.setState({
      requestError: "",
    });
    let errorMsg = "";
    switch (field) {
      case "text":
        if (value === "") errorMsg = "Text required";
        break;
      default:
    }
    this.setState({
      errors: { ...this.state.errors, [field]: errorMsg },
    });
  };

  changeHandler = (field, value) => {
    this.validate(field, value);
    this.setState({ inputs: { ...this.state.inputs, [field]: value } });
  };

  apiRequestHandler = (text) => {
    let message = {
      recipientsEmails: [this.repliedMessage.strippedSender.email],
      subject: "Re: " + this.repliedMessage.subject,
      text: text,
      read: false,
      toDoList: [],
      tagList: [],
    };
    console.log("Replying:")
    console.log(message);
    MessageApiUtils.replyMessage(message, this.repliedMessage.id)
      .then(() => {
        console.log("Mensaje enviado");
      })
      .catch((error) => {
        console.log("ERROR: no se pudo responder el mensaje");
      });
  };

  submitHandler = (event) => {
    event.preventDefault();
    this.validateAll();
    if (!this.hasErrors()) {
      let text = this.state.inputs.text;
      //Call API request in order to receive the user for the session
      this.apiRequestHandler(text);
      this.setState({ inputs: { ...this.state.inputs, text: "" } });
      this.setOpenMessage("");
    } else {
      console.log("There are errors in this form");
      console.log(this.state.errors);
    }
  };

  render() {
    return (
      <form onSubmit={this.submitHandler}>
        <div className="MsgContentInput">
          <Input
            name="text"
            type="text"
            value={this.state.inputs.text}
            placeholder="Write your reply"
            styleClass="Input ReplyInput"
            error={this.state.errors.text}
            changeHandler={this.changeHandler}
          />
        </div>
        <div className="MsgContentButtons">
          <GradientButton
            onClick={this.submitHandler}
            className="GradientButton--OpenMessage"
          >
            Reply
          </GradientButton>
        </div>
      </form>
    );
  }
}

export default ReplyForm;
