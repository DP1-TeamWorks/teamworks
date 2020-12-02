import React from "react";
import { login } from "../../utils/api/authApiUtils";

const hasErrors = (errors) => {
  let b =
    errors.length === 0
      ? true
      : errors.some((e) => {
          return e !== "";
        });
  return b;
};
class LoginForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      mail: "",
      password: "",
      errors: {},
      hasErrors: false,
    };
  }

  validate = (field, value) => {
    let errorMsg = "";
    switch (field) {
      case "mail":
        if (value === "") {
          errorMsg = "Mail required";
        }
        if (!/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@+[a-zA-Z0-9-]/.test(value)) {
          errorMsg = "Invalid Mail";
        }

        this.setState({
          errors: { ...this.state.errors, mail: errorMsg },
        });
        break;

      case "password":
        if (value === "") {
          errorMsg = "Password required";
        }
        if (value.length < 8) {
          errorMsg = "Passwords are larger";
        }
        this.setState({
          errors: { ...this.state.errors, password: errorMsg },
        });
        break;
      default:
    }
  };

  changeHandler = (event) => {
    let field = event.target.name;
    let value = event.target.value;
    this.validate(field, value);
    this.setState({ [field]: value });
  };

  submitHandler = (event) => {
    event.preventDefault();
    console.log(Object.values(this.state.errors));
    if (!hasErrors(Object.values(this.state.errors))) {
      let mail = this.state.mail;
      let password = this.state.password;
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
        <br/>
        <input placeholder="name@team" className="inputLogin" id="mailLogin" type="text" name="mail" onChange={this.changeHandler}
         style={{border: this.state.errors.mail &&  this.state.errors.mail !== "" ? "1px solid #e32d2d": ""}}/>
        <p className="error">{this.state.errors.mail}</p>
        <br/>
        <input  placeholder="HardToGuessPassword" className="inputLogin" id="passwordLogin" type="password" name="password" onChange={this.changeHandler}
         style={{border: this.state.errors.password &&  this.state.errors.password !== "" ? "1px solid #e32d2d": ""}}/>
        <p className="error">{this.state.errors.password}</p>
        <br />
        <input className="loginButton" type="submit" value="Sign in" disabled={hasErrors(Object.values(this.state.errors))}/>
      </form>
    );
  }
}

export default LoginForm;
