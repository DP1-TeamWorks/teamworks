import React, { useCallback } from "react";
import MilestoneApiUtils from "../../utils/api/MilestoneApiUtils";
import AddForm from "./AddForm";
import "./AddUserForm.css";
import Input from "./Input";

const AddMilestoneForm = ({projectId, onMilestoneAdded}) =>
{
  function addMilestone(milestone)
  {
    return new Promise((resolve, reject) =>
    {
      MilestoneApiUtils.createMilestone(projectId, milestone)
      .then(() => 
      {
        resolve();
        if (onMilestoneAdded)
          onMilestoneAdded();
      })
      .catch((err) => reject(err));
    });
  }

  return (
    <AddForm submitText="Add milestone" postFunction={addMilestone} validateExcludeFields={["dueFor"]}>
      <p className="InputTitle">Name</p>
      <Input name="name" placeholder="Sprint Feb 20" />
      <p className="InputTitle">Due by</p>
      <Input name="dueFor" type="date" />
    </AddForm>
  );
};

export default AddMilestoneForm;