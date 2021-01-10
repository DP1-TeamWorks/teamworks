import GoBackButton from "../buttons/GoBackButton"

const MilestonePage = ({ match: { params: { projectId, milestoneId } } }) =>
{
    return (
        <div className="SettingGroupsContainer">
            <GoBackButton darker />
            <h3>You are managing project {projectId}, milestone {milestoneId}</h3>
        </div>
    );
};

export default MilestonePage;