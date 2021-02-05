import axios from "axios";
import { API_URL } from "../../config/config";
import ApiUtils from "./ApiUtils";
const DEPARTMENT_URL = "/departments";
const BELONGS_URL = "/belongs";

const DepartmentApiUtils = {
  /*DEPARTMENTS*/
  getDepartments: () => ApiUtils.get(DEPARTMENT_URL),
  postDepartment: (department) => ApiUtils.post(DEPARTMENT_URL, department),
  updateDepartment: (department) => ApiUtils.patch(DEPARTMENT_URL, department),
  deleteDepartment: (id) => ApiUtils.delete(DEPARTMENT_URL + "/" + id),
  getMyDepartments: () => ApiUtils.get(DEPARTMENT_URL + "/mine"),
  addUserToDepartment: (departmentId, userId, departmentManager) => 
  {
    if (departmentManager !== undefined)
    {
      return ApiUtils.post(DEPARTMENT_URL + BELONGS_URL + `?departmentId=${departmentId}&belongUserId=${userId}&isDepartmentManager=${departmentManager}`);
    } else
    {
      return ApiUtils.post(DEPARTMENT_URL + BELONGS_URL + `?departmentId=${departmentId}&belongUserId=${userId}`);
    }
  },
  removeUserFromDepartment: (departmentId, userId) => 
  {
    return ApiUtils.delete(DEPARTMENT_URL + BELONGS_URL + `?departmentId=${departmentId}&belongUserId=${userId}`);
  },
  getMembersFromDepartment: (departmentId) => ApiUtils.get(DEPARTMENT_URL + BELONGS_URL + `?departmentId=${departmentId}`)
};

export default DepartmentApiUtils;
