import React from 'react';
import Section from "./Section"
import SettingsSidebar from "../components/sidebar/SettingsSidebar"
import ProfileHeader from '../components/profile/ProfileHeader';
import SettingGroup from '../components/settings/SettingGroup';
import EditableField from '../components/settings/EditableField';
import Button from '../components/buttons/Button';


const Settings = (props) =>
{
    return (
        <div className="Content">
            <SettingsSidebar />
            <Section className="Section--Lighter">
                <ProfileHeader
                    src="https://avatars0.githubusercontent.com/u/1417708?s=460&u=b8dd20ef775f23c845a418513e48617181de734a&v=4"
                    role="Team Manager"
                    name="Nicolás de Ory" />
                <div className="SettingGroupsContainer">
                    <SettingGroup name="Team name" description="Shown in messages sent by team members.">
                        <EditableField id="team-name" value="Pearson Specter" />
                    </SettingGroup>
                    <SettingGroup name="Team identifier" description="<span class='semibold'>nicolasdeory</span>@<mark>pearsonspecter</mark>">
                        <EditableField id="team-id" value="pearsonspecter" />
                    </SettingGroup>
                    <SettingGroup danger name="Delete team" description="Deletes the team, as well as its associated members, departments, projects and tasks. <br>This action cannot be undone.">
                        <Button className="Button--red">Delete team</Button>
                    </SettingGroup>
                </div>
            </Section>
        </div>
    )
}

export default Settings;