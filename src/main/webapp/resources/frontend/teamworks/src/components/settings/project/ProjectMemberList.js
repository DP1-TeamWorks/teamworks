import { faWindows } from "@fortawesome/free-brands-svg-icons";
import { Link } from "react-router-dom";
import { useContext } from "react";
import UserCredentials from "../../../context/UserCredentials";
import "../../../FontStyles.css";
import ProjectApiUtils from "../../../utils/api/ProjectApiUtils";
import Spinner from "../../spinner/Spinner";
import "../UserList.css";

const ProjectMemberList = ({departmentId, projectId, members, loading, onListUpdated}) => {

  const credentials = useContext(UserCredentials);
  const isProjectManager = credentials.isProjectManager(projectId);

  function onPromoteClicked(member)
  {
    let isCredentialInDanger = !credentials.isTeamManager && !credentials.isDepartmentManager(departmentId) && isProjectManager;
    if (member.isProjectManager)
    {
      let confirmMessage = `Are you sure to demote ${member.name} ${member.lastname} and make them a member?`;
      if (isCredentialInDanger)
      {
        confirmMessage = `NOTE: You are demoting yourself and you will NO LONGER HAVE MANAGEMENT PRIVILEGES. This action cannot be undone. Would you like to continue?`;
      }
      if (window.confirm(confirmMessage))
      {
        ProjectApiUtils.addUserToProject(projectId, member.userId, false)
          .then(() => 
          {
            if (isCredentialInDanger)
              window.location.reload();
            else
              onListUpdated();
          })
          .catch(err => console.error(err));
      }
    } else
    {
      let confirmMessage = `Are you sure to promote ${member.name} ${member.lastname} to project manager?`;
      if (isCredentialInDanger)
      {
        confirmMessage =  `NOTE: You are promoting ${member.name} ${member.lastname} to project manager. You will NO LONGER HAVE MANAGEMENT PRIVILEGES. This action cannot be undone. Would you like to continue?`;
      }
      if (window.confirm(confirmMessage))
      {
        ProjectApiUtils.addUserToProject(projectId, member.userId, true)
          .then(() => 
          {
            if (isCredentialInDanger)
              window.location.reload();
            else
              onListUpdated();
          })
          .catch(err => console.error(err));
      }
    }

  }

  function onRemoveClicked(member)
  {
    let isCredentialInDanger = member.userId === credentials.user.id && !credentials.isTeamManager && !credentials.isDepartmentManager(departmentId) && isProjectManager;
    let confirmMessage = `Are you sure to remove ${member.name} ${member.lastname} from the project?`;
    if (isCredentialInDanger)
    {
      confirmMessage = "Are you sure to REMOVE YOURSELF from this project and lose access? This action cannot be undone.";
    }
    if (window.confirm(confirmMessage))
    {
      ProjectApiUtils.removeUserFromProject(projectId, member.userId)
      .then(() => 
      {
        if (isCredentialInDanger)
          window.location.reload();
        else
          onListUpdated();
      })
      .catch(err => console.error(err));
    }
  }

  if (loading)
  {
    return <Spinner />
  }

  let elements;
  if (members && members.length > 0)
  {
    elements = members.map(member =>
    {
      const completename = member.name + " " + member.lastname;
      const username = completename.toLowerCase().replace(/ /g, "");
      let actionStrip;
      if (isProjectManager)
      {
        actionStrip = (
          <td className="ActionStrip">
            <span onClick={() => onPromoteClicked(member)} className="BoldTitle BoldTitle--Smallest ActionButton">{member.isProjectManager ? "Demote" : "Promote"}</span>
            <span onClick={() => onRemoveClicked(member)} className="BoldTitle BoldTitle--Smallest ActionButton">Remove</span>
          </td>
        );
      }
      return (
        <tr key={member.id}>
          <td><Link to={`/settings/users/${member.userId}/${username}`}>{member.lastname}, {member.name}</Link></td>
          <td>{member.email}</td>
          <td>{member.isProjectManager ? "Proj. Manager" : "Member"}</td>
          <td>{member.initialDate}</td>
          {actionStrip}
        </tr>
      );
    });
  } else
  {
    return <p>This project has no members yet.</p>
  }

  let actionRow;
  if (isProjectManager)
    actionRow = <th>Actions</th>
  return (
    <table className="UserList">
      <thead className="UserList__thead">
        <tr>
          <th>Name</th>
          <th>E-mail address</th>
          <th>Role</th>
          <th>Member since</th>
          {actionRow}
        </tr>
      </thead>
      <tbody>
        {elements}
      </tbody>
    </table>
  );
};

export default ProjectMemberList;
