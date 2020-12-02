import React from "react";

class LoginForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      mail: "",
      password: "",
      errors: { mail: "", password: "" },
    };
  }

  validate = (field, value) => {
    switch (field) {
      case "mail":
        if (value === "") {
          this.setState({
            errors: { ...this.state.errors, mail: "Mail required" },
          });
        }
        break;
      case "password":
        if (value === "") {
          this.setState({
            errors: { ...this.state.errors, password: "Password required" },
          });
        }
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
  };

  render() {
    return (
      <form onSubmit={this.submitHandler}>
        <br/>
        <input placeholder="name@team" className="inputLogin" type="text" name="mail" onChange={this.changeHandler} />
        <p className="error">{this.state.errors.mail}</p>
        <br/>
        <input  placeholder="HardToGuessPassword" className="inputLogin" type="password" name="password" onChange={this.changeHandler} />
        <p className="error">{this.state.errors.password}</p>
        <br />
        <input className="loginButton" type="submit" value="Sign in"/>
      </form>
    );
  }
}

export default LoginForm;
