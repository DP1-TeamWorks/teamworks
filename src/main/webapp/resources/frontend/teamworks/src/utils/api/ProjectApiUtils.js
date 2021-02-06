import ApiUtils from "./ApiUtils";
const PROJECT_URL = "/projects";
const PARTICIPATION_URL = "/participation";

const ProjectApiUtils = {
  /*PROJECT*/
  getProjects: (departmentId) => ApiUtils.get(PROJECT_URL + `?departmentId=${departmentId}`),
  getMyProjects: (departmentId) => ApiUtils.get(PROJECT_URL + "/mine?departmentId=" + departmentId),
  postProject: (departmentId, project) => ApiUtils.post(PROJECT_URL + `?departmentId=${departmentId}`, project),
  updateProject: (departmentId, projectId, project) => ApiUtils.patch(PROJECT_URL + `?departmentId=${departmentId}&projectId=${projectId}`, project),
  deleteProject: (departmentId, projectId) => ApiUtils.delete(PROJECT_URL + `?departmentId=${departmentId}&projectId=${projectId}`),
  addUserToProject: (projectId, userId, projectManager) => 
  {
    if (projectManager !== undefined)
    {
      return ApiUtils.post(PROJECT_URL + PARTICIPATION_URL + `?projectId=${projectId}&participationUserId=${userId}&willBeProjectManager=${projectManager}`);
    } else
    {
      return ApiUtils.post(PROJECT_URL + PARTICIPATION_URL + `?projectId=${projectId}&participationUserId=${userId}`);
    }
  },
  removeUserFromProject: (projectId, userId) => 
  {
    return ApiUtils.delete(PROJECT_URL + PARTICIPATION_URL + `?projectId=${projectId}&participationUserId=${userId}`);
  },
  getMembersFromProject: (projectId) => ApiUtils.get(PROJECT_URL + PARTICIPATION_URL + `?projectId=${projectId}`)
};

export default ProjectApiUtils;
