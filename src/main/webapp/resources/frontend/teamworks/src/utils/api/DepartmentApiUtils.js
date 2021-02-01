import axios from "axios";
import { API_URL } from "../../config/config";
import ApiUtils from "./ApiUtils";
const DEPARTMENT_URL = "/departments";

const DepartmentApiUtils = {
  /*DEPARTMENTS*/
  getDepartments: (projectId) => ApiUtils.get(DEPARTMENT_URL),
  getMyDepartments: (projectId) => ApiUtils.get(DEPARTMENT_URL + "/mine"),
};

export default DepartmentApiUtils;
