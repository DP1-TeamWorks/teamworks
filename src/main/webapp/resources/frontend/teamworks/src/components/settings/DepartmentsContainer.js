import SettingGroup from "./SettingGroup";
import EditableField from "./EditableField";
import Button from "../buttons/Button";
import "./SubsettingContainer.css";
import SidePaneElement from "./SidePaneElement";
import UserList from "./UserList";
import AddElementForm from "../forms/AddElementForm";
import { useState } from 'react';
import DepartmentApiUtils from "../../utils/api/DepartmentApiUtils";
import DepartmentSettings from "./DepartmentSettings";
import Spinner from "../spinner/Spinner";

const DepartmentsContainer = ({ departments, onDepartmentDeleted }) =>
{

  function onDepartmentNameUpdated(name)
  {
    if (name != myDepartments[selectedIndex].name)
    {
      // Update department object
      setDepartments(Object.values({
        ...myDepartments,
        [selectedIndex]:
        {
          ...myDepartments[selectedIndex],
          name: name
        }
      }));
    }
  }

  function onDepartmentDeleteClicked()
  {
    if (window.confirm("Are you sure to delete this department and associated data? This action cannot be undone."))
    {
      setIsDeleting(true);
      DepartmentApiUtils.deleteDepartment(myDepartments[selectedIndex].id)
        .then(() =>
        {
          setIsDeleting(false);
          if (onDepartmentDeleted)
            onDepartmentDeleted();
        })
        .catch((err) => 
        {
          setIsDeleting(false);
          console.error(err);
        });
    }

  }

  function updateDepartment(updatedDepartment)
  {
    // Add id for the API call
    updatedDepartment.id = myDepartments[selectedIndex].id;
    return DepartmentApiUtils.updateDepartment(updatedDepartment)
  }

  const [selectedIndex, setSelectedIndex] = useState(0);
  const [myDepartments, setDepartments] = useState(departments);
  const [isDeleting, setIsDeleting] = useState(false);

  if (myDepartments.length !== departments.length)
    setDepartments(departments);

  if (!myDepartments)
  {
    return <p>No departments found.</p>
  }

  if (selectedIndex >= myDepartments.length) // In case we deleted a department
  {
    setSelectedIndex(selectedIndex - 1);
    return; // set correct index and rerender
  }

  let SidepaneElements = [];
  for (let i = 0; i < myDepartments.length; i++)
  {
    const dpt = myDepartments[i];
    SidepaneElements.push(<SidePaneElement key={i} selected={i === selectedIndex} onClick={() => setSelectedIndex(i)}>{dpt.name}</SidePaneElement>);
  }

  let currentDepartment = myDepartments[selectedIndex];

  return (
    <div className="SubsettingContainer">
      <div className="SubsettingSidePane">
        {SidepaneElements}
      </div>
      <div className="SettingGroupsContainer">
        <SettingGroup name="Department name" description="Shown in projects assigned to team members.">
          <EditableField key={selectedIndex} id="department-name" value={currentDepartment.name} fieldName="name" postFunction={updateDepartment} onUpdated={onDepartmentNameUpdated} />
        </SettingGroup>
        <SettingGroup name="Description" description="A brief description for the responsibilities of the department.">
          <EditableField key={selectedIndex} smaller id="department-description" value={currentDepartment.description} fieldName="description" postFunction={updateDepartment} />
        </SettingGroup>
        <SettingGroup name="Add user to department" description="Type their name below.">
          <AddElementForm submitText="Add to Software" attributeName="Full Name" attributePlaceholder="Harvey Specter" />
        </SettingGroup>
        <SettingGroup name="Members" description="Department members are shown below. Click on an user to see their history.">
          <UserList />
        </SettingGroup>
        <SettingGroup danger name="Delete department" description="Deletes the department, as well as its associated members and projects. <br>This action cannot be undone.">
          <Button className="Button--red" onClick={onDepartmentDeleteClicked}>
            {isDeleting ? <Spinner red /> : "Delete department"}
          </Button>
        </SettingGroup>
      </div>
    </div>
  );
};

export default DepartmentsContainer;
