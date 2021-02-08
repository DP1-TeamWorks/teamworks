import { useEffect } from "react";
import Sticky from "react-sticky-el";
import { useState } from "react/cjs/react.development";
import MilestoneApiUtils from "../../../utils/api/MilestoneApiUtils";
import Button from "../../buttons/Button";
import GoBackButton from "../../buttons/GoBackButton";
import EditableField from "../EditableField";
import SettingGroup from "../SettingGroup";
import TodoTableContainer from "./TodoTableContainer";

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
        m.name = m.name ?? milestone.name;
        m.dueFor = m.dueFor ?? milestone.dueFor;
        return MilestoneApiUtils.createMilestone(projectId, m);
    }

    return (
        <>
            <Sticky
            boundaryElement=".Section"
            topOffset={-90}
            stickyStyle={{ transform: 'translateY(90px)', zIndex: 3 }}>
                <div className="ProfileHeader ProfileHeader--Slim">
                    <GoBackButton darker anchored />
                    <div className="ProfileTitleContainer">
                        <h3 className="TinyTitle">{projname}</h3>
                        <h1 className="BigTitle BigTitle--Slim">{milename}</h1>
                    </div>
                </div>
            </Sticky>
            <div className="SettingGroupsContainer">
                <SettingGroup name="Name">
                    <EditableField id="name" value={milename} postFunction={onAttributeUpdated} fieldName="name" />
                </SettingGroup>
                <SettingGroup name="Due by">
                    <EditableField id="dueFor" inputType="date" value={milestone?.dueFor} postFunction={onAttributeUpdated} fieldName="dueFor" />
                </SettingGroup>
                <SettingGroup name="Tasks" description=" ">
                    <TodoTableContainer />
                </SettingGroup>
                <SettingGroup name="Delete milestone" description="Deletes the milestone and associated tags from the team.">
                    <Button className="Button--red">Delete milestone</Button>
                </SettingGroup>
            </div>
        </>
    );
};

export default MilestonePage;