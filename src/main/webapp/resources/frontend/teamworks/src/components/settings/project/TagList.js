import { useEffect, useState } from "react";
import "../../../FontStyles.css";
import TagApiUtils from "../../../utils/api/TagApiUtils";
import Circle from "../../projects/tags/Circle";
import Spinner from "../../spinner/Spinner";
import "../UserList.css";

const TagList = ({ projectId, updateCounter }) =>
{

  const [tags, setTags] = useState(null);
  const [loading, setLoading] = useState(true);

  // TODO useCallback for fetchtags
  useEffect(() =>
  {
    fetchTags();
  }, [projectId, updateCounter]);

  function fetchTags()
  {
    TagApiUtils.getTags(projectId)
    .then(data =>
    {
      setTags(data);
      setLoading(false);
    })
    .catch(err => console.error(err));
  }

  function onRemoveClicked(tag)
  {
    if (window.confirm(`Are you sure to remove tag "${tag.title}" from the project?`))
    {
      TagApiUtils.deleteTag(projectId, tag.id)
      .then(() => fetchTags())
      .catch(err => console.error(err));
    }
  }

  if (loading)
  {
    return <Spinner />
  }

  let elements;
  if (tags && tags.length > 0)
  {
    elements = tags.map(tag =>
    {
      return (
        <tr key={tag.id}>
          <td><Circle color={tag.color} />  {tag.title}</td>
          <td>{tag.todosUsingTag}</td>
          <td className="ActionStrip">
            <span onClick={() => onRemoveClicked(tag)} className="BoldTitle BoldTitle--Smallest ActionButton">Remove</span>
          </td>
        </tr>
      );
    });
  } else
  {
    return <p>This project has no tags yet.</p>
  }

  return (
    <table className="UserList UserList--Smaller">
      <thead className="UserList__thead">
        <tr>
          <th>Name</th>
          <th>Tasks</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {elements}
      </tbody>
    </table>
  );
};

export default TagList;
