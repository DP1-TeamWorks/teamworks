import React from "react";
import AuthApiUtils from "../../utils/api/AuthApiUtils";
import Input from "./Input";
import SubmitButton from "./SubmitButton";
import "./AddUserForm.css";
import UserApiUtils from "../../utils/api/UserApiUtils";

class AddUserForm extends React.Component
{
  constructor(props)
  {
    super(props);
    this.state = {
      inputs: {
        name: "",
        lastname: "",
        password: "",
      },
      errors: {},
      requestError: "",
      isSubmitting: false
    };

    this.onUserRegistered = props.onUserRegistered;
  }

  hasErrors = () =>
  {
    const errors = Object.values(this.state.errors);
    const nInputs = Object.keys(this.state.inputs).length;
    let b =
      errors.length < nInputs
        ? true
        : errors.some((e) =>
        {
          return e !== "";
        });
    return b;
  };

  validateAll = () =>
  {
    Object.entries(this.state.inputs).forEach((e) =>
    {
      const [k, v] = e;
      this.validate(k, v);
    });
  };

  validate = (field, value) =>
  {
    this.setState({
      requestError: "",
    });
    let errorMsg = "";
    switch (field)
    {
      case "name":
        if (value === "")
        {
          errorMsg = "First name required";
        } else if (!/^[a-zA-ZÀ-ÿ\u00f1\u00d1 ]+$/.test(value))
        {
          errorMsg = "Invalid, use letters and numbers";
        }
        break;
      case "lastname":
        if (value === "")
        {
          errorMsg = "Last name required";
        } else if (!/^[a-zA-ZÀ-ÿ\u00f1\u00d1 ]+$/.test(value))
        {
          errorMsg = "Invalid, use letters and numbers";
        }
        break;
      case "password":
        if (value === "")
        {
          errorMsg = "Password required";
        } else if (value.length < 8)
        {
          errorMsg = "Passwords at least 8 characters";
        }
        break;
      default:
    }
    this.setState({
      errors: { ...this.state.errors, [field]: errorMsg },
    });
  };

  changeHandler = (field, value) =>
  {
    this.validate(field, value);
    this.setState({ inputs: { ...this.state.inputs, [field]: value } });
  };

  apiRequestHandler = (name, lastname, password) =>
  {
    this.setState({ isSubmitting: true });
    UserApiUtils.registerUser({
      name,
      lastname,
      password,
    })
      .then(() => 
      {
        this.setState({
          inputs: {},
          errors: {},
          requestError: "",
          isSubmitting: false
        });
        this.onUserRegistered()
      })
      .catch(err =>
      {
        console.log("API ERROR!");
        err = err.response.data;
        let error = "Something went wrong."
        if (err === "alreadyexists")
          error = "An user with that name already exists."
        else if (err === "errors")
          error = "Check your inputs and try again."
        this.setState({
          requestError: error,
          isSubmitting: false
        });
      });
  };

  submitHandler = (event) =>
  {
    event.preventDefault();
    this.validateAll();
    if (!this.hasErrors())
    {
      let name = this.state.inputs.name;
      let lastname = this.state.inputs.lastname;
      let password = this.state.inputs.password;
      //Call API request in order to receive the user for the session
      this.apiRequestHandler(
        name,
        lastname,
        password
      );
    } else
    {
      console.log("There are errors in this form");
      console.log(this.state.errors);
    }
  };

  render()
  {
    return (
      <form onSubmit={this.submitHandler} autoComplete="off">
        <p className="InputTitle">First Name</p>
        <div className="EditableField EditableField--OnlyInput">
          <Input
            name="name"
            styleClass="Input EditingInput"
            placeholder="Johnny"
            error={this.state.errors.name}
            changeHandler={this.changeHandler}
            value={this.state.inputs.name??''} />
        </div>
        <p className="InputTitle">Last Name</p>
        <div className="EditableField EditableField--OnlyInput">
          <Input
            name="lastname"
            styleClass="Input EditingInput"
            placeholder="Depp"
            error={this.state.errors.lastname}
            changeHandler={this.changeHandler}
            value={this.state.inputs.lastname??''} />
        </div>
        <p className="InputTitle">Password</p>
        <div className="EditableField EditableField--OnlyInput">
          <Input
            name="password"
            type="password"
            styleClass="Input EditingInput"
            placeholder="More than 8 characters"
            error={this.state.errors.password}
            changeHandler={this.changeHandler}
            value={this.state.inputs.password??''} />
        </div>
        <SubmitButton
          reducedsize
          text="Add user"
          requestError={this.state.requestError}
          hasErrors={this.hasErrors()}
          loading={this.state.isSubmitting}
        />
      </form>
    );
  }
}

export default AddUserForm;
