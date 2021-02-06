import SettingGroup from "./SettingGroup";
import EditableField from "./EditableField";
import Button from "../buttons/Button";
import "./SubsettingContainer.css";
import SidePaneElement from "./SidePaneElement";
import UserList from "./department/DepartmentMemberList";
import AddElementForm from "../forms/AddElementForm";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";

const ProjectsContainer = () =>
{
  return (
    <div className="SubsettingContainer">
      <div className="SubsettingSidePane">
        <SidePaneElement selected>Software</SidePaneElement>
        <SidePaneElement>Accounting</SidePaneElement>
        <SidePaneElement>Distribution</SidePaneElement>
      </div>
      <div className="SubsettingSidePane SubsettingSidePane--Second">
        <SidePaneElement highlighted>
          <FontAwesomeIcon icon={faPlus} className="AddIcon" />
          Add new project
          </SidePaneElement>
        <SidePaneElement>TeamWorks</SidePaneElement>
        <SidePaneElement selected>ColorLounge</SidePaneElement>
      </div>
      <div className="SettingGroupsContainer">
        <SettingGroup name="Project name" description="Choose an easily identifiable name for team members.">
          <EditableField id="project-name" value="ColorLounge" />
        </SettingGroup>
        <SettingGroup name="Add user to project" description="Type their name below. They must be a department member.">
          <AddElementForm submitText="Add to ColorLounge" attributeName="Full Name" attributePlaceholder="Harvey Specter" />
        </SettingGroup>
        <SettingGroup name="Members" description="Current project members are shown below. Click on an user to see their history.">
          <UserList />
        </SettingGroup>
        <SettingGroup name="Milestones" description="Click on a milestone below to manage it.">
          <UserList />
        </SettingGroup>
        <SettingGroup name="Tags" description="Click on a tag to manage it.">
          <AddElementForm submitText="Add tag" attributeName="Tag name" attributePlaceholder="Documentation" />
          <UserList />
        </SettingGroup>
        <SettingGroup danger name="Delete project" description="Deletes the project, as well as its associated members, tags and tasks. <br>This action cannot be undone.">
          <Button className="Button--red">Delete project</Button>
        </SettingGroup>
      </div>
    </div>
  );
};

export default ProjectsContainer;
