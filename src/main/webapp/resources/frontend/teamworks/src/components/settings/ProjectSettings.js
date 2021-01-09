import SettingGroup from "./SettingGroup";
import SubsettingContainerProjects from "./SubsettingContainerProjects";

const ProjectSettings = () =>
{
  return (
    <div className="SettingGroupsContainer">
      <SettingGroup
        name="Manage projects"
        description="Only showing projects you have management permissions on.">
        <SubsettingContainerProjects />
      </SettingGroup>
    </div>
  );
};

export default ProjectSettings;
