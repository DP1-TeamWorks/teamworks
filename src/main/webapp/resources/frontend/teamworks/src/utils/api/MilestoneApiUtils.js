import axios from "axios";
import { API_URL } from "../../config/config";
import ApiUtils from "./ApiUtils";
const MILESTONE_URL = "/milestones";

const MilestoneApiUtils = {
  /*MILESTONES*/
  getMilestones: (projectId) => ApiUtils.get(MILESTONE_URL + `?projectId=${projectId}`),
  getNextMilestone: (projectId) => ApiUtils.get(MILESTONE_URL + `/next?projectId=${projectId}`),
  createMilestone: (projectId, milestone) => ApiUtils.post(MILESTONE_URL + `?projectId=${projectId}`, milestone)
};

export default MilestoneApiUtils;
