import React, { useState } from "react";
import "./ProjectPicker.css";

const ProjectPicker = () => {
  const [department, setDepartment] = useState("DP1");
  const [project, setProject] = useState("TeamWorks");
  return (
    <>
      <h3 className="SidebarSectionTitle">Project</h3>
      <button className="PickerBox">
        {department} - {project}
      </button>
    </>
  );
};

export default ProjectPicker;
