import ApiUtils from "./ApiUtils";
const USER_URL = "/user";
const USERS_URL = "/users";

const UserApiUtils = {
  getUsers: () => ApiUtils.get(USERS_URL),
  registerUser: (user) => ApiUtils.post(USER_URL, user)
};

export default UserApiUtils;
