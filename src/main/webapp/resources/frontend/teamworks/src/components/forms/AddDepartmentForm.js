import React from "react";
import Input from "./Input";
import "./AddUserForm.css";
import AddForm from "./AddForm";
import DepartmentApiUtils from "../../utils/api/DepartmentApiUtils";

const AddDepartmentForm = ({onDepartmentAdded}) =>
{

  function addDepartment(department)
  {
    return new Promise((resolve, reject) =>
    {
      DepartmentApiUtils.postDepartment(department)
      .then(() => 
      {
        resolve();
        if (onDepartmentAdded)
          onDepartmentAdded();
      })
      .catch((err) => reject(err));
    });
  }

  return (
    <AddForm submitText="Add department" postFunction={addDepartment} alreadyExistsErrorText="There's already a department with that name.">
      <p className="InputTitle">Name</p>
      <Input name="name" placeholder="Software Dpt." />
      <p className="InputTitle">Description</p>
      <Input name="description" placeholder="A brief description" />
    </AddForm>
  );
};

export default AddDepartmentForm;