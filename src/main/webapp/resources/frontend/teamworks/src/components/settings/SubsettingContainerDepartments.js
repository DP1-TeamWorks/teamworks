import SettingGroup from "./SettingGroup";
import EditableField from "./EditableField";
import Button from "../buttons/Button";
import "./SubsettingContainer.css";
import SidePaneElement from "./SidePaneElement";
import UserList from "./UserList";
import AddElementForm from "../forms/AddElementForm";

const SubsettingContainerDepartments = ({departments}) =>
{
  let SidepaneElements;
  if (departments)
  {
    SidepaneElements = departments.map(dp => <SidePaneElement>{dp.name}</SidePaneElement>);
  }
  return (
    <div className="SubsettingContainer">
      <div className="SubsettingSidePane">
        {SidepaneElements}
      </div>
      <div className="SettingGroupsContainer">
        <SettingGroup name="Department name" description="Shown in projects assigned to team members.">
          <EditableField id="department-name" value="Software" />
        </SettingGroup>
        <SettingGroup name="Description" description="A brief description for the responsibilities of the department.">
          <EditableField smaller id="department-description" value="Maintains the current projects on the team." />
        </SettingGroup>
        <SettingGroup name="Add user to department" description="Type their name below.">
          <AddElementForm submitText="Add to Software" attributeName="Full Name" attributePlaceholder="Harvey Specter" />
        </SettingGroup>
        <SettingGroup name="Members" description="Department members are shown below. Click on an user to see their history.">
          <UserList />
        </SettingGroup>
        <SettingGroup danger name="Delete department" description="Deletes the department, as well as its associated members and projects. <br>This action cannot be undone.">
          <Button className="Button--red">Delete department</Button>
        </SettingGroup>
      </div>
    </div>
  );
};

export default SubsettingContainerDepartments;
