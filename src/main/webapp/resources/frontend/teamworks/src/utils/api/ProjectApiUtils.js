import axios from "axios";
import { API_URL } from "../../config/config";
const PROJECT_URL = "/project";

const ProjectApiUtils = {
  /*TAGS*/
  getTags: (projectId) =>
    axios.get(API_URL + PROJECT_URL + "/tags?projectId=" + projectId),
  addNewTag: (projectId, tag) =>
    axios.post(API_URL + PROJECT_URL + "/tags?projectId=" + projectId, tag),
  deleteTag: (tagId) =>
    axios.delete(API_URL + PROJECT_URL + "/tags?tagId=" + tagId),

  /*ToDos*/
  getToDos: (projectId) =>
    axios.get(API_URL + PROJECT_URL + "/toDos?projectId=" + projectId),
  getMyToDos: (projectId) =>
    axios.get(
      API_URL + PROJECT_URL + "/toDos?projectId=" + projectId + "&my=true"
    ),
  addNewToDo: (projectId, toDo) =>
    axios.post(API_URL + PROJECT_URL + "/toDos?projectId=" + projectId, toDo),
  markAsDone: (toDoId) =>
    axios.post(API_URL + PROJECT_URL + "/toDos?toDoId=" + toDoId),
};

export default ProjectApiUtils;
