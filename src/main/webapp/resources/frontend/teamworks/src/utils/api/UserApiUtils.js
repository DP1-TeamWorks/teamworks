import ApiUtils from "./ApiUtils";
const USER_URL = "/usersTW";
const USERS_URL = "/users";

const UserApiUtils = {
  getMyTeamUsers: () =>
    ApiUtils.get(USERS_URL),
  getAllUsers: () => ApiUtils.get(USERS_URL)
};

export default UserApiUtils;
