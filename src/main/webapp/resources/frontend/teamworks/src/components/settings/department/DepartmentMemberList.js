import { Link } from "react-router-dom";
import "../../../FontStyles.css";
import DepartmentApiUtils from "../../../utils/api/DepartmentApiUtils";
import Spinner from "../../spinner/Spinner";
import "../UserList.css";

const DepartmentMemberList = ({departmentId, members, loading, onListUpdated}) => {

  function onPromoteClicked(member)
  {
    if (member.isDepartmentManager)
    {
      // TODO: Change confirm message if it's department manager and not team manager who is asking
      if (window.confirm(`Are you sure to demote ${member.name} ${member.lastName} and make them a member?`))
      {
        DepartmentApiUtils.addUserToDepartment(departmentId, member.userId, false)
          .then(() => onListUpdated())
          .catch(err => console.error(err));
      }
    } else
    {
      // TODO: Change confirm message if it's department manager and not team manager who is asking
      if (window.confirm(`Are you sure to promote ${member.name} ${member.lastName} to department manager?`))
      {
        DepartmentApiUtils.addUserToDepartment(departmentId, member.userId, true)
          .then(() => onListUpdated())
          .catch(err => console.error(err));
      }
    }

  }

  function onRemoveClicked(member)
  {
    if (window.confirm(`Are you sure to remove ${member.name} ${member.lastName} from the team?`))
    {
      DepartmentApiUtils.removeUserFromDepartment(departmentId, member.userId)
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
          <td>{member.isDepartmentManager ? "Dpt. Manager" : "Member"}</td>
          <td>{member.initialDate}</td>
          <td className="ActionStrip">
            <span onClick={() => onPromoteClicked(member)} className="BoldTitle BoldTitle--Smallest ActionButton">{member.isDepartmentManager ? "Demote" : "Promote"}</span>
            <span onClick={() => onRemoveClicked(member)} className="BoldTitle BoldTitle--Smallest ActionButton">Remove</span>
          </td>
        </tr>
      );
    });
  } else
  {
    return <p>This department has no members yet.</p>
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

export default DepartmentMemberList;
