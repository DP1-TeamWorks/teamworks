import axios from "axios";
import { API_URL } from "../../config/config";
import ApiUtils from "./ApiUtils";
const DEPARTMENT_URL = "/departments";

const DepartmentApiUtils = {
  /*DEPARTMENTS*/
  getDepartments: () => ApiUtils.get(DEPARTMENT_URL),
  postDepartment: (department) => ApiUtils.post(DEPARTMENT_URL, department),
  updateDepartment: (department) => ApiUtils.patch(DEPARTMENT_URL, department),
  deleteDepartment: (id) => ApiUtils.delete(DEPARTMENT_URL + "/" + id),
  getMyDepartments: () => ApiUtils.get(DEPARTMENT_URL + "/mine"),
  addUserToDepartment: (departmentId, userId) => ApiUtils.post(DEPARTMENT_URL + `/belongs?departmentId=${departmentId}&belongUserId=${userId}`)
};

export default DepartmentApiUtils;
