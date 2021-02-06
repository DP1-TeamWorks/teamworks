import axios from "axios";
import { API_URL } from "../../config/config";
import ApiUtils from "./ApiUtils";
const TAGS_URL = "/tags";

const TagApiUtils = {
  /*TAGS*/
  getTags: (projectId) =>
    ApiUtils.get(TAGS_URL + "?projectId=" + projectId),
  addNewTag: (projectId, tag) =>
    ApiUtils.post(TAGS_URL + "?projectId=" + projectId, tag),
  deleteTag: (tagId) =>
    ApiUtils.delete(TAGS_URL + "?tagId=" + tagId),

  getAllMyTags: () => ApiUtils.get(TAGS_URL + "/mine/all")
};

export default TagApiUtils;
