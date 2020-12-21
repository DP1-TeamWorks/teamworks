import axios from "axios";
import { API_URL } from "../../config/config";
const AUTH_URL = "/auth";

export default {
  login: (credentials) =>
    axios.post(API_URL + AUTH_URL + "/login", credentials),
  signup: (signUpProps) =>
    axios.post(API_URL + AUTH_URL + "/signup", signUpProps),
  check: () => axios.get(API_URL + AUTH_URL),
  logout: () => axios.delete(API_URL + AUTH_URL),
};
