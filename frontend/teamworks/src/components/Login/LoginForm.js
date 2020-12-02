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
        <p>Team Mail:</p>
        <input type="text" name="mail" onChange={this.changeHandler} />
        <p className="error">{this.state.errors.mail}</p>
        <p>Password</p>
        <input type="password" name="password" onChange={this.changeHandler} />
        <p className="error">{this.state.errors.password}</p>
        <br />
        <br />
        <input type="submit" />
      </form>
    );
  }
}

export default LoginForm;
