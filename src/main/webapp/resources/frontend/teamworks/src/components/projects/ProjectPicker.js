import React, { useEffect, useState } from "react";
import TeamApiUtils from "../../utils/api/TeamApiUtils";
import "./ProjectPicker.css";

const ProjectPicker = ({ pickedProject, setPickedProject }) => {
  const [departmentList, setDepartmentList] = useState([
    {
      dptName: "DPT1",
      projectList: [
        {
          title: "TW1",
          id: "3",
          tagList: [
            { title: "Planning", color: "#FFD703", noOpenedMessages: 15 },
            { title: "Cleaning", color: "#DDFFDD", noOpenedMessages: 12 },
            { title: "MockUp", color: "#AAD7F3", noOpenedMessages: 43 },
          ],
        },
        {
          title: "LOL12",
          id: "5",
          tagList: [
            { title: "Planning", color: "#FFD703", noOpenedMessages: 25 },
            { title: "Cleaning", color: "#DDFFDD", noOpenedMessages: 12 },
            { title: "MockUp", color: "#AAD7F3", noOpenedMessages: 43 },
          ],
        },
        {
          title: "GH14",
          id: "71",
          tagList: [
            { title: "Planning", color: "#FFD703", noOpenedMessages: 5 },
            { title: "Cleaning", color: "#DDFFDD", noOpenedMessages: 162 },
            { title: "MockUp", color: "#AAD7F3", noOpenedMessages: 33 },
          ],
        },
      ],
    },
    {
      dptName: "DPT2",
      id: 12,
      projectList: [
        {
          title: "KO3",
          id: "3",
          tagList: [
            { title: "Planning", color: "#FFD703", noOpenedMessages: 15 },
            { title: "Cleaning", color: "#DDFFDD", noOpenedMessages: 12 },
            { title: "MockUp", color: "#AAD7F3", noOpenedMessages: 43 },
          ],
        },
        {
          title: "JH",
          id: "5",
          tagList: [
            { title: "Planning", color: "#FFD703", noOpenedMessages: 25 },
            { title: "Cleaning", color: "#DDFFDD", noOpenedMessages: 12 },
            { title: "MockUp", color: "#AAD7F3", noOpenedMessages: 43 },
          ],
        },
        {
          title: "SADK4",
          id: "71",
          tagList: [
            { title: "Planning", color: "#FFD703", noOpenedMessages: 5 },
            { title: "Cleaning", color: "#DDFFDD", noOpenedMessages: 162 },
            { title: "MockUp", color: "#AAD7F3", noOpenedMessages: 33 },
          ],
        },
      ],
    },
    {
      dptName: "DPT3",
      id: 143,
      projectList: [
        {
          title: "HW1",
          id: "3",
          tagList: [
            { title: "Planning", color: "#FFD703", noOpenedMessages: 15 },
            { title: "Cleaning", color: "#DDFFDD", noOpenedMessages: 12 },
            { title: "New ORders", color: "#AAD7F3", noOpenedMessages: 43 },
          ],
        },
        {
          title: "LASDL12",
          id: "123",
          tagList: [
            { title: "Planning", color: "#FFD703", noOpenedMessages: 25 },
            { title: "Greetings", color: "#DDFFDD", noOpenedMessages: 12 },
            { title: "New stuff", color: "#AAD7F3", noOpenedMessages: 43 },
          ],
        },
        {
          title: "HSAJ124",
          id: "53",
          tagList: [
            { title: "Planning", color: "#FFD703", noOpenedMessages: 5 },
            { title: "Debugging", color: "#DDFFDD", noOpenedMessages: 162 },
            { title: "MockUp", color: "#AAD7F3", noOpenedMessages: 33 },
          ],
        },
      ],
    },
  ]);
  const [department, setDepartment] = useState(departmentList[0]);
  const [pickedDepartment, setPickedDepartment] = useState(departmentList[0]);
  const [opened, setOpened] = useState(false);

  useEffect(() => {
    TeamApiUtils.getMyDepartments()
      .then((res) => {
        setDepartmentList(res.data);
      })
      .catch((error) => {
        console.log("ERROR: cannot get the departments");
      });
  }, []);

  const openOrClose = () => {
    setOpened(!opened);
  };

  const handlePickDepartment = (dpt) => {
    setPickedDepartment(dpt);
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
        {department.dptName} - {pickedProject.title}
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
                {project.title}
              </button>
            );
          })}
        </div>
      </div>
    </>
  );
};

export default ProjectPicker;
