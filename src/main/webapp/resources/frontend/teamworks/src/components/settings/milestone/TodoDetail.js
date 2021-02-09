import GradientButton from "../../buttons/GradientButton"
import AddTagToTaskForm from "../../forms/AddTagToTaskForm"
import AssignUserForm from "../../forms/AssignUserForm"
import EditableField from "../EditableField"
import SettingGroup from "../SettingGroup"

const TodoDetail = ({ onCollapseClicked, onMarkAsDoneClicked, onNameUpdated, onUserAssigned, onTagsUpdated, todo, tags, projectId, milestoneId }) =>
{
    let assigneeDescription;
    if (todo.assigneeId)
    {
        const completename = todo.assigneeName + " " + todo.assigneeLastname;
        const username = completename.toLowerCase().replace(/ /g, "");
        assigneeDescription = `Currently assigned to <a href="/settings/users/${todo.assigneeId}/${username}">${todo.assigneeName} ${todo.assigneeLastname}</a>`;
    } else
    {
        assigneeDescription = "Currently assigned to no one.";
    }
    return (
        <div className="TodoDetailContainer">
            <span onClick={onCollapseClicked} className="BoldTitle BoldTitle--Smallest ActionButton CloseButton">Collapse</span>
            <GradientButton
                className="MarkAsDoneButton"
                onClick={() => onMarkAsDoneClicked(todo)}
                reducedsize>
                {todo.done ? "Unmark as done" : "Mark as done"}
            </GradientButton>
            <SettingGroup name="Title">
                <EditableField
                    value={todo.title}
                    postFunction={p => onNameUpdated(todo, p)}
                    fieldName="title" />
            </SettingGroup>
            <SettingGroup name="Assignee" description={assigneeDescription}>
                <AssignUserForm
                    onUserAssigned={onUserAssigned}
                    submitText={`Assign to task`}
                    projectId={projectId}
                    milestoneId={milestoneId}
                    todo={todo} />
            </SettingGroup>
            <SettingGroup name="Tags" description=" ">
                <AddTagToTaskForm tags={tags} selectedTagIds={todo.tags.map(x => x.id)} todo={todo} onTagsUpdated={onTagsUpdated} />
            </SettingGroup>
        </div>
    )
}

export default TodoDetail;