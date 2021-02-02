import React, { useEffect, useState } from "react";
import Input from "../forms/Input";
import ProjectPicker from "../projects/ProjectPicker";
import Select from 'react-select'
import AsyncSelect from 'react-select/async'
import DepartmentApiUtils from "../../utils/api/DepartmentApiUtils";
import ProjectApiUtils from "../../utils/api/ProjectApiUtils";


const options = [
  { value: 'chocolate', label: 'Chocolate' },
  { value: 'strawberry', label: 'Strawberry' },
  { value: 'vanilla', label: 'Vanilla' }
]

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


const customStyles = {
  option: (provided, state) => ({
    ...provided,
    color: state.isSelected ? 'red' : '#222',
  }),
  control: (base, state) => ({
    ...base,
    background: "#262626",
    borderRadius: 4,
    // Overwrittes the different states of border
    borderColor: "rgba(166, 206, 86, 0.8)",
    // Removes weird border around container
    boxShadow: state.isFocused ? null : null,
    marginBottom: 10,
    }),
}


export default function NewMessageMultiSelect({name, placeholder}) {
  return (
    <div className="InputContainer">
        <p className="TitleNewMsg">{name}</p>
        <Select options={options} styles={customStyles} name={name} placeholder={placeholder}
        isMulti></Select>
    </div>
  );
};
