import axios from "axios";
import { API_URL } from "../../config/config";
import ApiUtils from "./ApiUtils";
const PROJECT_URL = "/projects";

const ProjectApiUtils = {
  /*PROJECT*/
  getProjects: (departmentId) => axios.get(PROJECT_URL),
  getMyProjects: (departmentId) =>
    ApiUtils.get(PROJECT_URL + "/mine?departmentId=" + departmentId),

  /*MILESTONES*/
  getNextMilestone: (projectId) =>
    ApiUtils.get(PROJECT_URL + "/milestones"),
};

export default ProjectApiUtils;
