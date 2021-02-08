import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Fragment } from "react";
import { Link } from "react-router-dom";
import { useState } from "react/cjs/react.development";
import Circle from "../../projects/tags/Circle";
import TodoDetail from "./TodoDetail";

const TodoTable = ({ todos, tags }) => 
{
    // selected todo index
    const [selectedIndex, setSelectedIndex] = useState(-1);

    let elements;
    if (todos)
    {
        elements = todos.map((t, i) =>
        {
            let tagElements = t.tags.map(x =>
            {
                return <Circle key={x.color} color={x.color} />;
            });

            let todoDetail;
            if (i === selectedIndex)
            {
                todoDetail = (
                    <tr>
                        <td colSpan={4}>
                            <TodoDetail todo={t} onCollapseClicked={() => setSelectedIndex(-1)} tags={tags} />
                        </td>
                    </tr>
                );
            }
            const completename = t.assigneeName + " " + t.assigneeLastname;
            const username = completename.toLowerCase().replace(/ /g, "");
            return (
                <Fragment key={t.id}>
                    <tr>
                        <td><i>{t.done ? "Done: " : ""}</i>{t.title}</td>
                        <td><Link to={`/settings/users/${t.assigneeId}/${username}`}>{t.assigneeName} {t.assigneeLastname}</Link></td>
                        <td>{tagElements}</td>
                        <td className="ActionStrip">
                            <span onClick={() => setSelectedIndex(i)} className="BoldTitle BoldTitle--Smallest ActionButton">Modify</span>
                            <span onClick={() => console.log("b")} className="BoldTitle BoldTitle--Smallest ActionButton">Mark as done</span>
                            <span onClick={() => console.log("c")} className="BoldTitle BoldTitle--Smallest ActionButton">Remove</span>
                        </td>
                    </tr>
                    {todoDetail}
                </Fragment>
            );
        });
    }

    return (
        <table className="UserList">
            <thead className="UserList__thead">
                <tr>
                    <th>Name</th>
                    <th>Assignee</th>
                    <th>Tags</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td colSpan={4} className="AddTodoButton">
                        <FontAwesomeIcon
                            icon={faPlus}
                            className="AddIcon"/>
                    New task
                    </td>
                </tr>
                {elements}
            </tbody>
        </table>
    );
};

export default TodoTable;