import Button from "../../buttons/Button";
import GoBackButton from "../../buttons/GoBackButton";
import ProfileHeader from "../../profile/ProfileHeader";
import EditableField from "../EditableField";
import SettingGroup from "../SettingGroup";

const UserPage = ({ match: { params: { userId } } }) =>
{
    return (
        <>
            <ProfileHeader
                slim
                src="/default_pfp.png"
                role="Team Manager"
                name={`User ${userId}`}>
                <GoBackButton darker anchored />
            </ProfileHeader>
            <div className="SettingGroupsContainer">
                <SettingGroup name="Name">
                    <EditableField id="name" value="Nicolás" />
                </SettingGroup>
                <SettingGroup name="Last name">
                    <EditableField id="last-name" value="de Ory Carmona" />
                </SettingGroup>
                <SettingGroup name="Email">
                    <EditableField id="last-name" value="nicolasdeorycarmona@peasonspecter" editable={false} />
                </SettingGroup>
                <SettingGroup name="User joined">
                    <EditableField id="join-date" value="01/10/2021" editable={false} />
                </SettingGroup>
                <SettingGroup name="Departments" description="A list of the departments the user belongs or has belonged to.">
                    {/* <UserList /> */}
                </SettingGroup>
                <SettingGroup name="Projects" description="A list of the projects the user partipates or has participated in.">
                    {/* <UserList /> */}
                </SettingGroup>
                <SettingGroup danger name="Delete user" description="Deletes the user from the team. <br>This action cannot be undone.">
                    <Button className="Button--red">Delete user</Button>
                </SettingGroup>
            </div>
        </>
    );
};

export default UserPage;