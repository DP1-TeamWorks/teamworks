import React, { useRef } from "react";
import { useState } from "react/cjs/react.development";
import ToDoApiUtils from "../../utils/api/ToDoApiUtils";
import GradientButton from "../buttons/GradientButton";
import AddForm from "./AddForm";
import "./AddUserForm.css";
import InputAutocompleteUser from "./InputAutocompleteUser";

const AssignUserForm = ({ onUserAssigned, submitText, milestoneId, projectId, todo }) =>
{
  const selectedUser = useRef(-1);
  const [unassignButtonVisible, setUnassignButtonVisible] = useState(Boolean(todo.assigneeId));

  function onUserSelected(field, userId)
  {
    selectedUser.current = userId;
  }

  function addUserToProject()
  {
    return new Promise((resolve, reject) =>
    {
      ToDoApiUtils.assignTodo(milestoneId, todo.id, selectedUser.current)
        .then(name => 
        {
          resolve();
          console.log(selectedUser.current);
          setUnassignButtonVisible(selectedUser.current !== -1);
          if (onUserAssigned)
            onUserAssigned({todo, id: selectedUser.current, name});
        })
        .catch((err) => reject(err));
    });
  }

  function onUnassigned() {
    selectedUser.current = -1;
    addUserToProject();
  }


  let unassignButton;
  if (unassignButtonVisible)
  {
    unassignButton = (
      <GradientButton onClick={onUnassigned}>Unassign</GradientButton>
    );
  }

  return (
    <>
    <AddForm
      tooManyErrorText="This user has too many tasks assigned. Mark some as done."
      disabled={selectedUser.current === todo.assigneeId??-1}
      hasAutocomplete submitText={submitText}
      postFunction={addUserToProject}
      onAutocompleteSelected={onUserSelected}>
      <p className="InputTitle">Full Name</p>
      <InputAutocompleteUser key={selectedUser} projectId={projectId} name="name" placeholder="Harvey Specter" />
    </AddForm>
    {unassignButton}
    </>
  );
};

export default AssignUserForm;