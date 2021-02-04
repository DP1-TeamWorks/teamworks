import React, { useState, useEffect } from "react";
import Input from "../forms/Input";
import InboxSidebar from "../sidebar/InboxSidebar";
import Inbox from "../../sections/Inbox";
import GradientButton from "../buttons/GradientButton";
import NewMessageMultiSelect from "./NewMessageMultiSelect";
import NewMessageSelect from "./NewMessageSelect";
import NewMessageText from "./NewMessageText";
import NewMessageSubject from "./NewMessageSubject";
import ProjectPicker from "../projects/ProjectPicker";
import DepartmentApiUtils from "../../utils/api/DepartmentApiUtils";
import ProjectApiUtils from "../../utils/api/ProjectApiUtils";

//UseEffect que traiga usuarios
//pickeddepartment useState
//pickedProject useState


class NewMessage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      inputs: {
        To: "",
        Department: "",
        Project: "",
        Tags: "",
        Subject: "",
        ToDos: "",
        Message: "",
      },
      errors: {},
      requestError: "",
    };

    this.ChangeModalNewMessage = props.ChangeModalNewMessage;
  }


  hasErrors = () => {
    const errors = Object.values(this.state.errors);
    const nInputs = Object.keys(this.state.inputs).length;
    let b =
      errors.length < 2
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
      case "Subject":
        if (!/^[A-Za-z0-9_-]*$/.test(value)) {
          errorMsg = "Invalid, use letters and numbers";
        }
        break;
      case "Message":
        if (value === "") {
          errorMsg = "Message required";
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

  submitHandler = (event) => {
    event.preventDefault();
    this.validateAll();
    if (!this.hasErrors()) {
      let Subject = this.state.inputs.Subject;
      let Message = this.state.inputs.Message;
    } else {
      console.log("There are errors in this form");
      console.log(this.state.errors);
    }
  };

  

  render() {

    const Options = ({ pickedProject, setPickedProject }) => {
      const [departmentList, setDepartmentList] = useState([]);
      const [projectList, setProjectList] = useState([]);
      const [userList, setUserList] = useState([]);

      const [department, setDepartment] = useState("DPT");
      const [pickedDepartment, setPickedDepartment] = useState({});
      const [opened, setOpened] = useState(false);
    
      useEffect(()=> {
        
      }, []);

      useEffect(() => {
        DepartmentApiUtils.getMyDepartments()
          .then((res) => {
            setDepartmentList(res);
          })
          .catch((error) => {
            console.log("ERROR: cannot get the departments");
          });
      }, []);
    
      useEffect(() => {
        ProjectApiUtils.getMyProjects(department.id)
          .then((res) => {
            setProjectList(res);
          })
          .catch((error) => {
            console.log("ERROR: cannot get the projects");
          });
      }, []);
    
      const openOrClose = () => {
        setOpened(!opened);
      };
    
      const handlePickDepartment = (dpt) => {
        console.log("GETTIN PROJECTS");
        setPickedDepartment(dpt);
        ProjectApiUtils.getMyProjects(dpt.id)
          .then((res) => {
            setProjectList(res);
          })
          .catch((error) => {
            console.log("ERROR: cannot get the projects");
          });
      };
    
      const handlePickProject = (project) => {
        setDepartment(pickedDepartment);
        setPickedProject(project);
        openOrClose();
      };
    };

    
    return (
      <div className="NewMsgModal">
        <div className="NewMsgContainer">
          <img
            className="CloseNewMsg"
            onClick={this.ChangeModalNewMessage}
            alt=""
            src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDMyOS4yNjkzMyAzMjkiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTIiIHhtbDpzcGFjZT0icHJlc2VydmUiIGNsYXNzPSIiPjxnPjxwYXRoIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgZD0ibTE5NC44MDA3ODEgMTY0Ljc2OTUzMSAxMjguMjEwOTM4LTEyOC4yMTQ4NDNjOC4zNDM3NS04LjMzOTg0NCA4LjM0Mzc1LTIxLjgyNDIxOSAwLTMwLjE2NDA2My04LjMzOTg0NC04LjMzOTg0NC0yMS44MjQyMTktOC4zMzk4NDQtMzAuMTY0MDYzIDBsLTEyOC4yMTQ4NDQgMTI4LjIxNDg0NC0xMjguMjEwOTM3LTEyOC4yMTQ4NDRjLTguMzQzNzUtOC4zMzk4NDQtMjEuODI0MjE5LTguMzM5ODQ0LTMwLjE2NDA2MyAwLTguMzQzNzUgOC4zMzk4NDQtOC4zNDM3NSAyMS44MjQyMTkgMCAzMC4xNjQwNjNsMTI4LjIxMDkzOCAxMjguMjE0ODQzLTEyOC4yMTA5MzggMTI4LjIxNDg0NGMtOC4zNDM3NSA4LjMzOTg0NC04LjM0Mzc1IDIxLjgyNDIxOSAwIDMwLjE2NDA2MyA0LjE1NjI1IDQuMTYwMTU2IDkuNjIxMDk0IDYuMjUgMTUuMDgyMDMyIDYuMjUgNS40NjA5MzcgMCAxMC45MjE4NzUtMi4wODk4NDQgMTUuMDgyMDMxLTYuMjVsMTI4LjIxMDkzNy0xMjguMjE0ODQ0IDEyOC4yMTQ4NDQgMTI4LjIxNDg0NGM0LjE2MDE1NiA0LjE2MDE1NiA5LjYyMTA5NCA2LjI1IDE1LjA4MjAzMiA2LjI1IDUuNDYwOTM3IDAgMTAuOTIxODc0LTIuMDg5ODQ0IDE1LjA4MjAzMS02LjI1IDguMzQzNzUtOC4zMzk4NDQgOC4zNDM3NS0yMS44MjQyMTkgMC0zMC4xNjQwNjN6bTAgMCIgZmlsbD0iI2ZmZmZmZiIgZGF0YS1vcmlnaW5hbD0iIzAwMDAwMCIgc3R5bGU9IiIgY2xhc3M9IiI+PC9wYXRoPjwvZz48L3N2Zz4="
          />
          <form onSubmit={this.submitHandler} className="NewMsgForm">
            <NewMessageMultiSelect
              name="To"
              placeholder="Users"
              changeHandler={this.changeHandler}
            ></NewMessageMultiSelect>
            <div className="SecondLineFlex">
              <NewMessageSelect
                name="Department"
                placeholder="DPT"
                changeHandler={this.changeHandler}
                options = {this.departmentList}
              ></NewMessageSelect>
              <NewMessageSelect
                name="Project"
                placeholder="Project"
                options={this.projectList}
                changeHandler={this.changeHandler}
              ></NewMessageSelect>
              <NewMessageMultiSelect
                name="Tags"
                placeholder="Tags"
                changeHandler={this.changeHandler}
              ></NewMessageMultiSelect>
            </div>
            <div className="NewMsg">
              <div className="NewMsgHeadLabel">
                <span className="NewMsgHeadText">Subject</span>
                <Input
                  name="Subject"
                  styleClass="Input NewMsgHead InputNewMsg"
                  placeholder="How are you?"
                  error={this.state.errors.Subject}
                  changeHandler={this.changeHandler}
                ></Input>
                <NewMessageMultiSelect
                  name="ToDos"
                  placeholder="ToDos"
                  changeHandler={this.changeHandler}
                ></NewMessageMultiSelect>
              </div>

              <NewMessageText
                name="Message"
                error={this.state.errors.Message}
                changeHandler={this.changeHandler}
              ></NewMessageText>
            </div>
            <div className="NewMsgSendOptions">
              <img
                className="NewMsgUpload"
                src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDQ3NS4wNzggNDc1LjA3NyIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNTEyIDUxMiIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSIgY2xhc3M9IiI+PGc+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+Cgk8Zz4KCQk8cGF0aCBkPSJNNDY3LjA4MSwzMjcuNzY3Yy01LjMyMS01LjMzMS0xMS43OTctNy45OTQtMTkuNDExLTcuOTk0aC0xMjEuOTFjLTMuOTk0LDEwLjY1Ny0xMC43MDUsMTkuNDExLTIwLjEyNiwyNi4yNjIgICAgYy05LjQyNSw2Ljg1Mi0xOS45MzgsMTAuMjgtMzEuNTQ2LDEwLjI4aC03My4wOTZjLTExLjYwOSwwLTIyLjEyNi0zLjQyOS0zMS41NDUtMTAuMjhjLTkuNDIzLTYuODUxLTE2LjEzLTE1LjYwNC0yMC4xMjctMjYuMjYyICAgIEgyNy40MDhjLTcuNjEyLDAtMTQuMDgzLDIuNjYzLTE5LjQxNCw3Ljk5NEMyLjY2NCwzMzMuMDkyLDAsMzM5LjU2MywwLDM0Ny4xNzh2OTEuMzYxYzAsNy42MSwyLjY2NCwxNC4wODksNy45OTQsMTkuNDEgICAgYzUuMzMsNS4zMjksMTEuODAxLDcuOTkxLDE5LjQxNCw3Ljk5MWg0MjAuMjY2YzcuNjEsMCwxNC4wODYtMi42NjIsMTkuNDEtNy45OTFjNS4zMzItNS4zMjgsNy45OTQtMTEuOCw3Ljk5NC0xOS40MXYtOTEuMzYxICAgIEM0NzUuMDc4LDMzOS41NjMsNDcyLjQxNiwzMzMuMDk5LDQ2Ny4wODEsMzI3Ljc2N3ogTTM2MC4wMjUsNDIzLjk3OGMtMy42MjEsMy42MTctNy45MDUsNS40MjgtMTIuODU0LDUuNDI4ICAgIHMtOS4yMjctMS44MTEtMTIuODQ3LTUuNDI4Yy0zLjYxNC0zLjYxMy01LjQyMS03Ljg5OC01LjQyMS0xMi44NDdzMS44MDctOS4yMzYsNS40MjEtMTIuODQ3YzMuNjItMy42MTMsNy44OTgtNS40MjgsMTIuODQ3LTUuNDI4ICAgIHM5LjIzMiwxLjgxNCwxMi44NTQsNS40MjhjMy42MTMsMy42MSw1LjQyMSw3Ljg5OCw1LjQyMSwxMi44NDdTMzYzLjYzOCw0MjAuMzY0LDM2MC4wMjUsNDIzLjk3OHogTTQzMy4xMDksNDIzLjk3OCAgICBjLTMuNjE0LDMuNjE3LTcuODk4LDUuNDI4LTEyLjg0OCw1LjQyOGMtNC45NDgsMC05LjIyOS0xLjgxMS0xMi44NDctNS40MjhjLTMuNjEzLTMuNjEzLTUuNDItNy44OTgtNS40Mi0xMi44NDcgICAgczEuODA3LTkuMjM2LDUuNDItMTIuODQ3YzMuNjE3LTMuNjEzLDcuODk4LTUuNDI4LDEyLjg0Ny01LjQyOGM0Ljk0OSwwLDkuMjMzLDEuODE0LDEyLjg0OCw1LjQyOCAgICBjMy42MTcsMy42MSw1LjQyNyw3Ljg5OCw1LjQyNywxMi44NDdTNDM2LjcyOSw0MjAuMzY0LDQzMy4xMDksNDIzLjk3OHoiIGZpbGw9IiNhN2UzMmQiIGRhdGEtb3JpZ2luYWw9IiMwMDAwMDAiIHN0eWxlPSIiPjwvcGF0aD4KCQk8cGF0aCBkPSJNMTA5LjYzMiwxNzMuNTloNzMuMDg5djEyNy45MDljMCw0Ljk0OCwxLjgwOSw5LjIzMiw1LjQyNCwxMi44NDdjMy42MTcsMy42MTMsNy45LDUuNDI3LDEyLjg0Nyw1LjQyN2g3My4wOTYgICAgYzQuOTQ4LDAsOS4yMjctMS44MTMsMTIuODQ3LTUuNDI3YzMuNjE0LTMuNjE0LDUuNDIxLTcuODk4LDUuNDIxLTEyLjg0N1YxNzMuNTloNzMuMDkxYzcuOTk3LDAsMTMuNjEzLTMuODA5LDE2Ljg0NC0xMS40MiAgICBjMy4yMzctNy40MjIsMS45MDItMTMuOTktMy45OTctMTkuNzAxTDI1MC4zODUsMTQuNTYyYy0zLjQyOS0zLjYxNy03LjcwNi01LjQyNi0xMi44NDctNS40MjZjLTUuMTM2LDAtOS40MTksMS44MDktMTIuODQ3LDUuNDI2ICAgIEw5Ni43ODYsMTQyLjQ2OWMtNS45MDIsNS43MTEtNy4yMzMsMTIuMjc1LTMuOTk5LDE5LjcwMUM5Ni4wMjYsMTY5Ljc4NSwxMDEuNjQsMTczLjU5LDEwOS42MzIsMTczLjU5eiIgZmlsbD0iI2E3ZTMyZCIgZGF0YS1vcmlnaW5hbD0iIzAwMDAwMCIgc3R5bGU9IiI+PC9wYXRoPgoJPC9nPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjwvZz48L3N2Zz4="
              />
              <GradientButton
                className="NewMsgSendButton "
                requestError={this.state.requestError}
                hasErrors={this.hasErrors()}
              >
                Send
              </GradientButton>
            </div>
          </form>
        </div>
      </div>
    );
  }
}

export default NewMessage;
