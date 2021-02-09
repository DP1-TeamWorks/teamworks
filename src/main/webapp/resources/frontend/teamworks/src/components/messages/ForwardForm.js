import React from "react";
import MessageApiUtils from "../../utils/api/MessageApiUtils";
import GradientButton from "../buttons/GradientButton";
import Select from "react-select";

class ForwardForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      inputs: {
        recipientsEmails: "",
      },
      errors: {},
      requestError: "",
    };
    this.forwardedMessage = props.forwardedMessage;
    this.forwardOptions = props.forwardOptions;
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
      case "recipientsEmails":
        if (value === []) errorMsg = "Recipients required";
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

  apiRequestHandler = (recipientsEmails) => {
    console.log("Forwarding to:");
    console.log(recipientsEmails);

    MessageApiUtils.forwardMessage(recipientsEmails, this.forwardedMessage.id)
      .then(() => {
        console.log("Mensaje enviado");
      })
      .catch((error) => {
        console.log("ERROR: no se pudo reenviar el mensaje");
      });
  };

  submitHandler = (event) => {
    event.preventDefault();
    this.validateAll();
    if (!this.hasErrors()) {
      let recipientsEmails = this.state.inputs.recipientsEmails;
      //Call API request in order to receive the user for the session
      this.apiRequestHandler(recipientsEmails);
      this.setState({ inputs: { ...this.state.inputs, recipientsEmails: "" } });
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
          <Select
            options={this.forwardOptions}
            onChange={(e) => {
              this.changeHandler(
                "recipientsEmails",
                e.map((x) => {
                  return x.value;
                })
              );
            }}
            styles={customStyles}
            placeholder={"Select the recipients"}
            isMulti
          ></Select>
        </div>
        <div className="MsgContentButtons">
          <GradientButton
            onClick={this.submitHandler}
            className="GradientButton--OpenMessage"
          >
            Send
          </GradientButton>
        </div>
      </form>
    );
  }
}

export default ForwardForm;

const customStyles = {
  option: (provided, state) => ({
    ...provided,
    color: "#a6ce56",
    width: 700,
  }),
  control: (base, state) => ({
    ...base,
    background: "#262626",
    borderRadius: 4,
    // Overwrittes the different states of border
    borderColor: "rgba(166, 206, 86, 0.8)",
    // Removes weird border around container
    boxShadow: state.isFocused ? null : null,
    marginBottom: 10,
  }),

  dropdownIndicator: (base) => ({
    ...base,
    fill: "#a6ce56",
    stroke: "#a6ce56",
  }),
  menu: (base) => ({
    ...base,
    backgroundColor: "#292d22",
  }),
  multiValue: (base) => ({
    ...base,
    backgroundColor: "#a6ce56",
  }),
  multiValueLabel: (base) => ({
    ...base,
    color: "white",
  }),
};
