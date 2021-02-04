import React from "react";
import Input from "./Input";
import SubmitButton from "./SubmitButton";
import "./AddUserForm.css";
import InputAutocompleteUser from "./InputAutocompleteUser";

class AddForm extends React.Component
{
  constructor(props)
  {
    super(props);
    this.state = {
      inputs: {},
      errors: {},
      requestError: "",
      children: [],
      isSubmitting: false,
      autocompleteVal: null
    };
    this.submitText = props.submitText ?? "Add element";
    this.postFunction = props.postFunction;
    this.children = props.children;
    this.alreadyExistsErrorText = props.alreadyExistsErrorText ?? "An element with the same name already exists.";
    this.disabled = props.disabled;
    this.onAutocompleteSelected = props.onAutocompleteSelected;
  }

  hasErrors = () =>
  {
    const errors = Object.values(this.state.errors);
    return errors.some((e) =>
    {
      return e !== "";
    });
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
    // same validation for all fields
    if (value === "")
    {
      errorMsg = "You can't leave this field empty";
    } else if (!/^[A-Za-z0-9_\s]*$/.test(value))
    {
      errorMsg = "Invalid, use letters and numbers";
    }
    this.setState({
      errors: { ...this.state.errors, [field]: errorMsg },
    });
  };

  changeHandler = (event) =>
  {
    let field = event.target.name;
    let value = event.target.value;
    this.validate(field, value);
    this.setState({ inputs: { ...this.state.inputs, [field]: value } });
  };

  apiRequestHandler = (postObject) =>
  {
    if (!this.postFunction)
    {
      this.setState({
        inputs: {},
        errors: {},
        requestError: "",
      });
      return;
    }
    this.setState({ isSubmitting: true });
    this.postFunction(postObject)
      .then(() =>
      {
        this.setState({
          inputs: {},
          errors: {},
          requestError: "",
          isSubmitting: false
        });
      })
      .catch((error) =>
      {
        console.error(error);
        const requestError = error.response.data === "alreadyexists" ? this.alreadyExistsErrorText : "Something went wrong";
        this.setState({
          requestError: requestError,
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
      let postObject = this.state.inputs;
      //Call API request
      this.apiRequestHandler(postObject);
    } else
    {
      console.log("There are errors in this form");
      console.log(this.state.errors);
    }
  };

  onAutocomplete = (field, value) =>
  {
    this.setState({autocompleteVal: value, inputs: { ...this.state.inputs, [field]: value }})
    if (this.onAutocompleteSelected)
      this.onAutocompleteSelected(field, value);
  };

  processChildren()
  {
    let inputs = {}
    const inputChildren = React.Children.map(this.children, child =>
      {
        if (React.isValidElement(child))
        {
          if (child.type === Input)
          {
            let name = child.props.name;
            let placeholder = child.props.placeholder;
            inputs[name] = this.state.inputs[name] ?? '';
            return (
              <div className="EditableField EditableField--OnlyInput">
                <Input
                  name={name}
                  styleClass="Input EditingInput"
                  placeholder={placeholder}
                  value={this.state.inputs[name]??''}
                  error={this.state.errors[name]??''}
                  changeHandler={this.changeHandler} />
              </div>
            );
          } else if (child.type === InputAutocompleteUser)
          {
            let name = child.props.name;
            inputs[name] = this.state.inputs[name] ?? '';
            if (!this.state.autocompleteVal)
              this.setState({autocompleteVal: -1}); // there's an autocompleted child so we set the state accordingly
            return React.cloneElement(child, 
              {
                onUserSelected: this.onAutocomplete,
                val: this.state.inputs[name]??''
              });
          }
        }
        return child;
      });
    this.setState({ inputs: inputs });
    this.setState({children: inputChildren});
  }

  componentDidMount()
  {
    this.processChildren();
  }

  componentDidUpdate(prevProps, prevState)
  {
    if (JSON.stringify(this.state.inputs) !== JSON.stringify(prevState.inputs))
      this.processChildren();
  }

  render()
  {
    return (
      <form onSubmit={this.submitHandler}>
        {this.state.children}
        <SubmitButton
          reducedsize
          text={this.submitText}
          requestError={this.state.requestError}
          hasErrors={this.hasErrors() || (this.state.autocompleteVal && this.state.autocompleteVal === -1)}
          loading={this.state.isSubmitting}
        />
      </form>
    );
  }
}

export default AddForm;
