import ApiUtils from "./ApiUtils";
const MILESTONE_URL = "/milestones";
const SINGLE_MILESTONE_URL = "/milestone";

const MilestoneApiUtils = {
  /*MILESTONES*/
  getMilestones: (projectId) => ApiUtils.get(MILESTONE_URL + `?projectId=${projectId}`),
  getMilestoneById: (projectId, milestoneId) => ApiUtils.get(SINGLE_MILESTONE_URL + `?projectId=${projectId}&milestoneId=${milestoneId}`),
  getNextMilestone: (projectId) => ApiUtils.get(MILESTONE_URL + `/next?projectId=${projectId}`),
  createMilestone: (projectId, milestone) => ApiUtils.post(MILESTONE_URL + `?projectId=${projectId}`, milestone)
};

export default MilestoneApiUtils;
