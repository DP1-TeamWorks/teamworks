import SettingGroup from "./SettingGroup";
import EditableField from "./EditableField";
import Button from "../buttons/Button";
import AddUserForm from "../forms/AddUserForm";
import UserList from "./UserList";
import AddElementForm from "../forms/AddElementForm";
import DepartmentsContainer from "./DepartmentsContainer";
import AddDepartmentForm from "../forms/AddDepartmentForm";
import { useEffect, useState } from "react";
import DepartmentApiUtils from "../../utils/api/DepartmentApiUtils";
import Spinner from "../spinner/Spinner";

const DepartmentSettings = () =>
{

  function retrieveDepartments()
  {
    DepartmentApiUtils.getDepartments()
      .then(dpts => setDepartments(dpts))
      .catch((err) => console.error(err));
  }

  const [departments, setDepartments] = useState(null);
  useEffect(() =>
  {
    retrieveDepartments();
  }, []);

  let manageDepartments;
  if (!departments)
  {
    manageDepartments = (
      <SettingGroup
        name="Manage departments"
        description=" ">
          <Spinner />
      </SettingGroup>
    );
  }
  else if (departments.length > 0)
  {
    manageDepartments = (
      <SettingGroup
        name="Manage departments"
        description="Only showing departments you have management permissions on.">
        <DepartmentsContainer departments={departments} onDepartmentDeleted={retrieveDepartments} />
      </SettingGroup>
    );
  }
  else
  {
    manageDepartments = (
      <SettingGroup
        name="Manage departments"
        description="There aren't any deparments yet.">
      </SettingGroup>
    );
  }

  return (
    <div className="SettingGroupsContainer">
      <SettingGroup
        name="Create new department"
        description="You will be able to add users once created.">
        <AddDepartmentForm onDepartmentAdded={retrieveDepartments} />
      </SettingGroup>
      {manageDepartments}
    </div>
  );
};

export default DepartmentSettings;
