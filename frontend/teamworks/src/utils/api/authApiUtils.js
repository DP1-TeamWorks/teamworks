import axios from 'axios';
import { API_URL } from '../../config/config';
const AUTH_URL = '/auth';

export const login = (credentials) => axios.post(API_URL + AUTH_URL + '/', credentials);
export const check = () => axios.get(API_URL + AUTH_URL)
export const logout = () => axios.delete(API_URL + AUTH_URL)