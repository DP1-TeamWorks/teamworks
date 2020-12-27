export const mode = "dev"; //dev || prod
export const API_URL =
  mode === "dev"
    ? "/api"
    : "http://x/api";
