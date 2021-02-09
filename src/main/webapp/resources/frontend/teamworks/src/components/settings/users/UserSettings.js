import SettingGroup from "../SettingGroup";
import AddUserForm from "../../forms/AddUserForm";
import { useEffect, useState } from "react";
import UserList from "./UserList";
import UserApiUtils from "../../../utils/api/UserApiUtils";
import UserCredentials from "../../../context/UserCredentials";
import { useContext } from "react/cjs/react.development";
//import UserList from "./UserList";

const UserSettings = () =>
{
  const credentials = useContext(UserCredentials);
  const [members, setMembers] = useState(null);
  console.log(credentials);

  function fetchMembers()
  {
    UserApiUtils.getUsers()
      .then(users => setMembers(users.sort((x,y) => (x.lastname+x.name).localeCompare(y.lastname+y.name))))
      .catch(err => console.error(err));
  }
  useEffect(() => fetchMembers(), []);


  return (
    <div className="SettingGroupsContainer">
      {credentials.isTeamManager ? (
        <SettingGroup
          name="Add a new user"
          description="Fill out the form fields below.">
          <AddUserForm onUserRegistered={fetchMembers} />
        </SettingGroup>
      ) : ""}
      <SettingGroup
        name="Users"
        description="Registered team users are shown below.">
        <UserList members={members} />
      </SettingGroup>
    </div>
  );
};

export default UserSettings;
