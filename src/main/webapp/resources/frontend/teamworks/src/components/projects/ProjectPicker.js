import React, { useEffect, useState } from "react";
import DepartmentApiUtils from "../../utils/api/DepartmentApiUtils";
import ProjectApiUtils from "../../utils/api/ProjectApiUtils";
import "./ProjectPicker.css";

const ProjectPicker = ({ pickedProject, setPickedProject }) => {
  const [departmentList, setDepartmentList] = useState([]);
  const [projectList, setProjectList] = useState([]);

  const [department, setDepartment] = useState("DPT");
  const [pickedDepartment, setPickedDepartment] = useState({});
  const [opened, setOpened] = useState(false);

  useEffect(() => {
    DepartmentApiUtils.getMyDepartments()
      .then((res) => {
        setDepartmentList(res);
      })
      .catch((error) => {
        console.log("ERROR: cannot get the departments");
      });
  }, []);

  useEffect(() => {
    ProjectApiUtils.getMyProjects(department.id)
      .then((res) => {
        setProjectList(res);
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
        setProjectList(res);
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
                className="CollapsedSelector"
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
                className="CollapsedSelector"
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
