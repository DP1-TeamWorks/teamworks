import SettingGroup from "./SettingGroup";
import EditableField from "./EditableField";
import Button from "../buttons/Button";
import AddUserForm from "../forms/AddUserForm";
import UserList from "./UserList";
import AddElementForm from "../forms/AddElementForm";
import SubsettingContainerDepartments from "./SubsettingContainerDepartments";
import AddDepartmentForm from "../forms/AddDepartmentForm";
import { useEffect, useState } from "react";
import DepartmentApiUtils from "../../utils/api/DepartmentApiUtils";

const DepartmentSettings = () =>
{

  const [updateCounter, setUpdateCounter] = useState(0);
  const [departments, setDepartments] = useState(null);
  useEffect(() =>
  {
    DepartmentApiUtils.getDepartments()
    .then(dpts => setDepartments(dpts))
    .catch((err) => console.err(err));
  }, [updateCounter]);

  let manageDeparments;
  if (departments)
  {
    if (departments.length > 0)
    {
      manageDeparments = (
        <SettingGroup
          name="Manage departments"
          description="Only showing departments you have management permissions on.">
          <SubsettingContainerDepartments departments={departments} />
        </SettingGroup>
      );
    }
    else
    {
      manageDeparments = (
        <SettingGroup
          name="Manage departments"
          description="There aren't any deparments yet.">
        </SettingGroup>
      );
    }
  }
  else
  {
    manageDeparments = (
      <SettingGroup
        name="Manage departments"
        description="Loading deparments...">
      </SettingGroup>
    );
  }
    
  return (
    <div className="SettingGroupsContainer">
      <SettingGroup
        name="Create new department"
        description="You will be able to add users once created.">
        <AddDepartmentForm onDepartmentAdded={() => setUpdateCounter(updateCounter+1)} />
      </SettingGroup>
      {manageDeparments}
    </div>
  );
};

export default DepartmentSettings;
