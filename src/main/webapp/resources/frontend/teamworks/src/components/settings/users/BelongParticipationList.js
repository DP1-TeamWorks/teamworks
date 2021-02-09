import { Link } from "react-router-dom";
import "../../../FontStyles.css";
import Spinner from "../../spinner/Spinner";
import "../UserList.css";

const BelongParticipationList = ({ department, project, values }) =>
{

  if (!values)
  {
    return <Spinner />
  }

  let elements;
  if (values && values.length > 0)
  {
    values = values.sort((a, b) => 
    {
      if (a.finalDate === null && b.finalDate !== null)
        return -1;
      if (a.finalDate !== null && b.finalDate === null)
        return 1;
      return new Date(a.initialDate) - new Date(b.initialDate);
    });
    elements = values.map(val =>
    {
      let name;
      if (department)
        name = val.departmentName;
      else if (project)
        name = val.projectName;

      let role;
      if (department)
        role = val.isDepartmentManager ? "Manager" : "Member";
      else if (project)
        role = val.isProjectManager ? "Manager" : "Member";

      if (project)
      {
        return (
          <tr key={val.id}>
            <td>{val.departmentName}</td>
            <td>{name}</td>
            <td>{val.initialDate}</td>
            <td>{val.finalDate ?? "None"}</td>
            <td>{role}</td>
          </tr>
        );
      } else
      {
        return (
          <tr key={val.id}>
            <td>{name}</td>
            <td>{val.initialDate}</td>
            <td>{val.finalDate ?? "None"}</td>
            <td>{role}</td>
          </tr>
        );
      }
      
    });
  }

  let tableHeaderName;
  if (department)
    tableHeaderName = "Department"
  else if (project)
    tableHeaderName = "Project"

  let tableHead;
  if (project)
    tableHead = (
      <tr>
        <th>Department</th>
        <th>{tableHeaderName}</th>
        <th>Start date</th>
        <th>End date</th>
        <th>Role</th>
      </tr>
    );
  else
    tableHead = (
      <tr>
        <th>{tableHeaderName}</th>
        <th>Start date</th>
        <th>End date</th>
        <th>Role</th>
      </tr>
    );

  return (
    <table className="UserList">
      <thead className="UserList__thead">
        {tableHead}
      </thead>
      <tbody>
        {elements}
      </tbody>
    </table>
  );
};

export default BelongParticipationList;
