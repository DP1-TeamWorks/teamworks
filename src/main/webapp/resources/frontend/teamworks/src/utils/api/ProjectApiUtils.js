import axios from "axios";
import { API_URL } from "../../config/config";
const PROJECT_URL = "/projects";

const ProjectApiUtils = {
  /*PROJECT*/
  getProjects: (departmentId) => axios.get(API_URL + PROJECT_URL),
  getMyProjects: (departmentId) =>
    axios.get(API_URL + PROJECT_URL + "/mine?departmentId=" + departmentId),

  /*MILESTONES*/
  getNextMilestone: (projectId) =>
    axios.get(API_URL + PROJECT_URL + "/milestones"),
};

export default ProjectApiUtils;
