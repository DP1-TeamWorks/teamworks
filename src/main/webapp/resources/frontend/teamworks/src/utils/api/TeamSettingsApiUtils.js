import ApiUtils from "./ApiUtils";
const TEAM_SETTINGS_URL = "/team";

const TeamSettingsApiUtils = {
  getTeamName: () => ApiUtils.get(TEAM_SETTINGS_URL),
  updateTeam: (updateObject) => ApiUtils.post(TEAM_SETTINGS_URL + "/update", updateObject),
  deleteTeam: () => ApiUtils.delete(TEAM_SETTINGS_URL + "/delete")
};

export default TeamSettingsApiUtils;
