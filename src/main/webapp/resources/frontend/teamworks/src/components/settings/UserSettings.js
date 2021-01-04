import SettingGroup from "./SettingGroup";
import EditableField from "./EditableField";
import Button from "../buttons/Button";
import AddUserForm from "../forms/AddUserForm";
import UserList from "./UserList";

const UserSettings = () => {
  return (
    <div className="SettingGroupsContainer">
      <SettingGroup
        name="Add a new user"
        description="Fill out the form fields below.">
        <AddUserForm />
      </SettingGroup>
      <SettingGroup
        name="User list"
        description="Registered users are shown below:">
        <UserList />
      </SettingGroup>
    </div>
  );
};

export default UserSettings;
