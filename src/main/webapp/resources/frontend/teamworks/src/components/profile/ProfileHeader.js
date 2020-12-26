import "./Profile.css";
import "../../FontStyles.css";

import ProfilePic from "./ProfilePic";

const ProfileHeader = (props) =>
{
    return (
        <div className="ProfileHeader">
            <ProfilePic src={props.src} />
            <div className="ProfileTitleContainer">
                <h3 className="TinyTitle">{props.role}</h3>
                <h1 className="BigTitle">{props.name}</h1>
            </div>
        </div>
    )
}

export default ProfileHeader;