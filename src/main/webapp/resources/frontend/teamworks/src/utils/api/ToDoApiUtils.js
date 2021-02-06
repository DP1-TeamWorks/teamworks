import ApiUtils from "./ApiUtils";
const TODO_URL = "/toDos";

const ToDoApiUtils = {
  /*ToDos*/
  getToDos: (milestoneId) =>
    ApiUtils.get(TODO_URL + "?milestoneId=" + milestoneId),
  getMyToDos: (milestoneId) =>
    ApiUtils.get(TODO_URL + "/mine?milestoneId=" + milestoneId),
  getAllMyToDos: () => ApiUtils.get(TODO_URL + "/mine/all"),
  addNewPersonalToDo: (milestoneId, toDo) =>
    ApiUtils.post(TODO_URL + "/mine?milestoneId=" + milestoneId, toDo),
  markToDoAsDone: (toDoId) =>
    ApiUtils.post(TODO_URL + "/markAsDone?toDoId=" + toDoId),
};

export default ToDoApiUtils;
