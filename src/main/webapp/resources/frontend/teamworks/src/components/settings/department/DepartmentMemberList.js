import { Link } from "react-router-dom";
import { useContext } from "react/cjs/react.development";
import UserCredentials from "../../../context/UserCredentials";
import "../../../FontStyles.css";
import DepartmentApiUtils from "../../../utils/api/DepartmentApiUtils";
import Spinner from "../../spinner/Spinner";
import "../UserList.css";

const DepartmentMemberList = ({ departmentId, members, loading, onListUpdated }) =>
{

  const credentials = useContext(UserCredentials);
  const isDepartmentManager = credentials.isDepartmentManager(departmentId);

  function onPromoteClicked(member)
  {
    let isCredentialInDanger = !credentials.isTeamManager && isDepartmentManager;
    if (member.isDepartmentManager)
    {
      let confirmMessage = `Are you sure to demote ${member.name} ${member.lastname} and make them a member?`;
      if (isCredentialInDanger)
      {
        confirmMessage = `NOTE: You are demoting yourself and you will NO LONGER HAVE MANAGEMENT PRIVILEGES. This action cannot be undone. Would you like to continue?`;
      }
      if (window.confirm(confirmMessage))
      {
        DepartmentApiUtils.addUserToDepartment(departmentId, member.userId, false)
          .then(() => {
            if (isCredentialInDanger) 
              window.location.replace('/settings/departments');
            else
              onListUpdated();
          })
          .catch(err => console.error(err));
      }
    } else
    {
      let confirmMessage = `Are you sure to demote ${member.name} ${member.lastname} and make them a member?`;
      if (isCredentialInDanger)
      {
        confirmMessage = `NOTE: You are promoting ${member.name} ${member.lastname} to department manager. You will NO LONGER HAVE MANAGEMENT PRIVILEGES. This action cannot be undone. Would you like to continue?`;
      }
      if (window.confirm(confirmMessage))
      {
        DepartmentApiUtils.addUserToDepartment(departmentId, member.userId, true)
          .then(() => 
          {
            if (isCredentialInDanger) 
              window.location.replace('/settings/departments');
            else
              onListUpdated();
          })
          .catch(err => console.error(err));
      }
    }

  }

  function onRemoveClicked(member)
  {
    let isCredentialInDanger = member.userId === credentials.user.id && !credentials.isTeamManager && isDepartmentManager;
    let confirmMessage = `Are you sure to remove ${member.name} ${member.lastname} from the department?`;
    if (isCredentialInDanger)
    {
      confirmMessage = "Are you sure to REMOVE YOURSELF from this department and lose access? This action cannot be undone.";
    }
    if (window.confirm(confirmMessage))
    {
      DepartmentApiUtils.removeUserFromDepartment(departmentId, member.userId)
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
      if (isDepartmentManager)
      {
        return (
          <tr key={member.id}>
            <td><Link to={`/settings/users/${member.userId}/${username}`}>{member.lastname}, {member.name}</Link></td>
            <td>{member.email}</td>
            <td>{member.isDepartmentManager ? "Dpt. Manager" : "Member"}</td>
            <td>{member.initialDate}</td>
            <td className="ActionStrip">
              <span onClick={() => onPromoteClicked(member)} className="BoldTitle BoldTitle--Smallest ActionButton">{member.isDepartmentManager ? "Demote" : "Promote"}</span>
              <span onClick={() => onRemoveClicked(member)} className="BoldTitle BoldTitle--Smallest ActionButton">Remove</span>
            </td>
          </tr>
        );
      } else 
      {
        return (
          <tr key={member.id}>
            <td><Link to={`/settings/users/${member.userId}/${username}`}>{member.lastname}, {member.name}</Link></td>
            <td>{member.email}</td>
            <td>{member.isDepartmentManager ? "Dpt. Manager" : "Member"}</td>
            <td>{member.initialDate}</td>
          </tr>
        );
      }
    });
  } else
  {
    return <p>This department has no members yet.</p>
  }

  let tableHeader;
  if (isDepartmentManager)
  {
    tableHeader = (
      <tr>
        <th>Name</th>
        <th>E-mail address</th>
        <th>Role</th>
        <th>Member since</th>
        <th>Actions</th>
      </tr>
    );
  } else
  {
    tableHeader = (
      <tr>
        <th>Name</th>
        <th>E-mail address</th>
        <th>Role</th>
        <th>Member since</th>
      </tr>
    );
  }

  return (
    <table className="UserList">
      <thead className="UserList__thead">
        {tableHeader}
      </thead>
      <tbody>
        {elements}
      </tbody>
    </table>
  );
};

export default DepartmentMemberList;
