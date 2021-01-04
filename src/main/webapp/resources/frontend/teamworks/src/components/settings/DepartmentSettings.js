import SettingGroup from "./SettingGroup";
import EditableField from "./EditableField";
import Button from "../buttons/Button";
import AddUserForm from "../forms/AddUserForm";
import UserList from "./UserList";
import AddElementForm from "../forms/AddElementForm";
import SubsettingContainer from "./SubsettingContainer";

const DepartmentSettings = () =>
{
  return (
    <div className="SettingGroupsContainer">
      <SettingGroup
        name="Create new department"
        description="You will be able to add users once created.">
        <AddElementForm submitText="Add department" attributeName="Name" attributePlaceholder="Software Dpt."/>
      </SettingGroup>
      <SettingGroup
        name="Manage departments"
        description="Only showing departments you have management permissions on.">
        <SubsettingContainer />
      </SettingGroup>
    </div>
  );
};

export default DepartmentSettings;
