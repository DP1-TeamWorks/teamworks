import "./TodoTable.css";
import TagSelectorStrip from "./TagSelectorStrip";
import TodoTable from "./TodoTable";
import { useState } from "react/cjs/react.development";

const TodoTableContainer = () =>
{

    let tags = [
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
    ]

    // const [selectedTodoId, setSelectedTodoId] = useState(null);
    const [tagList, setTagList] = useState(tags);
    const [todoList, setTodoList] = useState(todos);

    return (
        <div className="TodoTableContainer">
            <TagSelectorStrip tags={tagList} />
            <TodoTable todos={todoList} tags={tagList} />
        </div>
    );
}

export default TodoTableContainer;