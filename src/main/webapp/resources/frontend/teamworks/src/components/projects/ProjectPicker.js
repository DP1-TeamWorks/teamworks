import React, { useState } from "react";
import "./ProjectPicker.css";

const ProjectPicker = ({ pickedProject, setPickedProject }) => {
  const [department, setDepartment] = useState({
    dptName: "DPT1",
    projectList: ["project 1", "project 1.2", "project 1.3"],
  });
  const [project, setProject] = useState(pickedProject.title);
  const [opened, setOpened] = useState(false);
  const [departmentList, setDepartmentList] = useState([
    {
      dptName: "DPT1",
      projectList: ["project 1", "project 1.2", "project 1.3"],
    },
    {
      dptName: "DPT2",
      projectList: ["project 2", "project 2.2", "project 2.3"],
    },
    {
      dptName: "DPT3",
      projectList: ["project 3", "project 3.2", "project 3.3"],
    },
  ]);
  const [pickedDepartment, setPickedDepartment] = useState(departmentList[0]);

  // TODO Api request for dept list

  const openOrClose = () => {
    setOpened(!opened);
  };

  const handlePickDepartment = (dpt) => {
    setPickedDepartment(dpt);
  };

  const handlePickProject = (project) => {
    setDepartment(pickedDepartment);
    // TODO Api request for the specified project
    setProject(project);
    openOrClose();
  };

  return (
    <>
      <h3 className="SidebarSectionTitle">Project</h3>

      <button className="PickerBox" onClick={openOrClose}>
        {department.dptName} - {project}
      </button>
      <div
        className="CollapsedDPTPicker"
        style={{ maxHeight: opened ? "none" : "0px" }}
      >
        <div className="DepartmentPicker">
          {departmentList.map((dpt) => {
            return (
              <button
                className="CollapsedDepartment"
                onClick={() => handlePickDepartment(dpt)}
              >
                {dpt.dptName}
              </button>
            );
          })}
        </div>
        <div className="ProjectPicker">
          {pickedDepartment.projectList.map((project) => {
            return (
              <button
                className="CollapsedDepartment"
                onClick={() => handlePickProject(project)}
              >
                {project}
              </button>
            );
          })}
        </div>
      </div>
    </>
  );
};

export default ProjectPicker;
