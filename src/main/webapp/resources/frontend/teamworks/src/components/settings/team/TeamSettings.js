import { useEffect, useState } from 'react';
import TeamSettingsApiUtils from '../../../utils/api/TeamSettingsApiUtils';
import Button from '../../buttons/Button';
import EditableField from '../EditableField';
import SettingGroup from '../SettingGroup';

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
                <EditableField value={teamName} postFunction={TeamSettingsApiUtils.updateTeam} fieldName="name" />
            </SettingGroup>
            <SettingGroup danger name="Delete team" description="Deletes the team, as well as its associated members, departments, projects and tasks. <br>This action cannot be undone.">
                <Button className="Button--red" onClick={onDeleteClicked}>Delete team</Button>
            </SettingGroup>
        </div>
    );
}

export default TeamSettings;