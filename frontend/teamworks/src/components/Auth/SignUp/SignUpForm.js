import React from "react";
import AuthApiUtils from "../../../utils/api/AuthApiUtils";
import Input from "../../Forms/Input";
import SubmitButton from "../../Forms/SubmitButton";

class SignUpForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      inputs: {
        teamname: "",
        identifier: "",
        username: "",
        password: "",
      },
      errors: {},
    };
  }

  hasErrors = () => {
    const errors = Object.values(this.state.errors);
    const nInputs = Object.keys(this.state.inputs).length;
    console.log(this.state.inputs);
    console.log(nInputs, errors.length);
    let b =
      errors.length < nInputs
        ? true
        : errors.some((e) => {
            return e !== "";
          });
    console.log("hasErrors? ", b);
    return b;
  };

  validateAll = () => {
    console.log("Validating ALL");
    Object.entries(this.state.inputs).forEach((e) => {
      const [k, v] = e;
      console.log(k);
      console.log(v);
      this.validate(k, v);
    });
  };

  validate = (field, value) => {
    console.log("Validating: ", field, value);
    let errorMsg = "";
    switch (field) {
      case "teamname":
        if (value === "") {
          errorMsg = "TeamName required";
        }
        break;
      case "identifier":
        if (value === "") {
          errorMsg = "Identifier required";
        } else if (!/^[A-Za-z0-9_-]*$/.test(value)) {
          errorMsg = "Invalid identifier, use letters and numbers";
        }
        break;
      case "username":
        if (value === "") {
          errorMsg = "Username required";
        } else if (!/^[A-Za-z0-9_-]*$/.test(value)) {
          errorMsg = "Invalid username, use letters and numbers";
        }
        break;
      case "password":
        if (value === "") {
          errorMsg = "Password required";
        } else if (value.length < 8) {
          errorMsg = "Passwords are larger";
        }

        break;
      default:
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

  submitHandler = (event) => {
    event.preventDefault();
    this.validateAll();
    if (!this.hasErrors()) {
      let teamname = this.state.inputs.teamname;
      let identifier = this.state.inputs.identifier;
      let username = this.state.inputs.username;
      let password = this.state.inputs.password;
      //Call API request in order to receive the user for the session
      AuthApiUtils.signup({ teamname, identifier, username, password });
    } else {
      console.log("There are errors in this form");
      console.log(this.state.errors);
    }
  };

  render() {
    return (
      <form onSubmit={this.submitHandler}>
        <p className="InputTitle">Team name</p>
        <Input
          name="teamname"
          type="text"
          placeholder="Stark Industries"
          styleClass="InputLogin"
          error={this.state.errors.teamname}
          changeHandler={this.changeHandler}
        />
        <p className="InputTitle">Shorthand Identifier</p>
        <Input
          name="identifier"
          type="text"
          placeholder="stark"
          styleClass="InputLogin"
          error={this.state.errors.identifier}
          changeHandler={this.changeHandler}
        />

      <svg className="Line">
        <line x1="0" y1="0" x2="1000" y2="0" className="ColorLine"/>
      </svg>

        <p className="InputTitle">Username</p>
        <Input
          name="username"
          type="text"
          placeholder="johnyDeep"
          styleClass="InputLogin"
          error={this.state.errors.username}
          changeHandler={this.changeHandler}
        />
        <p className="InputTitle">Password</p>
        <Input
          name="password"
          type="password"
          placeholder="Password"
          styleClass="InputLogin"
          error={this.state.errors.password}
          changeHandler={this.changeHandler}
        />
        <SubmitButton value="Sign up" hasErrors={this.hasErrors()} />
      </form>
    );
  }
}

export default SignUpForm;
