import React, { useRef } from "react";
import DepartmentApiUtils from "../../utils/api/DepartmentApiUtils";
import AddForm from "./AddForm";
import "./AddUserForm.css";
import InputAutocompleteUser from "./InputAutocompleteUser";

const AddUserToDepartment = ({ onUserAdded, submitText, departmentId }) =>
{
  function onUserSelected(field, userId)
  {
    selectedUser.current = userId;
  }

  function addUserToDepartment()
  {
    return new Promise((resolve, reject) =>
    {
      DepartmentApiUtils.addUserToDepartment(departmentId, selectedUser.current)
        .then(() => 
        {
          resolve();
          if (onUserAdded)
            onUserAdded();
        })
        .catch((err) => reject(err));
    });
  }

  const selectedUser = useRef(-1);

  return (
    <AddForm hasAutocomplete submitText={submitText} postFunction={addUserToDepartment} alreadyExistsErrorText="That user is already a member in this department." onAutocompleteSelected={onUserSelected}>
      <p className="InputTitle">Full Name</p>
      <InputAutocompleteUser key={selectedUser} name="name" placeholder="Harvey Specter"/>
    </AddForm>
  );
};

export default AddUserToDepartment;