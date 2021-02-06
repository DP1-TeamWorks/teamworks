import React from "react";
import ProjectApiUtils from "../../../utils/api/ProjectApiUtils";
import Input from "../../forms/Input";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import ToDoApiUtils from "../../../utils/api/ToDoApiUtils";
import SubmitButton from "../../forms/SubmitButton";
import SubmitError from "../../forms/SubmitError";

class AddToDoForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      inputs: {
        toDo: "",
      },
      errors: {},
      requestError: "",
    };
    this.disable = props.shouldDisable;
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

  validate = (value) => {
    this.setState({
      requestError: "",
    });

    let errorMsg = "";
    if (value === "") errorMsg = "ToDo required";
    else if (value.length > 14) errorMsg = "Too Long toDo";

    this.setState({
      errors: { ...this.state.errors, toDo: errorMsg },
    });
  };

  changeHandler = (field, value) => {
    this.validate(value);
    this.setState({ inputs: { ...this.state.inputs, toDo: value } });
  };

  apiRequestHandler = (toDoTitle) => {
    ToDoApiUtils.addNewPersonalToDo(this.props.milestoneId, {
      title: toDoTitle,
      done: false,
    })
      .then((res) => {
        this.props.setReloadToDos(true);
      })
      .catch((error) => {
        console.log("ERROR: cannot add the new todo");
        console.log(error.response);
        if (error.response.data === "Many toDos assigned to milestone")
          this.setState({
            requestError: "Cannot add more toDos",
          });
      });
  };

  submitHandler = (event) => {
    event.preventDefault();
    if (!this.hasErrors()) {
      console.log("CREATING NEW TODO");
      let toDoTitle = this.state.inputs.toDo;
      this.setState({ inputs: { ...this.state.inputs, toDo: "" } });
      this.apiRequestHandler(toDoTitle);
      event.target.reset();
    }
  };

  render() {
    return (
      <form onSubmit={this.submitHandler}>
        <span className="ToDoAdd" onClick={null}>
          <FontAwesomeIcon icon={faPlus} style={{ color: "lightgray" }} />
        </span>{" "}
        {!this.shouldDisable && (
          <Input
            hide={this.nOfToDos === 7}
            placeholder="Add new ToDo ..."
            styleClass="InputNewToDo"
            changeHandler={this.changeHandler}
            error={this.state.errors.toDo}
          />
        )}
        <SubmitError error={this.requestError} />
      </form>
    );
  }
}

export default AddToDoForm;
