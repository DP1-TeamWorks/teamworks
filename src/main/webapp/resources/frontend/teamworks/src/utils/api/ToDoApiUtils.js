import axios from "axios";
import { API_URL } from "../../config/config";
import ApiUtils from "./ApiUtils";
const TODO_URL = "/toDos";

const ToDoApiUtils = {
  /*ToDos*/
  getToDos: (milestoneId) =>
    ApiUtils.get(TODO_URL + "?milestoneId=" + milestoneId),
  getMyToDos: (milestoneId) =>
    ApiUtils.get(TODO_URL + "/mine?milestoneId=" + milestoneId),
  addNewPersonalToDo: (milestoneId, toDo) =>
    ApiUtils.post(TODO_URL + "/mine?milestoneId=" + milestoneId, toDo),
  markToDoAsDone: (toDoId) =>
    ApiUtils.post(TODO_URL + "/markAsDone?toDoId=" + toDoId),
};

export default ToDoApiUtils;
