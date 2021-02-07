import { useEffect, useState } from 'react';
import DepartmentApiUtils from "../../../utils/api/DepartmentApiUtils";
import Button from "../../buttons/Button";
import AddUserToDepartment from "../../forms/AddUserToDepartmentForm";
import Spinner from "../../spinner/Spinner";
import EditableField from "../EditableField";
import SettingGroup from "../SettingGroup";
import SidePaneElement from "../SidePaneElement";
import DepartmentMemberList from "./DepartmentMemberList";
import Sticky from "react-sticky-el";
import "../SubsettingContainer.css";

const DepartmentsContainer = ({ departments, onDepartmentDeleted }) =>
{

  function onDepartmentAttributeUpdated(field, value)
  {
    if (value !== myDepartments[selectedIndex][field])
    {
      // Update department object
      setMyDepartments(Object.values({
        ...myDepartments,
        [selectedIndex]:
        {
          ...myDepartments[selectedIndex],
          [field]: value
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
    return DepartmentApiUtils.updateDepartment(updatedDepartment);
  }

  function onUserAdded()
  {
    fetchDepartmentMembers();
  }

  function fetchDepartmentMembers()
  {
    if (departmentMembers != null)
      setDepartmentMembers(null);
    DepartmentApiUtils.getMembersFromDepartment(myDepartments[selectedIndex].id)
      .then(data => setDepartmentMembers(data))
      .catch(err => console.error(err));
  }

  const [selectedIndex, setSelectedIndex] = useState(0);
  const [myDepartments, setMyDepartments] = useState(departments);
  const [isDeleting, setIsDeleting] = useState(false);
  const [departmentMembers, setDepartmentMembers] = useState(null);

  useEffect(() => fetchDepartmentMembers(), [selectedIndex, myDepartments])

  if (myDepartments.length !== departments.length) // When the department property is changed
    setMyDepartments(departments);

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

  const currentDepartment = myDepartments[selectedIndex];

  return (
    <div className="SubsettingContainer">
      <div className="SubsettingSidePane">
        <Sticky
          boundaryElement=".SubsettingSidePane"
          topOffset={-240}
          stickyStyle={{ transform: 'translateY(240px)' }}>
          {SidepaneElements}
        </Sticky>
      </div>
      <div className="SettingGroupsContainer">
        <SettingGroup
          name="Department name"
          description="Shown in projects assigned to team members.">
          <EditableField
            key={selectedIndex}
            value={currentDepartment.name}
            fieldName="name"
            postFunction={updateDepartment}
            onUpdated={onDepartmentAttributeUpdated} />
        </SettingGroup>
        <SettingGroup
          name="Description"
          description="A brief description for the responsibilities of the department.">
          <EditableField
            key={selectedIndex}
            smaller
            value={currentDepartment.description}
            fieldName="description"
            postFunction={updateDepartment}
            onUpdated={onDepartmentAttributeUpdated} />
        </SettingGroup>
        <SettingGroup
          name="Add user to department"
          description="Start typing their name below.">
          <AddUserToDepartment
            key={currentDepartment.name}
            onUserAdded={onUserAdded}
            departmentId={currentDepartment.id}
            submitText={`Add to ${currentDepartment.name}`} />
        </SettingGroup>
        <SettingGroup
          name="Members"
          description="Click on a user to see their profile and hover to show available actions.">
          <DepartmentMemberList
            key={selectedIndex}
            departmentId={currentDepartment.id}
            loading={departmentMembers == null}
            members={departmentMembers}
            onListUpdated={fetchDepartmentMembers} />
        </SettingGroup>
        <SettingGroup
          danger
          name="Delete department"
          description="Deletes the department, as well as its associated members and projects. <br>This action cannot be undone.">
          <Button
            className="Button--red"
            onClick={onDepartmentDeleteClicked}>
            {isDeleting ? <Spinner red /> : "Delete department"}
          </Button>
        </SettingGroup>
      </div>
    </div>
  );
};

export default DepartmentsContainer;
