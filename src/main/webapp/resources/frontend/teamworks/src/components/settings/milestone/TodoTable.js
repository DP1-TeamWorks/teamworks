import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Fragment } from "react";
import { Link } from "react-router-dom";
import { useEffect, useState } from "react/cjs/react.development";
import ToDoApiUtils from "../../../utils/api/ToDoApiUtils";
import Circle from "../../projects/tags/Circle";
import Spinner from "../../spinner/Spinner";
import TodoDetail from "./TodoDetail";

const TodoTable = ({ tags, projectId, milestoneId, selectedTagId }) => 
{
    // selected todo index
    const [selectedIndex, setSelectedIndex] = useState(-1);
    const [todos, setTodos] = useState([]);
    const [addLoading, setAddLoading] = useState(true);

    const fetchTodos = () =>
    {
        setSelectedIndex(-1);
        ToDoApiUtils.getToDos(milestoneId)
            .then(t => 
            {
                setTodos(t);
                setAddLoading(false);
            })
            .catch(err => console.error(err));
    }

    const refreshTodos = () =>
    {
        // refresh but no fetch
        setTodos(JSON.parse(JSON.stringify(todos)));
        setAddLoading(false);
    }

    useEffect(() =>
    {
        fetchTodos();
    }, [milestoneId]);


    function addNewTodo()
    {
        setAddLoading(true);
        ToDoApiUtils.createTodo(milestoneId, { title: "New task", done: false })
            .then(x =>
            {
                if (selectedTagId !== -1)
                {
                    ToDoApiUtils.setTags(milestoneId, x.id, [selectedTagId])
                        .then(() =>
                        {
                            fetchTodos();
                        })
                        .catch(err => console.error(err));
                }
                else
                {
                    fetchTodos();
                }
            })
            .catch(err => console.error(err));
    }

    function markAsDone(todo)
    {
        setAddLoading(true);
        ToDoApiUtils.markToDoAsDone(todo.id, todo.done)
            .then(() => 
            {
                todo.done = !todo.done;
                refreshTodos()
            })
            .catch(err => console.error(err));
    }

    function deleteTodo(todo)
    {
        if (window.confirm(`Are you sure to delete task "${todo.title}"?`))
        {
            setAddLoading(true);
            ToDoApiUtils.deleteTodo(milestoneId, todo.id)
                .then(() =>
                {
                    const idx = todos.indexOf(todos.find(x => x.id === todo.id))
                    todos.splice(idx, 1);
                    refreshTodos();
                })
                .catch(err => console.log(err));
        }
    }

    function onNameUpdated(todo, p)
    {
        p.id = todo.id;
        return new Promise((resolve, reject) =>
        {
            ToDoApiUtils.updateTitle(milestoneId, p)
                .then(() => 
                {
                    todo.title = p.title;
                    refreshTodos();
                    resolve();
                })
                .catch(err => reject(err))
        });
    }

    function onUserAssigned(updateObject)
    {
        let todo = updateObject.todo;
        todo.assigneeId = updateObject.id;
        todo.assigneeName = updateObject.name;
        todo.assigneeLastname = "";
        refreshTodos();
    }

    function onTagsUpdated(todo, tagIdList)
    {
        ToDoApiUtils.setTags(milestoneId, todo.id, tagIdList)
            .then(x => 
            {
                todo.tags = x.tags;
                refreshTodos()
            })
            .catch(err => console.error(err));
    }

    function compareTodo(a, b)
    {
        function compareDone(x, y)
        {
            if (x.done === y.done)
                return 0;
            else if (x.done)
                return 1;
            else
                return -1;
        }

        function compareAssignee(x, y)
        {
            if (x.assigneeId === y.assigneeId)
                return 0;
            else if (x.assigneeId === null)
            {
                return -1;
            }
            else if (y.assigneeId === null)
            {
                return 1;
            } else
            {
                return compareString(x.assigneeName + x.assigneeLastname, y.assigneeName + y.assigneeLastname);
            }
        }

        function compareString(x, y)
        {
            return x.localeCompare(y);
        }

        const donecomp = compareDone(a, b);
        if (donecomp !== 0)
            return donecomp;

        const assigneecomp = compareAssignee(a, b);
        if (assigneecomp !== 0)
            return assigneecomp;

        const tagcomp = b.tags.length - a.tags.length;
        if (tagcomp !== 0)
            return tagcomp;

        return compareString(a.title, b.title);
    }


    let elements;
    if (todos)
    {
        let arr = todos;
        if (selectedTagId !== -1)
            arr = todos.filter(x => x.tags.map(x => x.id).indexOf(selectedTagId) >= 0);

        if (selectedIndex === -1)
            arr = arr.sort(compareTodo);

        // const selectedIndexAfterSort = arr.indexOf(arr.find((x,i) => i === selectedIndex))

        elements = arr.map((t, i) =>
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
                            <TodoDetail
                                todo={t}
                                onCollapseClicked={() => setSelectedIndex(-1)}
                                onMarkAsDoneClicked={markAsDone}
                                onNameUpdated={onNameUpdated}
                                onUserAssigned={onUserAssigned}
                                onTagsUpdated={onTagsUpdated}
                                tags={tags}
                                projectId={projectId}
                                milestoneId={milestoneId} />
                        </td>
                    </tr>
                );
            }
            const completename = t.assigneeName + " " + t.assigneeLastname;
            const username = completename.toLowerCase().replace(/ /g, "");
            // this could be its own component to be honest
            return (
                <Fragment key={t.id}>
                    <tr>
                        <td className={t.done ? "DoneTableCell" : ""}><i>{t.done ? "Done: " : ""}</i>{t.title}</td>
                        <td><Link to={`/settings/users/${t.assigneeId}/${username}`}>{t.assigneeName} {t.assigneeLastname}</Link></td>
                        <td>{tagElements}</td>
                        <td className="ActionStrip">
                            <span onClick={() => setSelectedIndex(i)} className="BoldTitle BoldTitle--Smallest ActionButton">Modify</span>
                            <span onClick={() => markAsDone(t)} className="BoldTitle BoldTitle--Smallest ActionButton">{t.done ? "Unm" : "M"}ark as done</span>
                            <span onClick={() => deleteTodo(t)} className="BoldTitle BoldTitle--Smallest ActionButton">Remove</span>
                        </td>
                    </tr>
                    {todoDetail}
                </Fragment>
            );
        });
    }

    return (
        <table className="UserList TodoTable">
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
                    <td colSpan={4} className="AddTodoButton" onClick={addNewTodo}>
                        <FontAwesomeIcon
                            icon={faPlus}
                            className="AddIcon" />
                        New task
                        {addLoading ? <Spinner green className="TodoTable__Spinner" /> : ""}
                    </td>
                </tr>
                {elements}
            </tbody>
        </table>
    );
};

export default TodoTable;