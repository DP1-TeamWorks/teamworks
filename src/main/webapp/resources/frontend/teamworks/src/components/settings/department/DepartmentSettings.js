import { useEffect, useState } from "react";
import DepartmentApiUtils from "../../../utils/api/DepartmentApiUtils";
import AddDepartmentForm from "../../forms/AddDepartmentForm";
import Spinner from "../../spinner/Spinner";
import SettingGroup from "../SettingGroup";
import DepartmentsContainer from "./DepartmentsContainer";

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
