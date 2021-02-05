import axios from "axios";
import ApiUtils from "./ApiUtils";
const PROJECT_URL = "/projects";

const ProjectApiUtils = {
  /*PROJECT*/
  getProjects: (departmentId) => ApiUtils.get(PROJECT_URL + `?departmentId=${departmentId}`),
  getMyProjects: (departmentId) => ApiUtils.get(PROJECT_URL + "/mine?departmentId=" + departmentId),
  postProject: (departmentId, project) => ApiUtils.post(PROJECT_URL + `?departmentId=${departmentId}`, project),
  /*MILESTONES*/
  getNextMilestone: (projectId) =>
    ApiUtils.get(PROJECT_URL + "/milestones"),
};

export default ProjectApiUtils;
