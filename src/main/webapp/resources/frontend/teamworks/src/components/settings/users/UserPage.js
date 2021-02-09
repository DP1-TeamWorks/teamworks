import { useEffect, useState } from "react";
import Button from "../../buttons/Button";
import GoBackButton from "../../buttons/GoBackButton";
import ProfileHeader from "../../profile/ProfileHeader";
import EditableField from "../EditableField";
import SettingGroup from "../SettingGroup";
import UserApiUtils from "../../../utils/api/UserApiUtils";
import Spinner from "../../spinner/Spinner";
import BelongParticipationList from "./BelongParticipationList";
import Sticky from "react-sticky-el";
import UserCredentials from "../../../context/UserCredentials";
import { useContext } from "react/cjs/react.development";

const UserPage = ({ match: { params: { userId, userName } } }) =>
{

    const credentials = useContext(UserCredentials);
    const isTeamManager = credentials.isTeamManager;

    const [user, setUser] = useState(null);

    function fetchUser()
    {
        UserApiUtils.getUserById(userId)
            .then(u => setUser(u))
            .catch(err => console.error(err));
    }

    useEffect(() =>
    {
        fetchUser(userId);
    }, [userId])

    function updateUser(user)
    {
        user.id = userId;
        return new Promise((resolve, reject) =>
        {
            UserApiUtils.updateUser(user)
                .then(() =>
                {
                    fetchUser();
                    resolve();
                })
                .catch(err => reject(err))
        });
    }

    function makeTeamManager() 
    {
        alert("TODO");
    }

    function deleteUser()
    {
        if (window.confirm("Are you sure to delete this user from the team? This action cannot be undone."))
        {
            UserApiUtils.deleteUser(user.user.id)
                .then(() => window.location.replace('/settings/users'))
                .catch(err => console.error(err));
        }
    }

    if (user === null)
    {
        return (
            <>
                <ProfileHeader
                    slim
                    src="/default_pfp.png">
                    <GoBackButton darker anchored />
                </ProfileHeader>
                <div className="SettingGroupsContainer">
                    <Spinner />
                </div>
            </>
        );
    }

    let userAttrs = user.user;
    let role = "Member";
    if (userAttrs.role === "team_owner")
        role = "Team owner";
    else if (user.departmentBelongs.some(x => x.isDepartmentManager && x.finalDate === null))
        role = "Department manager";
    else if (user.projectParticipations.some(x => x.isProjectManager && x.finalDate === null))
        role = "Project manager";

    return (
        <>
            <Sticky
                boundaryElement=".Section"
                topOffset={-90}
                stickyStyle={{ transform: 'translateY(90px)', zIndex: 3 }}>
                <ProfileHeader
                    slim
                    src="/default_pfp.png"
                    role={role}
                    name={`${userAttrs.name} ${userAttrs.lastname}`}>
                    <GoBackButton darker anchored />
                </ProfileHeader>
            </Sticky>
            <div className="SettingGroupsContainer">
                <SettingGroup name="First name">
                    <EditableField
                        value={userAttrs.name}
                        editable={isTeamManager}
                        fieldName="name"
                        postFunction={updateUser} />
                </SettingGroup>
                <SettingGroup name="Last name">
                    <EditableField
                        value={userAttrs.lastname}
                        editable={isTeamManager}
                        fieldName="lastname"
                        postFunction={updateUser} />
                </SettingGroup>
                <SettingGroup name="Email">
                    <EditableField value={userAttrs.email} editable={false} />
                </SettingGroup>
                <SettingGroup name="User joined">
                    <EditableField value={userAttrs.joinDate} editable={false} />
                </SettingGroup>
                <SettingGroup name="Departments" description="A list of the departments the user belongs or has belonged to.">
                    <BelongParticipationList department values={user.departmentBelongs} />
                </SettingGroup>
                <SettingGroup name="Projects" description="A list of the projects the user partipates or has participated in.">
                    <BelongParticipationList project values={user.projectParticipations} />
                </SettingGroup>
                {isTeamManager ? (
                    <SettingGroup danger name="Make team manager" description="Transfer team ownership to this user. <br>This action cannot be undone and you will lose your privileges.">
                        <Button className="Button--red" onClick={makeTeamManager}>Make team manager</Button>
                    </SettingGroup>
                ) : ""}
                {isTeamManager ? (
                    <SettingGroup danger name="Delete user" description="Deletes the user from the team. <br>This action cannot be undone.">
                        <Button className="Button--red" onClick={deleteUser}>Delete user</Button>
                    </SettingGroup>
                ) : ""}
            </div>
        </>
    );
};

export default UserPage;