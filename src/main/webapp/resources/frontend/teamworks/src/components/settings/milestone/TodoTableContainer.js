import "./TodoTable.css";
import TagSelectorStrip from "./TagSelectorStrip";
import TagApiUtils from "../../../utils/api/TagApiUtils";
import TodoTable from "./TodoTable";
import { useEffect, useState } from "react/cjs/react.development";
import ToDoApiUtils from "../../../utils/api/ToDoApiUtils";

const TodoTableContainer = ({milestoneId, projectId}) =>
{

    useEffect(() =>
    {
        TagApiUtils.getTags(projectId)
        .then(t => setTagList(t))
        .catch(err => console.error(err));
    }, [projectId]);

    // const [selectedTodoId, setSelectedTodoId] = useState(null);
    const [tagList, setTagList] = useState([]);
    const [selectedTagId, setSelectedTagId] = useState(-1);

    return (
        <div className="TodoTableContainer">
            <TagSelectorStrip tags={tagList} onSelectedIndexChanged={v => setSelectedTagId(v)} />
            <TodoTable tags={tagList} projectId={projectId} milestoneId={milestoneId} selectedTagId={selectedTagId} />
        </div>
    );
}

export default TodoTableContainer;