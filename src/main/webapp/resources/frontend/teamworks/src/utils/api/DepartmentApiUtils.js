import axios from "axios";
import { API_URL } from "../../config/config";
const DEPARTMENT_URL = "/departments";

const DepartmentApiUtils = {
  /*DEPARTMENTS*/
  getDepartments: (projectId) => axios.get(API_URL + DEPARTMENT_URL),
  getMyDepartments: (projectId) =>
    axios.get(API_URL + DEPARTMENT_URL + "/mine"),
};

export default DepartmentApiUtils;
