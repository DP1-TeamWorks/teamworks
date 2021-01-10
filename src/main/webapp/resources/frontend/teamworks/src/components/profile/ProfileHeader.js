import "./Profile.css";
import "../../FontStyles.css";
import AuthApiUtils from "../../utils/api/AuthApiUtils";

import ProfilePic from "./ProfilePic";

const ProfileHeader = (props) => {
  return (
    <div
      className="ProfileHeader"
      onClick={() => {
        AuthApiUtils.logout()
          .then((res) => console.log("logged Out"))
          .catch((error) => {
            console.log("ERROR: cannot logout");
          });
        window.location.reload(true);
      }}
    >
      <ProfilePic src={props.src} />
      <div className="ProfileTitleContainer">
        <h3 className="TinyTitle">{props.role}</h3>
        <h1 className="BigTitle">{props.name}</h1>
      </div>
    </div>
  );
};

export default ProfileHeader;
