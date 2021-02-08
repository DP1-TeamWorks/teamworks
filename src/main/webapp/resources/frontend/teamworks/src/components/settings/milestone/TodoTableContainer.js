import "./TodoTable.css";
import TagSelectorStrip from "./TagSelectorStrip";
import TagApiUtils from "../../../utils/api/TagApiUtils";
import TodoTable from "./TodoTable";
import { useEffect, useState } from "react/cjs/react.development";
import ToDoApiUtils from "../../../utils/api/ToDoApiUtils";

const TodoTableContainer = ({milestoneId, projectId}) =>
{

    /*let tags = [
        {
            id: 1,
            name: "Hola",
            color: "#FF0000"
        },
        {
            id: 2,
            name: "Hola2",
            color: "#00FF00"
        },
        {
            id: 3,
            name: "Hola3",
            color: "#FFFF00"
        },
    ];

    let todos = [
        {
            id: 1,
            title: "hiya",
            tags: tags,
            assigneeId: 1,
            assigneeName: "Nico",
            assigneeLastname: "de Ory",
        },
        {
            id: 2,
            title: "hiya2",
            tags: tags,
            assigneeId: 2,
            assigneeName: "Nico3",
            assigneeLastname: "de Ory4",
        },
        {
            id: 3,
            title: "hiya3",
            tags: tags,
            assigneeId: 3,
            assigneeName: "Nico6",
            assigneeLastname: "de Ory5",
        }
    ]*/

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