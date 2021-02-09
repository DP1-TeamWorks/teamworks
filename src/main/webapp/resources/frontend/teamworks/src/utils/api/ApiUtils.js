import axios from "axios";

const API_URL = "/api";

const ApiUtils = {
  get: (url, reloadOnUnauthorized = true) =>
  {
    return new Promise((resolve, reject) =>
    {
      axios.get(API_URL + url)
      .then((res) => resolve(res.data))
      .catch((err) =>
      {
        if (err.response?.status === 403 && reloadOnUnauthorized)
        {
          window.location.replace('/'); // unauthorized. refresh? 
        }
        reject(err);
      })
    })
  },
  post: (url, updateObject, reloadOnUnauthorized = true) =>
  {
    return new Promise((resolve, reject) =>
    {
      axios.post(API_URL + url, updateObject)
      .then((res) => resolve(res.data))
      .catch((err) =>
      {
        if (err.response?.status === 403 && reloadOnUnauthorized)
        {
          window.location.replace('/'); // unauthorized. redirect to login 
        }
        reject(err);
      })
    });
  },
  patch: (url, updateObject, reloadOnUnauthorized = true) =>
  {
    return new Promise((resolve, reject) =>
    {
      axios.patch(API_URL + url, updateObject)
      .then((res) => resolve(res.data))
      .catch((err) =>
      {
        if (err.response?.status === 403 && reloadOnUnauthorized)
        {
          window.location.replace('/'); // unauthorized. redirect to login 
        }
        reject(err);
      })
    });
  },
  delete: (url, reloadOnUnauthorized = true) =>
  {
    return new Promise((resolve, reject) =>
    {
      axios.delete(API_URL + url)
      .then((res) => resolve(res.data))
      .catch((err) =>
      {
        if (err.response?.status === 403 && reloadOnUnauthorized)
        {
          window.location.replace('/'); // unauthorized. redirect to login 
        }
        reject(err);
      })
    });
  }
};

export default ApiUtils;
