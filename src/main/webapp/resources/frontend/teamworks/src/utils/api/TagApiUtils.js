import axios from "axios";
import { API_URL } from "../../config/config";
const PROJECT_URL = "/projects";

const TagApiUtils = {
  /*TAGS*/
  getTags: (projectId) =>
    axios.get(API_URL + PROJECT_URL + "/tags?projectId=" + projectId),
  addNewTag: (projectId, tag) =>
    axios.post(API_URL + PROJECT_URL + "/tags?projectId=" + projectId, tag),
  deleteTag: (tagId) =>
    axios.delete(API_URL + PROJECT_URL + "/tags?tagId=" + tagId),
};

export default TagApiUtils;
