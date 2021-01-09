import React, { useEffect, useState } from "react";
import DepartmentApiUtils from "../../utils/api/DepartmentApiUtils";
import ProjectApiUtils from "../../utils/api/ProjectApiUtils";
import "./ProjectPicker.css";

const ProjectPicker = ({ pickedProject, setPickedProject }) => {
  const [departmentList, setDepartmentList] = useState([
    {
      name: "DPT1",
      id: 15,
    },
    {
      name: "DPT2",
      id: 12,
    },
    {
      name: "DPT3",
      id: 143,
    },
  ]);
  const [projectList, setProjectList] = useState([
    {
      name: "TeamWorks1",
      id: 1,
      tags: [
        { title: "Planning", color: "#FFD703" },
        { title: "Cleaning", color: "#DDFFDD" },
        { title: "MockUp", color: "#AAD7F3" },
      ],
    },
    {
      name: "NEWHorizons1",
      id: 3,
      tags: [
        { title: "Planning", color: "#FFD703" },
        { title: "Cleaning", color: "#DDFFDD" },
        { title: "MockUp", color: "#AAD7F3" },
      ],
    },
    {
      name: "CaptureTheFlag",
      id: 6,
      tags: [
        { title: "Planning", color: "#FFD703" },
        { title: "Cleaning", color: "#DDFFDD" },
        { title: "MockUp", color: "#AAD7F3" },
      ],
    },
  ]);

  const [department, setDepartment] = useState(departmentList[0]);
  const [pickedDepartment, setPickedDepartment] = useState(departmentList[0]);
  const [opened, setOpened] = useState(false);

  useEffect(() => {
    DepartmentApiUtils.getMyDepartments()
      .then((res) => {
        setDepartmentList(res.data);
      })
      .catch((error) => {
        console.log("ERROR: cannot get the departments");
      });
  }, []);

  useEffect(() => {
    ProjectApiUtils.getMyProjects(department.id)
      .then((res) => {
        setProjectList(res.data);
      })
      .catch((error) => {
        console.log("ERROR: cannot get the projects");
      });
  }, []);

  const openOrClose = () => {
    setOpened(!opened);
  };

  const handlePickDepartment = (dpt) => {
    console.log("GETTIN PROJECTS");
    setPickedDepartment(dpt);
    ProjectApiUtils.getMyProjects(dpt.id)
      .then((res) => {
        setProjectList(res.data);
      })
      .catch((error) => {
        console.log("ERROR: cannot get the projects");
      });
  };

  const handlePickProject = (project) => {
    setDepartment(pickedDepartment);
    setPickedProject(project);
    openOrClose();
  };

  return (
    <>
      <h3 className="SidebarSectionTitle">Project</h3>

      <button className="PickerBox" onClick={openOrClose}>
        {department.name} - {pickedProject.name}
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
                {dpt.name}
              </button>
            );
          })}
        </div>
        <div className="ProjectPicker">
          {projectList.map((project) => {
            return (
              <button
                className="CollapsedDepartment"
                onClick={() => handlePickProject(project)}
              >
                {project.name}
              </button>
            );
          })}
        </div>
      </div>
    </>
  );
};

export default ProjectPicker;
