import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useEffect, useState } from "react";
import Sticky from "react-sticky-el";
import ProjectApiUtils from "../../../utils/api/ProjectApiUtils";
import Button from "../../buttons/Button";
import AddMilestoneForm from "../../forms/AddMilestoneForm";
import AddTagForm from "../../forms/AddTagForm";
import AddUserToProject from "../../forms/AddUserToProjectForm";
import Spinner from "../../spinner/Spinner";
import EditableField from "../EditableField";
import SettingGroup from "../SettingGroup";
import SidePaneElement from "../SidePaneElement";
import "../SubsettingContainer.css";
import MilestoneList from "./MilestoneList";
import ProjectMemberList from "./ProjectMemberList";
import TagList from "./TagList";

const ProjectsContainer = ({ departments, onProjectAdded, onProjectDeleted }) =>
{

  const [departmentIndex, setDepartmentIndex] = useState(0);
  const [projectIndex, setProjectIndex] = useState(0);
  const [myDepartments, setMyDepartments] = useState(departments);
  const [isAddLoading, setIsAddLoading] = useState(false);
  const [isDeleting, setIsDeleting] = useState(false);
  const [projectMembers, setProjectMembers] = useState(null);
  const [milestoneUpdateCounter, setMilestoneUpdateCounter] = useState(0);
  const [tagUpdateCounter, setTagUpdateCounter] = useState(0);

  // TODO useCallback to fix this warning? how would we fix this
  useEffect(() => fetchProjectMembers(), [departmentIndex, projectIndex, myDepartments])

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
    if (isAddLoading)
      return;

    const dpt = myDepartments[departmentIndex]
    const project = {
      name: generateNewProjectName(dpt.projects),
      description: "This is a brief description."
    }
    setIsAddLoading(true);
    ProjectApiUtils.postProject(dpt.id, project)
      .then(() => 
      {
        if (onProjectAdded)
          onProjectAdded();
      })
      .catch(err => console.error(err));
  }

  function onProjectAttributeUpdated(field, value)
  {
    if (value !== myDepartments[departmentIndex].projects[projectIndex][field])
    {
      myDepartments[departmentIndex].projects[projectIndex][field] = value;
      // Trigger update
      setMyDepartments(Object.values({
        ...myDepartments
      }));
    }
  }

  function onMilestoneAdded()
  { 
    setMilestoneUpdateCounter(Math.random()); // HACK force an update
  }

  function onTagAdded()
  { 
    setTagUpdateCounter(Math.random()); // HACK force an update
  }

  function onProjectDeleteClicked()
  {
    if (window.confirm("Are you sure to delete this project and its associated data? This action cannot be undone."))
    {
      const dpt = myDepartments[departmentIndex];
      setIsDeleting(true);
      ProjectApiUtils.deleteProject(dpt.id, dpt.projects[projectIndex].id)
        .then(() =>
        {
          setIsDeleting(false);
          window.scrollTo(0, 0);
          if (onProjectDeleted)
            onProjectDeleted();
        })
        .catch((err) => 
        {
          setIsDeleting(false);
          console.error(err);
        });
    }
  }

  function updateProject(updatedProject)
  {
    // Add id for the API call
    const dpt = myDepartments[departmentIndex];
    updatedProject.id = dpt.projects[projectIndex].id;
    return ProjectApiUtils.updateProject(dpt.id, updatedProject.id, updatedProject);
  }

  function onUserAdded()
  {
    fetchProjectMembers();
  }

  function fetchProjectMembers()
  {
    if (projectMembers != null)
      setProjectMembers(null);
    if (myDepartments[departmentIndex].projects[projectIndex])
    {
      const projectId = myDepartments[departmentIndex].projects[projectIndex].id;
      ProjectApiUtils.getMembersFromProject(projectId)
        .then(data => setProjectMembers(data))
        .catch(err => console.error(err));
    } else 
    {
      setProjectMembers(null);
    }
  }

  if (JSON.stringify(myDepartments) !== JSON.stringify(departments)) // When the department property is changed 
  {
    setIsAddLoading(false);
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
    if (projectIndex >= projects.length)
    {
      setProjectIndex(0);
      return;
    }
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
            onUpdated={onProjectAttributeUpdated} />
        </SettingGroup>
        <SettingGroup
          name="Description"
          description="A brief description of the project.">
          <EditableField
            smaller
            key={`${departmentIndex}-${projectIndex}`}
            fieldName="description"
            value={currentProject.description}
            postFunction={updateProject}
            onUpdated={onProjectAttributeUpdated} />
        </SettingGroup>
        <SettingGroup
          name="Add user to project"
          description="Type their name below. They must be a department member.">
          <AddUserToProject
            key={currentProject.name}
            onUserAdded={onUserAdded}
            projectId={currentProject.id}
            departmentId={departments[departmentIndex].id}
            submitText={`Add to ${currentProject.name}`} />
        </SettingGroup>
        <SettingGroup
          name="Members"
          description="Click on a user to see their profile and hover to show available actions.">
          <ProjectMemberList
            key={`${departmentIndex}-${projectIndex}`}
            projectId={currentProject.id}
            loading={projectMembers == null}
            members={projectMembers}
            onListUpdated={fetchProjectMembers} />
        </SettingGroup>
        <SettingGroup
          name="Milestones"
          description="You can add a new milestone below.">
          <AddMilestoneForm
            key={`mileform${currentProject.id}`}
            projectId={currentProject.id}
            onMilestoneAdded={onMilestoneAdded} />
          <MilestoneList
            key={`list${currentProject.id}`}
            updateCounter={milestoneUpdateCounter}
            projectId={currentProject.id} />
        </SettingGroup>
        <SettingGroup
          name="Tags"
          description="You can add a new tag below.">
          <AddTagForm
            key={`tagform${currentProject.id}`}
            projectId={currentProject.id}
            onTagAdded={onTagAdded} />
          <TagList
            key={`taglist${currentProject.id}`}
            updateCounter={tagUpdateCounter}
            projectId={currentProject.id} />
        </SettingGroup>
        <SettingGroup
          danger
          name="Delete project"
          description="Deletes the project, as well as its associated members, tags and tasks. <br>This action cannot be undone.">
          <Button
            className="Button--red"
            onClick={onProjectDeleteClicked}>
            {isDeleting ? <Spinner red /> : "Delete project"}
          </Button>
        </SettingGroup>
      </>
    );
  }

  let addBtn;
  if (isAddLoading)
  {
    addBtn = <Spinner />
  } else
  {
    addBtn = (
      <>
        <FontAwesomeIcon
          icon={faPlus}
          className="AddIcon" />
        Add new project
      </>);
  }

  return (
    <div className="SubsettingContainer">
      <div className="SubsettingSidePane">
        <Sticky
          boundaryElement=".SubsettingSidePane"
          topOffset={-240}
          stickyStyle={{ transform: 'translateY(240px)' }}>
          {DepartmentElements}
        </Sticky>
      </div>
      <div className="SubsettingSidePane SubsettingSidePane--Second">
        <Sticky
          boundaryElement=".SubsettingSidePane"
          topOffset={-240}
          stickyStyle={{ transform: 'translateY(240px)' }}>
          <SidePaneElement reducedpadding={isAddLoading} onClick={createProject}
            highlighted elementDiv={isAddLoading}>
            {addBtn}
          </SidePaneElement>
          {ProjectElements}
        </Sticky>
      </div>
      <div className="SettingGroupsContainer">
        {Content}
      </div>
    </div>
  );
};

export default ProjectsContainer;
