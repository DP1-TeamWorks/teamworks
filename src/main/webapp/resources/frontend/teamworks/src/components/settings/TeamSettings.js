import SettingGroup from './SettingGroup';
import EditableField from './EditableField';
import Button from '../buttons/Button';
import TeamSettingsApiUtils from '../../utils/api/TeamSettingsApiUtils';
import { useEffect, useState } from 'react';

const TeamSettings = () =>
{

    function onDeleteClicked()
    {
        if (window.confirm("Are you sure to delete this team and associated data? This action cannot be undone and you will be signed out."))
        {
            TeamSettingsApiUtils.deleteTeam().then(() => window.location.replace('/'));
            
        }
    }

    const [teamName, setTeamName] = useState("");
    useEffect(() => 
    {
        TeamSettingsApiUtils.getTeamName().then(name => setTeamName(name));
    }, []);

    return (
        <div className="SettingGroupsContainer">
            <SettingGroup name="Team name" description="Shown in messages sent by team members.">
                <EditableField id="team-name" value={teamName} postFunction={TeamSettingsApiUtils.updateTeam} fieldName="name" />
            </SettingGroup>
            <SettingGroup danger name="Delete team" description="Deletes the team, as well as its associated members, departments, projects and tasks. <br>This action cannot be undone.">
                <Button className="Button--red" onClick={onDeleteClicked}>Delete team</Button>
            </SettingGroup>
        </div>
    );
}

export default TeamSettings;