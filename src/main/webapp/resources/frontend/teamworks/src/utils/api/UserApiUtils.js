import ApiUtils from "./ApiUtils";
const USER_URL = "/user";
const USERS_URL = "/users";

const UserApiUtils = {
  getUserById: (userId) => ApiUtils.get(USER_URL + "?userId=" + userId),
  getUsers: () => ApiUtils.get(USERS_URL),
  registerUser: (user) => ApiUtils.post(USER_URL + "/create", user),
  updateUser: (user) => ApiUtils.post(USER_URL + "/update", user),
  deleteUser: (userId) => ApiUtils.delete(USER_URL + `/delete?userId=${userId}`)
};

export default UserApiUtils;
