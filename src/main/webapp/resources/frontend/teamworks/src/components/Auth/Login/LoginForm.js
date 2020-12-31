import React from "react";
import AuthApiUtils from "../../../utils/api/AuthApiUtils";
import Input from "../../forms/Input";
import SubmitButton from "../../forms/SubmitButton";

class LoginForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      inputs: {
        mail: "",
        password: "",
      },
      errors: {},
      requestError: "",
    };
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
    this.setState();
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

  apiRequestHandler = (mail, password) => {
    AuthApiUtils.login({ mail, password })
      .then((res) => {
        this.props.onLoginChanged(true);
      })
      .catch((error) => {
        console.log("API ERROR!");
        console.log(error);
        this.setState({
          requestError: "User not Found",
        });
      });
  };

  submitHandler = (event) => {
    event.preventDefault();
    this.validateAll();
    if (!this.hasErrors()) {
      let mail = this.state.inputs.mail;
      let password = this.state.inputs.password;
      //Call API request in order to receive the user for the session
      this.apiRequestHandler(mail, password);
    }
  };

  render() {
    return (
      <form onSubmit={this.submitHandler}>
        <br />
        <Input
          name="mail"
          type="text"
          placeholder="name@team"
          styleClass="Input InputLogin"
          error={this.state.errors.mail}
          changeHandler={this.changeHandler}
        />
        <br />
        <Input
          name="password"
          type="password"
          placeholder="Password"
          styleClass="Input InputLogin"
          error={this.state.errors.password}
          changeHandler={this.changeHandler}
        />
        <br />
        <SubmitButton
          text="Log in"
          requestError={this.state.requestError}
          hasErrors={this.hasErrors()}
        />
      </form>
    );
  }
}

export default LoginForm;
