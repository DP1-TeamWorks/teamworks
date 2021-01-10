import "./Profile.css";
import "../../FontStyles.css";
import AuthApiUtils from "../../utils/api/AuthApiUtils";

import ProfilePic from "./ProfilePic";

const ProfileHeader = ({src, role, name, slim, children}) =>
{

    if (slim)
    {
        return (
            <div className="ProfileHeader ProfileHeader--Slim">
                {children}
                <ProfilePic src={src} className="ProfilePic--Slim" />
                <div className="ProfileTitleContainer">
                    <h3 className="TinyTitle">{role}</h3>
                    <h1 className="BigTitle BigTitle--Slim">{name}</h1>
                </div>
            </div>
        );
    }
    else
    {
        return (
            <div className="ProfileHeader">
                <ProfilePic src={src} />
                <div className="ProfileTitleContainer">
                    <h3 className="TinyTitle">{role}</h3>
                    <h1 className="BigTitle">{name}</h1>
                </div>
            </div>
        );
    }
    
}

export default ProfileHeader;
