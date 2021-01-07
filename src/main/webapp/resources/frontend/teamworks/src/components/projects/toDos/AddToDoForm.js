import React from "react";
import ProjectApiUtils from "../../../utils/api/ProjectApiUtils";
import Input from "../../forms/Input";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";

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
    if (value === "") {
      errorMsg = "ToDo required";
    } else if (value.length > 14) {
      errorMsg = "Too Long toDo";
    }
    this.setState({
      errors: { ...this.state.errors, toDo: errorMsg },
    });
  };

  changeHandler = (event) => {
    let value = event.target.value;
    this.validate(value);
    this.setState({ inputs: { ...this.state.inputs, toDo: value } });
  };

  apiRequestHandler = (toDo) => {
    ProjectApiUtils.addNewToDo({ toDo: toDo, tagList: [] })
      .then((res) => {
        this.props.setReloadToDos(true);
      })
      .catch((error) => {
        console.log("ERROR: cannot add the new todo");
        this.setState({
          requestError: "Cannot add the new todo",
        });
      });
  };

  submitHandler = (event) => {
    event.preventDefault();
    if (!this.hasErrors()) {
      let toDo = this.state.inputs.toDo;
      //Call API request in order to receive the user for the session
      this.apiRequestHandler(toDo);
    }
  };

  render() {
    return (
      <form onSubmit={this.submitHandler} style={{ display: "inline-block" }}>
        <span className="ToDoAdd" onClick={null}>
          <FontAwesomeIcon icon={faPlus} style={{ color: "lightgray" }} />
        </span>{" "}
        <Input
          placeholder="Add new ToDo ..."
          styleClass="InputNewToDo"
          changeHandler={this.changeHandler}
        />
      </form>
    );
  }
}

export default AddToDoForm;
