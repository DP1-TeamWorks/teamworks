import ApiUtils from "./ApiUtils";

const TAGS_URL = "/tags";

const TagApiUtils = {
  /*TAGS*/
  getTags: (projectId) => ApiUtils.get(TAGS_URL + "?projectId=" + projectId),
  getAllMyTags: () => ApiUtils.get(TAGS_URL + "/mine/all"),
  addNewTag: (projectId, tag) =>
    ApiUtils.post(TAGS_URL + "?projectId=" + projectId, tag),
  deleteTag: (projectId, tagId) =>
    ApiUtils.delete(TAGS_URL + `?projectId=${projectId}&tagId=${tagId}`),
};

export default TagApiUtils;
