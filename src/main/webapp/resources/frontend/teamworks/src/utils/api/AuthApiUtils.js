import axios from "axios";
import { API_URL } from "../../config/config";
import ApiUtils from "./ApiUtils";
const AUTH_URL = "/auth";

const AuthApiUtils = {
  login: (credentials) =>
    ApiUtils.post(AUTH_URL + "/login", credentials, false),
  islogged: () => ApiUtils.get(AUTH_URL + "/islogged", false),
  signup: (signUpProps) =>
    ApiUtils.post(AUTH_URL + "/signup", signUpProps, false),
  check: () => ApiUtils.get(AUTH_URL, false),
  logout: () => ApiUtils.delete(AUTH_URL + "/logout", false),
};

export default AuthApiUtils;
