import React from "react";
import { login } from "../../../utils/api/authApiUtils";
import LSInput from "./LSInput";
import SubmitButton from "./SubmitButton";

class LoginForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      inputs: {
        mail: "",
        password: "",
      },
      errors: {},
      hasErrors: true,
    };
  }

  updateHasErrors = () => {
    let errors = Object.values(this.state.errors);
    let b =
      errors.length === 0
        ? true
        : errors.some((e) => {
            return e !== "";
          });
    this.setState({
      hasErrors: b,
    });
    console.log(this.state.hasErrors);
  };

  validateAll = () => {
    Object.entries(this.state.inputs).forEach((e) => {
      const [k, v] = e;
      console.log(k);
      console.log(v);
      this.validate(k, v);
    });
  };

  validate = (field, value) => {
    let errorMsg = "";
    switch (field) {
      case "mail":
        if (value === "") {
          errorMsg = "Mail required";
        } else if (
          !/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@+[a-zA-Z0-9-]/.test(value)
        ) {
          errorMsg = "Invalid Mail";
        }

        this.setState({
          errors: { ...this.state.errors, mail: errorMsg },
        });
        break;

      case "password":
        if (value === "") {
          errorMsg = "Password required";
        } else if (value.length < 8) {
          errorMsg = "Passwords are larger";
        }
        this.setState({
          errors: { ...this.state.errors, password: errorMsg },
        });
        break;
      default:
    }
    this.updateHasErrors();
  };

  changeHandler = (event) => {
    let field = event.target.name;
    let value = event.target.value;
    this.validate(field, value);
    this.setState({ inputs: { [field]: value } });
  };

  submitHandler = (event) => {
    event.preventDefault();
    if (!this.state.hasErrors) {
      this.validateAll();
      let mail = this.state.inputs.mail;
      let password = this.state.inputs.password;
      //Call API request in order to receive the user for the session
      login({ mail, password });
    } else {
      console.log("There are errors in this form");
      console.log(this.state.errors);
    }
  };

  render() {
    return (
      <form onSubmit={this.submitHandler}>
        <LSInput
          name="mail"
          type="text"
          placeholder="name@team"
          styleClass="inputLogin"
          error={this.state.errors.mail}
          changeHandler={this.changeHandler}
        />
        <LSInput
          name="password"
          type="password"
          placeholder="HardToGuessPassword"
          styleClass="inputLogin"
          error={this.state.errors.password}
          changeHandler={this.changeHandler}
        />
        <SubmitButton value="Sign in" hasErrors={this.state.hasErrors} />
      </form>
    );
  }
}

export default LoginForm;
