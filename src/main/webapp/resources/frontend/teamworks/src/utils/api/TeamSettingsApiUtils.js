import axios from "axios";
import { API_URL } from "../../config/config";
import ApiUtils from "./ApiUtils";
const TEAM_SETTINGS_URL = "/team";

const TeamSettingsApiUtils = {
  getTeamName: () => ApiUtils.get(TEAM_SETTINGS_URL),
  updateTeam: (updateObject) => ApiUtils.post(TEAM_SETTINGS_URL, updateObject)
};

export default TeamSettingsApiUtils;
