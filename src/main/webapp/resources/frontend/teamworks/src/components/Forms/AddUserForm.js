import React from "react";
import AuthApiUtils from "../../utils/api/AuthApiUtils";
import Input from "./Input";
import SubmitButton from "./SubmitButton";
import "./AddUserForm.css";

class AddUserForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      inputs: {
        firstname: "",
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
      case "firstname":
        if (value === "") {
          errorMsg = "First name required";
        } else if (!/^[A-Za-z0-9_-]*$/.test(value)) {
          errorMsg = "Invalid, use letters and numbers";
        }
        break;
      case "lastname":
        if (value === "") {
          errorMsg = "Last name required";
        } else if (!/^[A-Za-z0-9_-]*$/.test(value)) {
          errorMsg = "Invalid, use letters and numbers";
        }
        break;
      case "password":
        if (value === "") {
          errorMsg = "Password required";
        } else if (value.length < 8) {
          errorMsg = "Passwords at least 8 characters";
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
        <p className="InputTitle">First Name</p>
        <div className="EditableField">
            <Input name="firstname" styleClass="Input EditingInput" placeholder="Johnny" error={this.state.errors.firstname} changeHandler={this.changeHandler}/>
        </div>
        <p className="InputTitle">Last Name</p>
        <div className="EditableField">
            <Input name="lastname" styleClass="Input EditingInput" placeholder="Depp" error={this.state.errors.lastname} changeHandler={this.changeHandler}/>
        </div>
        <p className="InputTitle">Password</p>
        <div className="EditableField">
            <Input name="password" type="password" styleClass="Input EditingInput" placeholder="More than 8 characters" error={this.state.errors.password} changeHandler={this.changeHandler}/>
        </div>
        <SubmitButton
          reducedsize
          text="Add user"
          requestError={this.state.requestError}
          hasErrors={this.hasErrors()}
        />
      </form>
    );
  }
}

export default AddUserForm;
