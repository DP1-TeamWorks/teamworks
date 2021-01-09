import SettingGroup from "./SettingGroup";
import EditableField from "./EditableField";
import Button from "../buttons/Button";
import AddUserForm from "../forms/AddUserForm";
import UserList from "./UserList";
import AddElementForm from "../forms/AddElementForm";
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
