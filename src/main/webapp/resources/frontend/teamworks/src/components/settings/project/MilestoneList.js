import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "../../../FontStyles.css";
import MilestoneApiUtils from "../../../utils/api/MilestoneApiUtils";
import Spinner from "../../spinner/Spinner";
import "../UserList.css";

const MilestoneList = ({ projectName, projectId, updateCounter }) =>
{

  const [milestones, setMilestones] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() =>
  {
    MilestoneApiUtils.getMilestones(projectId)
      .then(data =>
      {
        setMilestones(data);
        setLoading(false);
      })
      .catch(err => console.error(err));
  }, [projectId, updateCounter]);

  if (loading)
  {
    return <Spinner />
  }

  let elements;
  if (milestones && milestones.length > 0)
  {
    const today = new Date();
    elements = milestones.map(milestone =>
    {
      const milename = milestone.name.replace(/ /g,"-");
      const projname = projectName.replace(/ /g, "-");
      const pastText = new Date(milestone.dueFor) < today ? "Expired:" : "";
      return (
        <tr key={milestone.id}>
          <td><i>{pastText}</i> <Link to={`/settings/projects/${projname}/${projectId}/${milename}/${milestone.id}`}>{milestone.name}</Link></td>
          <td>{milestone.toDos.length}</td>
          <td>{milestone.dueFor}</td>
        </tr>
      );
    });
  } else
  {
    return <p>This project has no milestones yet.</p>
  }

  return (
    <table className="UserList UserList--Smaller">
      <thead className="UserList__thead">
        <tr>
          <th>Name</th>
          <th>Tasks</th>
          <th>Due by</th>
        </tr>
      </thead>
      <tbody>
        {elements}
      </tbody>
    </table>
  );
};

export default MilestoneList;
