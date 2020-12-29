import axios from "axios";
import { API_URL } from "../../config/config";
const AUTH_URL = "/auth";

const AuthApiUtils = {
  login: (credentials) =>
    axios.post(API_URL + AUTH_URL + "/login", credentials),
  islogged: () => axios.get(API_URL + AUTH_URL + "/islogged"),
  signup: (signUpProps) =>
    axios.post(API_URL + AUTH_URL + "/signup", signUpProps),
  check: () => axios.get(API_URL + AUTH_URL),
  logout: () => axios.delete(API_URL + AUTH_URL + "/logout"),
};

export default AuthApiUtils;
