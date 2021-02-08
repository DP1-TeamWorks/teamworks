import React, { useState } from "react";
import AuthApiUtils from "../../utils/api/AuthApiUtils";
import "../Auth/Login/Login";
import Button from "../buttons/Button";
import LinkButton from "../buttons/LinkButton";
import ProfilePic from "../profile/ProfilePic";
import "./Header.css";
import MainTitle from "./MainTitle";

const handleLogout = async event => {
  AuthApiUtils.logout();
  window.location.reload(false)
}

const Header = ({ nosearchbar, handleSearch}) => {
  let content;
  const [searchWords, setSearchWords] = useState("");


  const changeHandler = (e) => {
    setSearchWords(e.target.value);
  };

  const submitHandler = (e) => {
    e.preventDefault();
    handleSearch(searchWords);
  };


  if (nosearchbar) {
    content = (
      <LinkButton className="Button--green HeaderButton" path="/">
        Go to Inbox
      </LinkButton>
    );
  } else {
    content = (
      <>
        <label className="HeaderSearchbar">
          <form onSubmit={submitHandler}>
            <input
              className="SearchInput"
              onChange={changeHandler}
              placeholder="Search for messages"
            ></input>
          </form>
        </label>
        <LinkButton
          className="Button--green HeaderButton"
          path="/settings/team"
        >
          Manage team
        </LinkButton>
      </>
    );
  }

  

  return (
    <div className="Header">
      <MainTitle />
      {content}
      <ProfilePic small src="/default_pfp.png" />
      <Button className="Button--red HeaderButtonLogout" onClick={handleLogout}>
        <img className="LogoutIcon" src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDQ4OS44ODggNDg5Ljg4OCIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNTEyIDUxMiIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSIgY2xhc3M9IiI+PGc+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+Cgk8Zz4KCQk8cGF0aCBkPSJNMjUuMzgzLDI5MC41Yy03LjItNzcuNSwyNS45LTE0Ny43LDgwLjgtMTkyLjNjMjEuNC0xNy40LDUzLjQtMi41LDUzLjQsMjVsMCwwYzAsMTAuMS00LjgsMTkuNC0xMi42LDI1LjcgICAgYy0zOC45LDMxLjctNjIuMyw4MS43LTU2LjYsMTM2LjljNy40LDcxLjksNjUsMTMwLjEsMTM2LjgsMTM4LjFjOTMuNywxMC41LDE3My4zLTYyLjksMTczLjMtMTU0LjVjMC00OC42LTIyLjUtOTIuMS01Ny42LTEyMC42ICAgIGMtNy44LTYuMy0xMi41LTE1LjYtMTIuNS0yNS42bDAsMGMwLTI3LjIsMzEuNS00Mi42LDUyLjctMjUuNmM1MC4yLDQwLjUsODIuNCwxMDIuNCw4Mi40LDE3MS44YzAsMTI2LjktMTA3LjgsMjI5LjItMjM2LjcsMjE5LjkgICAgQzEyMi4xODMsNDgxLjgsMzUuMjgzLDM5Ni45LDI1LjM4MywyOTAuNXogTTI0NC44ODMsMGMtMTgsMC0zMi41LDE0LjYtMzIuNSwzMi41djE0OS43YzAsMTgsMTQuNiwzMi41LDMyLjUsMzIuNSAgICBzMzIuNS0xNC42LDMyLjUtMzIuNVYzMi41QzI3Ny4zODMsMTQuNiwyNjIuODgzLDAsMjQ0Ljg4MywweiIgZmlsbD0iI2ZmM2MzYyIgZGF0YS1vcmlnaW5hbD0iIzAwMDAwMCIgc3R5bGU9IiIgY2xhc3M9IiI+PC9wYXRoPgoJPC9nPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjwvZz48L3N2Zz4=" />
      </Button>
    </div>
  );
};

export default Header;
