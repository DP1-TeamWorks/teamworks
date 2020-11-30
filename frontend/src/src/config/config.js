export const mode = "dev"; //dev || prod
export const API_URL =
  mode === "dev"
    ? "http://localhost:8080/api"
    : "http://x/api";
