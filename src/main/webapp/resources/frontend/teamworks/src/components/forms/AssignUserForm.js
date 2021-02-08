import React, { useRef } from "react";
import ProjectApiUtils from "../../utils/api/ProjectApiUtils";
import AddForm from "./AddForm";
import "./AddUserForm.css";
import InputAutocompleteUser from "./InputAutocompleteUser";

const AssignUserForm = ({ onUserAdded, submitText, projectId }) =>
{
  function onUserSelected(field, userId)
  {
    selectedUser.current = userId;
  }

  function addUserToProject()
  {
    return new Promise((resolve, reject) =>
    {
     /* ProjectApiUtils.addUserToProject(projectId, selectedUser.current)
        .then(() => 
        {
          resolve();
          if (onUserAdded)
            onUserAdded();
        })
        .catch((err) => reject(err));*/
    });
  }

  const selectedUser = useRef(-1);

  return (
    <AddForm hasAutocomplete submitText={submitText} postFunction={addUserToProject} alreadyExistsErrorText="That user is already a project member." onAutocompleteSelected={onUserSelected}>
      <p className="InputTitle">Full Name</p>
      <InputAutocompleteUser key={selectedUser} projectId={projectId} name="name" placeholder="Harvey Specter"/>
    </AddForm>
  );
};

export default AssignUserForm;