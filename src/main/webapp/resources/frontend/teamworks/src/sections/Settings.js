import React from 'react';
import Section from "./Section"
import SettingsSidebar from "../components/sidebar/SettingsSidebar"
import ProfileHeader from '../components/profile/ProfileHeader';
import { Redirect, Route, Switch, useLocation } from "react-router-dom";
import TeamSettings from '../components/settings/team/TeamSettings';
import UserSettings from '../components/settings/users/UserSettings';
import DepartmentSettings from "../components/settings/department/DepartmentSettings";
import ProjectSettings from '../components/settings/project/ProjectSettings';
import UserPage from '../components/settings/users/UserPage';
import MilestonePage from '../components/settings/milestone/MilestonePage';
import { useContext } from 'react';
import UserCredentials from '../context/UserCredentials';
import Spinner from '../components/spinner/Spinner';


const PATHS = ["/settings/team", "/settings/departments", "/settings/projects", "/settings/users"];

const Settings = () =>
{
    const credentials = useContext(UserCredentials);
    const location = useLocation();

    if (credentials.isTeamManager === undefined)
        return (
            <div className="Content">
                <SettingsSidebar />
                <Section className="Section--Lighter">
                    <div className="SettingGroupsContainer">
                        <Spinner />
                    </div>
                </Section>
            </div>
        );

    if (credentials.isTeamManager !== undefined && location.pathname.indexOf("/settings") >= 0 && PATHS.indexOf(location.pathname) === -1
        && !(location.pathname.indexOf("/settings/projects") >= 0 || location.pathname.indexOf("/settings/users") >= 0))
        {
            if (credentials.isTeamManager)
            {
                return <Redirect to="/settings/team" />
            } else
            {
                return <Redirect to="/settings/projects" />
            }
        }

    return (
        <div className="Content">
            <SettingsSidebar />
            <Section className="Section--Lighter">
                <ProfileHeader
                    src="/default_pfp.png"
                    role={credentials.getRoleName()}
                    name={credentials.user.name + " " + credentials.user.lastname} />
                <Switch>
                    <Route path="/settings/team">
                        <TeamSettings />
                    </Route>
                    <Route path="/settings/departments">
                        <DepartmentSettings />
                    </Route>
                    <Route path="/settings/users/:userId/:userName" component={UserPage} />
                    <Route path="/settings/projects/:projectName/:projectId/:milestoneName/:milestoneId" component={MilestonePage} />
                    <Route path="/settings/projects">
                        <ProjectSettings />
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