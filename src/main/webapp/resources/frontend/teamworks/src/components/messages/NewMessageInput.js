import React, { useEffect, useState } from "react";
import Input from "../forms/Input";
import ProjectPicker from "../projects/ProjectPicker";
import Select from 'react-select'
import AsyncSelect from 'react-select/async'
import DepartmentApiUtils from "../../utils/api/DepartmentApiUtils";
import ProjectApiUtils from "../../utils/api/ProjectApiUtils";




const Options = ({ pickedProject, setPickedProject }) => {
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
};

export default function NewMessageInput({name}) {
  return (
    <div className="InputContainer">
        <p className="TitleNewMsg">{name}</p>
        <Select options={Options} className="" isMulti theme={theme => ({
      ...theme,
      borderRadius: 5,
      colors: {
        ...theme.colors,
        primary25: 'neutral60',
        primary: 'neutral0',
        primary50: "neutral50",
      },
    })}>
        </Select>
    </div>
  );
};
