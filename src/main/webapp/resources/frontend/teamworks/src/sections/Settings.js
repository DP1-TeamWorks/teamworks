import React from 'react';
import Section from "./Section"
import SettingsSidebar from "../components/sidebar/SettingsSidebar"
import ProfileHeader from '../components/profile/ProfileHeader';
import { Redirect, Route, Switch, useLocation } from "react-router-dom";
import TeamSettings from '../components/settings/TeamSettings';
import UserSettings from '../components/settings/UserSettings';

const PATHS = ["/settings/team", "/settings/departments", "/settings/projects", "/settings/users"];

const Settings = () =>
{

    const location = useLocation();
    if (location.pathname.indexOf("/settings") >= 0 && PATHS.indexOf(location.pathname) === -1)
        return <Redirect to="/settings/team" />

    return (
        <div className="Content">
            <SettingsSidebar />
            <Section className="Section--Lighter">
                <ProfileHeader
                    src="/default_pfp.png"
                    role="Team Manager"
                    name="Nicolás de Ory" />
                <Switch>
                    <Route path="/settings/team">
                        <TeamSettings />
                    </Route>
                    <Route path="/settings/users">
                        <UserSettings />
                    </Route>
                </Switch>
            </Section>
        </div>
    )
}

export default Settings;