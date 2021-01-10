import React from "react";
import AuthApiUtils from "../../utils/api/AuthApiUtils";
import Input from "./Input";
import SubmitButton from "./SubmitButton";
import "./AddUserForm.css";

class AddElementForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      inputs: {
        name: ""
      },
      errors: {},
      requestError: "",
    };
    this.submitText = props.submitText;
    this.attributeName = props.attributeName;
    this.attributePlaceholder = props.attributePlaceholder;
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
      case "name":
        if (value === "") {
          errorMsg = "Name required";
        } else if (!/^[A-Za-z0-9_\s]*$/.test(value)) {
          errorMsg = "Invalid, use letters and numbers";
        }
        break;
    }
    this.setState({
      errors: { ...this.state.errors, [field]: errorMsg },
    });
  };

  changeHandler = (event) => {
    let field = event.target.name;
    let value = event.target.value;
    this.validate(field, value);
    this.setState({ inputs: { ...this.state.inputs, [field]: value } });
  };

  apiRequestHandler = (teamname, identifier, firstname, lastname, password) => {
    AuthApiUtils.signup({
      teamname,
      identifier,
      firstname,
      lastname,
      password,
    })
      .then((res) => {
        console.log("Signed Up, redirecting to login");
        const mail = `${firstname.toLowerCase()}${lastname.toLowerCase()}@${identifier}`;
        AuthApiUtils.login({mail, password}).then(() =>
        {
          window.location.replace("/");
        });
      })
      .catch((error) => {
        console.log("API ERROR!");
        console.log(error);
        this.setState({
          requestError: "We had a server problem",
        });
      });
  };

  submitHandler = (event) => {
    event.preventDefault();
    this.validateAll();
    if (!this.hasErrors()) {
      let teamname = this.state.inputs.teamname;
      let identifier = this.state.inputs.identifier;
      let username = this.state.inputs.username;
      let lastname = this.state.inputs.lastname;
      let password = this.state.inputs.password;
      //Call API request in order to receive the user for the session
      this.apiRequestHandler(
        teamname,
        identifier,
        username,
        lastname,
        password
      );
    } else {
      console.log("There are errors in this form");
      console.log(this.state.errors);
    }
  };

  render() {
    return (
      <form onSubmit={this.submitHandler}>
        <p className="InputTitle">{this.attributeName}</p>
        <div className="EditableField EditableField--OnlyInput">
            <Input name="name" styleClass="Input EditingInput" placeholder={this.attributePlaceholder} error={this.state.errors.firstname} changeHandler={this.changeHandler}/>
        </div>
        <SubmitButton
          reducedsize
          text={this.submitText}
          requestError={this.state.requestError}
          hasErrors={this.hasErrors()}
        />
      </form>
    );
  }
}

export default AddElementForm;
