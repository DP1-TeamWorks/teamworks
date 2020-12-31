import SettingGroup from './SettingGroup';
import EditableField from './EditableField';
import Button from '../buttons/Button';
import AddUserForm from '../Forms/AddUserForm';
import "./UserList.css";

const UserList = () =>
{
    return (
        <table className="UserList">
            <thead className="UserList__thead">
                <tr>
                    <th>Full Name</th>
                    <th>E-mail Address</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Nicolás de Ory</td>
                    <td>nicolasdeory@pearsonspecter</td>
                    <td>Team Manager</td>
                    <td>Delete</td>
                </tr>
            </tbody>
        </table>
    );
}

export default UserList;