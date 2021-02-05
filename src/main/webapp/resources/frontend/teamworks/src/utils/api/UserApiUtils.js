import axios from "axios";
import { API_URL } from "../../config/config";
import ApiUtils from "./ApiUtils";
const USER_URL = "/usersTW";

const UserApiUtils = {
  getMyTeamUsers: () =>
    ApiUtils.get(USER_URL),
  
};

export default UserApiUtils;
