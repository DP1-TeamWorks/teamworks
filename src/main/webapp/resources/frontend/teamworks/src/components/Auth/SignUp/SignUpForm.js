import React from "react";
import AuthApiUtils from "../../../utils/api/AuthApiUtils";
import Input from "../../forms/Input";
import SubmitButton from "../../forms/SubmitButton";

class SignUpForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      inputs: {
        teamname: "",
        identifier: "",
        username: "",
        lastname: "",
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
    let errorMsg = "";
    switch (field) {
      case "teamname":
        if (value === "") {
          errorMsg = "TeamName required";
        } else if (!/^[A-Za-zÀ-ÿ0-9\u00f1\u00d1_-]*$/.test(value)) {
          errorMsg = "Invalid, use letters and numbers";
        } else if (value.length > 25) {
          errorMsg = "The name is too long";
        }
        break;
      case "identifier":
        if (value === "") {
          errorMsg = "Identifier required";
        } else if (!/^[A-Za-zÀ-ÿ\u00f1\u00d1_-]*$/.test(value)) {
          errorMsg = "Invalid, use letters and numbers";
        } else if (value.length > 25) {
          errorMsg = "The identifier is too long";
        }
        break;
      case "username":
        if (value === "") {
          errorMsg = "Username required";
        } else if (!/^[A-Za-zÀ-ÿ\u00f1\u00d1]*$/.test(value)) {
          errorMsg = "Invalid, use letters and numbers";
        } else if (value.length > 25) {
          errorMsg = "The name is too long";
        }
        break;
      case "lastname":
        if (value === "") {
          errorMsg = "Lastname required";
        } else if (!/^[A-Za-zÀ-ÿ\u00f1\u00d1]*$/.test(value)) {
          errorMsg = "Invalid, use letters and numbers";
        } else if (value.length > 120) {
          errorMsg = "The name is too long";
        }
        break;
      case "password":
        if (value === "") {
          errorMsg = "Password required";
        } else if (value.length < 8) {
          errorMsg = "Passwords must be larger";
        } else if (value.length > 25) {
          errorMsg = "The password is too long";
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

  apiRequestHandler = (teamname, identifier, username, lastname, password) => {
    AuthApiUtils.signup({
      teamname,
      identifier,
      username,
      lastname,
      password,
    })
      .then((res) => {
        console.log("Signed Up, redirecting to login");
        const mail = `${username.toLowerCase()}${lastname.toLowerCase()}@${identifier}`;
        AuthApiUtils.login({ mail, password }).then(() => {
          window.location.replace("/");
        });
      })
      .catch((error) => {
        console.log("AAAA" + error)
        console.log(error.response)
        if (error.response.data.includes("constraint")) {
          this.setState({
            requestError: "There's already a team or identifier with that name.",
          });
        } else {
          console.log("API ERROR!");
          console.log(error);
          this.setState({
            requestError: "We had a server problem",
          });
        }
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
        <p className="InputTitle">Team name</p>
        <Input
          name="teamname"
          type="text"
          placeholder="Stark Industries"
          styleClass="Input InputLogin"
          error={this.state.errors.teamname}
          changeHandler={this.changeHandler}
        />
        <p className="InputTitle">Shorthand Identifier</p>
        <Input
          name="identifier"
          type="text"
          placeholder="stark"
          styleClass="Input InputLogin"
          error={this.state.errors.identifier}
          changeHandler={this.changeHandler}
        />
        <br />
        <span className={"PreviewWord"}>PREVIEW</span>{" "}
        <span className={"Preview"}>
          {this.state.inputs.username.toLowerCase()}
          {this.state.inputs.lastname}@{this.state.inputs.identifier}
        </span>
        <svg className="Line">
          <line x1="0" y1="0" x2="1000" y2="0" className="ColorLine" />
        </svg>
        <p className="InputTitle">Name</p>
        <Input
          name="username"
          type="text"
          placeholder="Johnny"
          styleClass="Input InputLogin"
          error={this.state.errors.username}
          changeHandler={this.changeHandler}
        />
        <p className="InputTitle">LastName</p>
        <Input
          name="lastname"
          type="text"
          placeholder="Deep"
          styleClass="Input InputLogin"
          error={this.state.errors.lastname}
          changeHandler={this.changeHandler}
        />
        <p className="InputTitle">Password</p>
        <Input
          name="password"
          type="password"
          placeholder="Password"
          styleClass="Input InputLogin"
          error={this.state.errors.password}
          changeHandler={this.changeHandler}
        />
        <SubmitButton
          text="Sign up"
          requestError={this.state.requestError}
          hasErrors={this.hasErrors()}
        />
      </form>
    );
  }
}

export default SignUpForm;
