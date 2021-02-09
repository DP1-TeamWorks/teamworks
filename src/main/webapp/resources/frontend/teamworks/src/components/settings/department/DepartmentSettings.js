import { useEffect, useState } from "react";
import { useContext } from "react/cjs/react.development";
import UserCredentials from "../../../context/UserCredentials";
import DepartmentApiUtils from "../../../utils/api/DepartmentApiUtils";
import AddDepartmentForm from "../../forms/AddDepartmentForm";
import Spinner from "../../spinner/Spinner";
import SettingGroup from "../SettingGroup";
import DepartmentsContainer from "./DepartmentsContainer";

const DepartmentSettings = () =>
{

  const credentials = useContext(UserCredentials);

  function retrieveDepartments()
  {
    let func = credentials.isTeamManager ? DepartmentApiUtils.getDepartments : DepartmentApiUtils.getMyDepartments;
    func()
      .then(dpts => setDepartments(dpts.sort((a,b) => a.name.localeCompare(b.name))))
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
        description=" ">
        <DepartmentsContainer departments={departments} onDepartmentDeleted={retrieveDepartments} />
      </SettingGroup>
    );
  }
  else
  {
    manageDepartments = (
      <SettingGroup
        name="Manage departments"
        description={credentials.isTeamManager ? "There aren't any departments yet." : "You aren't a member of any departments."}>
      </SettingGroup>
    );
  }

  let addNewDepartment;
  if (credentials.isTeamManager)
    addNewDepartment = (
      <SettingGroup
        name="Create new department"
        description="You will be able to add users once created.">
        <AddDepartmentForm onDepartmentAdded={retrieveDepartments} />
      </SettingGroup>
    );

  return (
    <div className="SettingGroupsContainer">
      {addNewDepartment}
      {manageDepartments}
    </div>
  );
};

export default DepartmentSettings;
