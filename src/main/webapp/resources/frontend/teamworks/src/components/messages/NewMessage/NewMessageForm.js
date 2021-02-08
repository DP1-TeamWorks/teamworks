import React, { useState, useEffect } from "react";
import Input from "../../forms/Input";
import GradientButton from "../../buttons/GradientButton";
import NewMessageMultiSelect from "./NewMessageMultiSelect";
import NewMessageText from "./NewMessageText";
import MessageApiUtils from "../../../utils/api/MessageApiUtils";

class NewMessageForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      inputs: {
        To: "",
        Tags: "",
        Subject: "",
        ToDos: "",
        Message: "",
      },
      errors: {},
      requestError: "",
    };

    this.ChangeModalNewMessage = props.ChangeModalNewMessage;
    this.mailOptions = props.mailOptions;
    //this.mailOptions.push({ label: "Other", value: "@team.com" });
    this.toDoOptions = props.toDoOptions;
    this.tagOptions = props.tagOptions;
  }

  hasErrors = () => {
    const errors = Object.values(this.state.errors);
    const nInputs = Object.keys(this.state.inputs).length;

    let b =
      errors.length < nInputs - 2
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
      case "To":
        if (value.length < 1 || value === "")
          errorMsg = "Select one recipient at least";
        break;
      case "Subject":
        if (value.length > 100)
          errorMsg = "Too Long Subject (" + (100 - value.length) + ")";
        break;
      case "Message":
        if (value === "") errorMsg = "Message required";
        if (value.length > 750)
          errorMsg = "The Message is too Long (" + (750 - value.length) + ")";
        break;
      default:
    }
    this.setState({
      errors: { ...this.state.errors, [field]: errorMsg },
    });
  };

  changeHandler = (field, value) => {
    this.validate(field, value);
    /*if (field === "To")
      if (!this.mailOptions.includes(value)) {
        this.mailOptions.pop();
        this.mailOptions.push({ label: value, value: value });
      }*/
    this.setState({
      inputs: { ...this.state.inputs, [field]: value },
    });
  };

  apiRequestHandler = (to, subject, text, toDos, tags) => {
    console.log("Sending: ");
    let message = {
      recipientsEmails: to,
      subject: subject,
      text: text,
      read: false,
      toDoList: toDos && toDos !== "" ? toDos : [],
      tagList: tags && tags !== "" ? tags : [],
    };
    console.log(message);
    MessageApiUtils.newMessage(message)
      .then(() => {
        console.log("Mensaje enviado");
      })
      .catch((error) => {
        console.log("ERROR: no se pudo enviar el mensaje");
        this.setState({
          requestError: "Couldn't send the message",
        });
      });
  };

  submitHandler = (event) => {
    event.preventDefault();
    this.validateAll();
    if (!this.hasErrors()) {
      let to = this.state.inputs.To;
      let subject = this.state.inputs.Subject;
      let text = this.state.inputs.Message;
      let toDos = this.state.inputs.ToDos;
      let tags = this.state.inputs.Tags;
      this.apiRequestHandler(to, subject, text, toDos, tags);
      this.ChangeModalNewMessage();
    } else {
      console.log("There are errors in this form");
      console.log(this.state.errors);
    }
  };

  render() {
    return (
      <form onSubmit={this.submitHandler} className="NewMsgForm">
        <NewMessageMultiSelect
          name="To"
          placeholder="Users"
          changeHandler={this.changeHandler}
          options={this.mailOptions}
          error={this.state.errors.To}
        ></NewMessageMultiSelect>
        <div className="SecondLineFlex">
          <NewMessageMultiSelect
            name="Tags"
            placeholder="Tags"
            changeHandler={this.changeHandler}
            options={this.tagOptions}
            className="InputContainer2"
          ></NewMessageMultiSelect>
          <NewMessageMultiSelect
            name="ToDos"
            placeholder="ToDos"
            options={this.toDoOptions}
            changeHandler={this.changeHandler}
            className="InputContainer2"
          ></NewMessageMultiSelect>
        </div>
        <div className="NewMsg">
          <div className="NewMsgHeadLabel">
            <span className="NewMsgHeadText">Subject</span>
            <Input
              name="Subject"
              styleClass="Input NewMsgHead InputNewMsg"
              placeholder="How are you?"
              error={this.state.errors.Subject}
              changeHandler={this.changeHandler}
            ></Input>
          </div>

          <NewMessageText
            name="Message"
            error={this.state.errors.Message}
            changeHandler={this.changeHandler}
          ></NewMessageText>
        </div>
        <div className="NewMsgSendOptions">
          <img
            className="NewMsgUpload"
            src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDQ3NS4wNzggNDc1LjA3NyIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNTEyIDUxMiIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSIgY2xhc3M9IiI+PGc+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+Cgk8Zz4KCQk8cGF0aCBkPSJNNDY3LjA4MSwzMjcuNzY3Yy01LjMyMS01LjMzMS0xMS43OTctNy45OTQtMTkuNDExLTcuOTk0aC0xMjEuOTFjLTMuOTk0LDEwLjY1Ny0xMC43MDUsMTkuNDExLTIwLjEyNiwyNi4yNjIgICAgYy05LjQyNSw2Ljg1Mi0xOS45MzgsMTAuMjgtMzEuNTQ2LDEwLjI4aC03My4wOTZjLTExLjYwOSwwLTIyLjEyNi0zLjQyOS0zMS41NDUtMTAuMjhjLTkuNDIzLTYuODUxLTE2LjEzLTE1LjYwNC0yMC4xMjctMjYuMjYyICAgIEgyNy40MDhjLTcuNjEyLDAtMTQuMDgzLDIuNjYzLTE5LjQxNCw3Ljk5NEMyLjY2NCwzMzMuMDkyLDAsMzM5LjU2MywwLDM0Ny4xNzh2OTEuMzYxYzAsNy42MSwyLjY2NCwxNC4wODksNy45OTQsMTkuNDEgICAgYzUuMzMsNS4zMjksMTEuODAxLDcuOTkxLDE5LjQxNCw3Ljk5MWg0MjAuMjY2YzcuNjEsMCwxNC4wODYtMi42NjIsMTkuNDEtNy45OTFjNS4zMzItNS4zMjgsNy45OTQtMTEuOCw3Ljk5NC0xOS40MXYtOTEuMzYxICAgIEM0NzUuMDc4LDMzOS41NjMsNDcyLjQxNiwzMzMuMDk5LDQ2Ny4wODEsMzI3Ljc2N3ogTTM2MC4wMjUsNDIzLjk3OGMtMy42MjEsMy42MTctNy45MDUsNS40MjgtMTIuODU0LDUuNDI4ICAgIHMtOS4yMjctMS44MTEtMTIuODQ3LTUuNDI4Yy0zLjYxNC0zLjYxMy01LjQyMS03Ljg5OC01LjQyMS0xMi44NDdzMS44MDctOS4yMzYsNS40MjEtMTIuODQ3YzMuNjItMy42MTMsNy44OTgtNS40MjgsMTIuODQ3LTUuNDI4ICAgIHM5LjIzMiwxLjgxNCwxMi44NTQsNS40MjhjMy42MTMsMy42MSw1LjQyMSw3Ljg5OCw1LjQyMSwxMi44NDdTMzYzLjYzOCw0MjAuMzY0LDM2MC4wMjUsNDIzLjk3OHogTTQzMy4xMDksNDIzLjk3OCAgICBjLTMuNjE0LDMuNjE3LTcuODk4LDUuNDI4LTEyLjg0OCw1LjQyOGMtNC45NDgsMC05LjIyOS0xLjgxMS0xMi44NDctNS40MjhjLTMuNjEzLTMuNjEzLTUuNDItNy44OTgtNS40Mi0xMi44NDcgICAgczEuODA3LTkuMjM2LDUuNDItMTIuODQ3YzMuNjE3LTMuNjEzLDcuODk4LTUuNDI4LDEyLjg0Ny01LjQyOGM0Ljk0OSwwLDkuMjMzLDEuODE0LDEyLjg0OCw1LjQyOCAgICBjMy42MTcsMy42MSw1LjQyNyw3Ljg5OCw1LjQyNywxMi44NDdTNDM2LjcyOSw0MjAuMzY0LDQzMy4xMDksNDIzLjk3OHoiIGZpbGw9IiNhN2UzMmQiIGRhdGEtb3JpZ2luYWw9IiMwMDAwMDAiIHN0eWxlPSIiPjwvcGF0aD4KCQk8cGF0aCBkPSJNMTA5LjYzMiwxNzMuNTloNzMuMDg5djEyNy45MDljMCw0Ljk0OCwxLjgwOSw5LjIzMiw1LjQyNCwxMi44NDdjMy42MTcsMy42MTMsNy45LDUuNDI3LDEyLjg0Nyw1LjQyN2g3My4wOTYgICAgYzQuOTQ4LDAsOS4yMjctMS44MTMsMTIuODQ3LTUuNDI3YzMuNjE0LTMuNjE0LDUuNDIxLTcuODk4LDUuNDIxLTEyLjg0N1YxNzMuNTloNzMuMDkxYzcuOTk3LDAsMTMuNjEzLTMuODA5LDE2Ljg0NC0xMS40MiAgICBjMy4yMzctNy40MjIsMS45MDItMTMuOTktMy45OTctMTkuNzAxTDI1MC4zODUsMTQuNTYyYy0zLjQyOS0zLjYxNy03LjcwNi01LjQyNi0xMi44NDctNS40MjZjLTUuMTM2LDAtOS40MTksMS44MDktMTIuODQ3LDUuNDI2ICAgIEw5Ni43ODYsMTQyLjQ2OWMtNS45MDIsNS43MTEtNy4yMzMsMTIuMjc1LTMuOTk5LDE5LjcwMUM5Ni4wMjYsMTY5Ljc4NSwxMDEuNjQsMTczLjU5LDEwOS42MzIsMTczLjU5eiIgZmlsbD0iI2E3ZTMyZCIgZGF0YS1vcmlnaW5hbD0iIzAwMDAwMCIgc3R5bGU9IiI+PC9wYXRoPgoJPC9nPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjwvZz48L3N2Zz4="
          />
          <SubmitButton
            text={"Send"}
            className="NewMsgSendButton "
            requestError={this.state.requestError}
            hasErrors={this.hasErrors()}
          />
        </div>
      </form>
    );
  }
}

export default NewMessageForm;
