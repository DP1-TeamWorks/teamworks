import "./Profile.css";

const ProfilePic = (props) =>
{
    return (
        <img className="ProfilePic" src={props.src}></img>
    )
}

export default ProfilePic;