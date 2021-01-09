import axios from "axios";
import { API_URL } from "../../config/config";
const TODO_URL = "/toDos";

const ToDoApiUtils = {
  /*ToDos*/
  getToDos: (milestoneId) =>
    axios.get(API_URL + TODO_URL + "?milestoneId=" + milestoneId),
  getMyToDos: (milestoneId) =>
    axios.get(API_URL + TODO_URL + "/mine?milestoneId=" + milestoneId),
  addNewToDo: (milestoneId, toDo) =>
    axios.post(API_URL + TODO_URL + "?milestoneId=" + milestoneId, toDo),
  markToDoAsDone: (toDoId) =>
    axios.post(API_URL + TODO_URL + "/markAsDone?toDoId=" + toDoId),
};

export default ToDoApiUtils;
