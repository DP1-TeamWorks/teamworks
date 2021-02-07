import { useEffect } from "react";
import { useState } from "react/cjs/react.development";
import MilestoneApiUtils from "../../../utils/api/MilestoneApiUtils";
import Button from "../../buttons/Button";
import GoBackButton from "../../buttons/GoBackButton";
import EditableField from "../EditableField";
import SettingGroup from "../SettingGroup";

const MilestonePage = ({ match: { params: { projectName, projectId, milestoneName, milestoneId } } }) =>
{
    const projname = projectName.replace(/-/g," ");
    const milename = milestoneName.replace(/-/g," ");

    const [milestone, setMilestone] = useState(null);

    useEffect(() =>
    {
        MilestoneApiUtils.getMilestoneById(projectId, milestoneId)
        .then(m => setMilestone(m))
        .catch(err => console.error(err));
    }, [projectId, milestoneId]);

    function onAttributeUpdated(m)
    {
        m.id = milestoneId;
        return MilestoneApiUtils.createMilestone(projectId, m);
    }

    return (
        <>
            <div className="ProfileHeader ProfileHeader--Slim">
                <GoBackButton darker anchored />
                <div className="ProfileTitleContainer">
                    <h3 className="TinyTitle">{projname}</h3>
                    <h1 className="BigTitle BigTitle--Slim">{milename}</h1>
                </div>
            </div>
            <div className="SettingGroupsContainer">
                <SettingGroup name="Name">
                    <EditableField id="name" value={milename} postFunction={onAttributeUpdated} fieldName="name" />
                </SettingGroup>
                <SettingGroup name="Due by">
                    <EditableField id="dueFor" inputType="date" value={milestone?.dueFor} postFunction={onAttributeUpdated} fieldName="dueFor" />
                </SettingGroup>
                <SettingGroup name="Email">
                    <EditableField id="last-name" value="nicolasdeorycarmona@peasonspecter" editable={false} />
                </SettingGroup>
                <SettingGroup name="User joined">
                    <EditableField id="join-date" value="01/10/2021" editable={false} />
                </SettingGroup>
                <SettingGroup name="Departments" description="A list of the departments the user belongs or has belonged to.">
                    {/* <UserList /> */}
                </SettingGroup>
                <SettingGroup name="Projects" description="A list of the projects the user partipates or has participated in.">
                    {/* <UserList /> */}
                </SettingGroup>
                <SettingGroup danger name="Delete user" description="Deletes the user from the team. <br>This action cannot be undone.">
                    <Button className="Button--red">Delete user</Button>
                </SettingGroup>
            </div>
        </>
    );
};

export default MilestonePage;