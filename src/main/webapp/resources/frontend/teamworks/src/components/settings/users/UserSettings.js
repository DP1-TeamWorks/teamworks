import SettingGroup from "../SettingGroup";
import AddUserForm from "../../forms/AddUserForm";
import { useEffect, useState } from "react";
import UserList from "./UserList";
import UserApiUtils from "../../../utils/api/UserApiUtils";
//import UserList from "./UserList";

const UserSettings = () =>
{

  const [members, setMembers] = useState(null);


  function fetchMembers()
  {
    UserApiUtils.getUsers()
      .then(users => setMembers(users.sort((x,y) => (x.lastname+x.name).localeCompare(y.lastname+y.name))))
      .catch(err => console.error(err));
  }
  useEffect(() => fetchMembers(), []);


  return (
    <div className="SettingGroupsContainer">
      <SettingGroup
        name="Add a new user"
        description="Fill out the form fields below.">
        <AddUserForm onUserRegistered={fetchMembers} />
      </SettingGroup>
      <SettingGroup
        name="Users"
        description="Registered team users are shown below.">
        <UserList members={members} />
      </SettingGroup>
    </div>
  );
};

export default UserSettings;
