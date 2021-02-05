import SettingGroup from "./SettingGroup";
import ProjectsContainer from "./ProjectsContainer";

const ProjectSettings = () =>
{
  return (
    <div className="SettingGroupsContainer">
      <SettingGroup
        name="Manage projects"
        description="Only showing projects you have management permissions on.">
        <ProjectsContainer />
      </SettingGroup>
    </div>
  );
};

export default ProjectSettings;
