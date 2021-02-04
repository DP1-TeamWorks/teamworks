import SettingGroup from './SettingGroup';
import EditableField from './EditableField';
import Button from '../buttons/Button';
import TeamSettingsApiUtils from '../../utils/api/TeamSettingsApiUtils';
import { useEffect, useState } from 'react';

const TeamSettings = () =>
{

    const [teamName, setTeamName] = useState("");
    useEffect(() => 
    {
        TeamSettingsApiUtils.getTeamName().then(name => setTeamName(name));
    }, []);

    return (
        <div className="SettingGroupsContainer">
            <SettingGroup name="Team name" description="Shown in messages sent by team members.">
                <EditableField id="team-name" value={teamName} apiFunction={TeamSettingsApiUtils.updateTeam} fieldName="name" />
            </SettingGroup>
            <SettingGroup danger name="Delete team" description="Deletes the team, as well as its associated members, departments, projects and tasks. <br>This action cannot be undone.">
                <Button className="Button--red">Delete team</Button>
            </SettingGroup>
        </div>
    );
}

export default TeamSettings;