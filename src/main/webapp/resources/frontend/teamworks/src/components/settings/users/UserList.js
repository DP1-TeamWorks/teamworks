import { Link } from "react-router-dom";
import "../../../FontStyles.css";
import Spinner from "../../spinner/Spinner";
import "../UserList.css";

const UserList = ({members}) => {

  if (!members)
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
      return (
        <tr key={member.id}>
          <td><Link to={`/settings/users/${member.id}/${username}`}>{member.lastname}, {member.name}</Link></td>
          <td>{member.email}</td>
          <td>{member.role === "team_owner" ? "Team manager" : "Member"}</td>
          <td>{member.joinDate}</td>
        </tr>
      );
    });
  }

  return (
    <table className="UserList">
      <thead className="UserList__thead">
        <tr>
          <th>Name</th>
          <th>E-mail address</th>
          <th>Role</th>
          <th>Member since</th>
        </tr>
      </thead>
      <tbody>
        {elements}
      </tbody>
    </table>
  );
};

export default UserList;
