import axios from "axios";
import { API_URL } from "../../config/config";
import ApiUtils from "./ApiUtils";
const USER_URL = "/user";
const USERS_URL = "/users";

const UserApiUtils = {
  /*USER*/
  getAllUsers: () => ApiUtils.get(USERS_URL)
};

export default UserApiUtils;
