import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useEffect, useState } from "react";
import React from "react";
import Button from "../../buttons/Button";
import AddElementForm from "../../forms/AddElementForm";
import EditableField from "../EditableField";
import SettingGroup from "../SettingGroup";
import SidePaneElement from "../SidePaneElement";
import "../SubsettingContainer.css";
import ProjectApiUtils from "../../../utils/api/ProjectApiUtils";

const ProjectsContainer = ({ departments, onProjectAdded }) =>
{

  const [departmentIndex, setDepartmentIndex] = useState(0);
  const [projectIndex, setProjectIndex] = useState(0);
  const [myDepartments, setMyDepartments] = useState(departments);
  const [isDeleting, setIsDeleting] = useState(false);
  const [departmentMembers, setDepartmentMembers] = useState(null);

  function generateNewProjectName(projects)
  {
    const filtered = projects.filter(p => p.name.indexOf("New Project") >= 0);
    let i = 1;
    let name = "New Project";
    while (true)
    {
      // eslint-disable-next-line no-loop-func
      if (!filtered.some(p => p.name === name))
        break;
      i++;
      name = "New Project " + i;
    }
    return name;
  }

  function createProject()
  {
    const dpt = myDepartments[departmentIndex]
    const project = {
      name: generateNewProjectName(dpt.projects),
      description: "This is a brief description."
    }
    ProjectApiUtils.postProject(dpt.id, project)
    .then(() => 
    {
      if (onProjectAdded)
        onProjectAdded();
    })
    .catch(err => console.error(err));
  }

  function onProjectNameUpdated(name)
  {
    if (name !== myDepartments[departmentIndex].projects[projectIndex].name)
    {
      myDepartments[departmentIndex].projects[projectIndex].name = name;
      // Trigger update
      setMyDepartments(Object.values({
        ...myDepartments
      }));
    }
  }

  function updateProject(updatedProject)
  {
    // Add id for the API call
    const dpt = myDepartments[departmentIndex];
    updatedProject.id = dpt.projects[projectIndex].id;
    return ProjectApiUtils.postProject(dpt.id, updatedProject);
  }

  if (JSON.stringify(myDepartments) !== JSON.stringify(departments)) // When the department property is changed 
  {
    setMyDepartments(departments);
    return;
  }

  if (!myDepartments)
    return <p>No projects found.</p>

  let DepartmentElements = [];

  for (let i = 0; i < myDepartments.length; i++)
  {
    const dpt = myDepartments[i];
    DepartmentElements.push(<SidePaneElement key={i} selected={i === departmentIndex} onClick={() => setDepartmentIndex(i)}>{dpt.name}</SidePaneElement>);
  }

  const projects = myDepartments[departmentIndex].projects
  const ProjectElements = projects.map((x, i) =>
  {
    return <SidePaneElement key={i} selected={i === projectIndex} onClick={() => setProjectIndex(i)}>{x.name}</SidePaneElement>;
  });

  

  let Content;
  if (projects.length === 0)
  {
    Content = <p>There are no projects in this department. Click on "Add new project" to create a new one.</p>
  } else
  {
    const currentProject = projects[projectIndex];
    Content = (
      <>
        <SettingGroup
          name="Project name"
          description="Choose an easily identifiable name for team members.">
          <EditableField
            key={`${departmentIndex}-${projectIndex}`}
            value={currentProject.name} 
            fieldName="name"
            postFunction={updateProject}
            onUpdated={onProjectNameUpdated} />
        </SettingGroup>
        <SettingGroup
          name="Description"
          description="A brief description of the project.">
          <EditableField
            smaller
            key={`${departmentIndex}-${projectIndex}`}
            fieldName="description"
            value={currentProject.description}
            postFunction={updateProject} />
        </SettingGroup>
        <SettingGroup
          name="Add user to project"
          description="Type their name below. They must be a department member.">
          <AddElementForm
            submitText="Add to ColorLounge"
            attributeName="Full Name"
            attributePlaceholder="Harvey Specter" />
        </SettingGroup>
        <SettingGroup
          name="Members"
          description="Current project members are shown below. Click on an user to see their history.">
          {/* <UserList /> */}
        </SettingGroup>
        <SettingGroup
          name="Milestones"
          description="Click on a milestone below to manage it.">
          {/* <UserList /> */}
        </SettingGroup>
        <SettingGroup
          name="Tags"
          description="Click on a tag to manage it.">
          <AddElementForm
            submitText="Add tag"
            attributeName="Tag name"
            attributePlaceholder="Documentation" />
          {/* <UserList /> */}
        </SettingGroup>
        <SettingGroup
          danger
          name="Delete project"
          description="Deletes the project, as well as its associated members, tags and tasks. <br>This action cannot be undone.">
          <Button
            className="Button--red">
            Delete project
        </Button>
        </SettingGroup>
      </>
    );
  }
  return (
    <div className="SubsettingContainer">
      <div className="SubsettingSidePane">
        {DepartmentElements}
      </div>
      <div className="SubsettingSidePane SubsettingSidePane--Second">
        <SidePaneElement onClick={createProject}
          highlighted>
          <FontAwesomeIcon
            icon={faPlus}
            className="AddIcon" />
          Add new project
          </SidePaneElement>
        {ProjectElements}
      </div>
      <div className="SettingGroupsContainer">
        {Content}
      </div>
    </div>
  );
};

export default ProjectsContainer;
