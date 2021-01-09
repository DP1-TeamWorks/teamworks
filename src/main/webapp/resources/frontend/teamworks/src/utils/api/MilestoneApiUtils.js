import axios from "axios";
import { API_URL } from "../../config/config";
const MILESTONE_URL = "/milestones";

const MilestoneApiUtils = {
  /*MILESTONES*/
  getNextMilestone: (projectId) =>
    axios.get(API_URL + MILESTONE_URL + "/next"),
};

export default MilestoneApiUtils;
