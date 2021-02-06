import { Link } from "react-router-dom";
import "../../../FontStyles.css";
import ProjectApiUtils from "../../../utils/api/ProjectApiUtils";
import Spinner from "../../spinner/Spinner";
import "../UserList.css";

const ProjectMemberList = ({projectId, members, loading, onListUpdated}) => {

  function onPromoteClicked(member)
  {
    if (member.isProjectManager)
    {
      // TODO: Change confirm message if it's project manager and not team manager who is asking
      if (window.confirm(`Are you sure to demote ${member.name} ${member.lastName} and make them a member?`))
      {
        ProjectApiUtils.addUserToProject(projectId, member.userId, false)
          .then(() => onListUpdated())
          .catch(err => console.error(err));
      }
    } else
    {
      // TODO: Change confirm message if it's project manager and not team manager who is asking
      if (window.confirm(`Are you sure to promote ${member.name} ${member.lastName} to project manager?`))
      {
        ProjectApiUtils.addUserToProject(projectId, member.userId, true)
          .then(() => onListUpdated())
          .catch(err => console.error(err));
      }
    }

  }

  function onRemoveClicked(member)
  {
    if (window.confirm(`Are you sure to remove ${member.name} ${member.lastName} from the project?`))
    {
      ProjectApiUtils.removeUserFromProject(projectId, member.userId)
      .then(() => onListUpdated())
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
      return (
        <tr key={member.id}>
          <td><Link to={`/settings/users/${member.userId}`}>{member.lastName}, {member.name}</Link></td>
          <td>{member.email}</td>
          <td>{member.isProjectManager ? "Proj. Manager" : "Member"}</td>
          <td>{member.initialDate}</td>
          <td className="ActionStrip">
            <span onClick={() => onPromoteClicked(member)} className="BoldTitle BoldTitle--Smallest ActionButton">{member.isProjectManager ? "Demote" : "Promote"}</span>
            <span onClick={() => onRemoveClicked(member)} className="BoldTitle BoldTitle--Smallest ActionButton">Remove</span>
          </td>
        </tr>
      );
    });
  } else
  {
    return <p>This project has no members yet.</p>
  }

  return (
    <table className="UserList">
      <thead className="UserList__thead">
        <tr>
          <th>Name</th>
          <th>E-mail address</th>
          <th>Role</th>
          <th>Member since</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {elements}
      </tbody>
    </table>
  );
};

export default ProjectMemberList;
