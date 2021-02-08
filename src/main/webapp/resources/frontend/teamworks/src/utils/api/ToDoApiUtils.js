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
  markToDoAsDone: (toDoId, unmark) => ApiUtils.post(TODO_URL + `/markAsDone?unmark=${unmark?1:0}&toDoId=${toDoId}`),
  createTodo: (milestoneId, todo) => ApiUtils.post(TODO_URL + "/create?milestoneId=" + milestoneId, todo),
  assignTodo: (milestoneId, todoId, assigneeId) => ApiUtils.post(TODO_URL + `/assign?todoId=${todoId}&milestoneId=${milestoneId}&assigneeId=${assigneeId}`),
  updateTitle: (milestoneId, todo) => ApiUtils.post(TODO_URL + `/updateTitle?todoId=${todo.id}&milestoneId=${milestoneId}`, todo),
  setTags: (milestoneId, todoId, tagIds) => ApiUtils.post(TODO_URL + `/setTags?todoId=${todoId}&milestoneId=${milestoneId}`, tagIds),
  deleteTodo: (milestoneId, todoId) => ApiUtils.delete(TODO_URL + `/delete?milestoneId=${milestoneId}&toDoId=${todoId}`)

};

export default ToDoApiUtils;
