const PROJECT_URL = "/projects";

const TagApiUtils = {
  /*TAGS*/
  getTags: (projectId) =>
    ApiUtils.get(PROJECT_URL + "/tags?projectId=" + projectId),
  addNewTag: (projectId, tag) =>
    ApiUtils.post(PROJECT_URL + "/tags?projectId=" + projectId, tag),
  deleteTag: (tagId) =>
    ApiUtils.delete(PROJECT_URL + "/tags?tagId=" + tagId),
};

export default TagApiUtils;
