import axios from "axios";
import { API_URL } from "../../config/config";
const TEAM_URL = "/team";

const TeamApiUtils = {
  /*DEPARTMENTS*/
  getDepartments: (projectId) => axios.get(API_URL + TEAM_URL + "/departments"),
  getMyDepartments: (projectId) =>
    axios.get(API_URL + TEAM_URL + "/departments?mine=true"),
};

export default TeamApiUtils;
