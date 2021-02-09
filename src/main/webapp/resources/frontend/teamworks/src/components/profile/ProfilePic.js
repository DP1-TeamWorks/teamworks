import "./Profile.css";

const ProfilePic = ({src, className, small, slim}) =>
{
    let picClass = small ? "ProfilePicHeader" : "ProfilePic";
    return (
        <img className={`${picClass} ${className??""}`} src={src} alt="Profile" ></img>
    )
}

export default ProfilePic;