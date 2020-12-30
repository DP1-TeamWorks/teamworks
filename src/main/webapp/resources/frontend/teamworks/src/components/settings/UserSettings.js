import SettingGroup from './SettingGroup';
import EditableField from './EditableField';
import Button from '../buttons/Button';
import AddUserForm from '../Forms/AddUserForm';
import UserList from './UserList';

const UserSettings = () =>
{
    return (
        <div className="SettingGroupsContainer">
            <SettingGroup name="Add a new user" description="You will need to specify their full name and login details.">
                <AddUserForm />
            </SettingGroup>
            <SettingGroup name="User list" description="Registered users are shown below:">
                <UserList />
            </SettingGroup>
        </div>
    );
}

export default UserSettings;