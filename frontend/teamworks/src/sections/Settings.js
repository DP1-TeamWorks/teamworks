import React from 'react';
import Section from "./Section"
import SettingsSidebar from "../components/sidebar/SettingsSidebar"
import ProfileHeader from '../components/profile/ProfileHeader';


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
            </Section>
        </div>
    )
}

export default Settings;