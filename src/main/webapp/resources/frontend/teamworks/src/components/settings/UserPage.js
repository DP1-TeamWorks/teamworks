import GoBackButton from "../buttons/GoBackButton";
import ProfileHeader from "../profile/ProfileHeader";

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
                <h3>You are seeing user {userId}.</h3>
            </div>
        </>
    );
};

export default UserPage;