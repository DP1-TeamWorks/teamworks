import axios from "axios";
import { API_URL } from "../../config/config";
import ApiUtils from "./ApiUtils";
const MILESTONE_URL = "/milestones";

const MilestoneApiUtils = {
  /*MILESTONES*/
  getNextMilestone: (projectId) =>
    ApiUtils.get(MILESTONE_URL + "/next?projectId=" + projectId),
};

export default MilestoneApiUtils;
