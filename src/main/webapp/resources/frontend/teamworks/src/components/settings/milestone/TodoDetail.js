import GradientButton from "../../buttons/GradientButton"
import AddTagToTaskForm from "../../forms/AddTagToTaskForm"
import AssignUserForm from "../../forms/AssignUserForm"
import EditableField from "../EditableField"
import SettingGroup from "../SettingGroup"

const TodoDetail = ({onCollapseClicked, todo, tags}) =>
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
                reducedsize>
                {todo.done ? "Unmark as done" : "Mark as done"}
            </GradientButton>
            <SettingGroup name="Title">
                <EditableField
                    value={todo.title}
                    fieldName="title"
                    onUpdated={() => console.log('b')} />
            </SettingGroup>
            <SettingGroup name="Assignee" description={assigneeDescription}>
                <AssignUserForm submitText={`Assign to task`} />
            </SettingGroup>
            <SettingGroup name="Tags" description=" ">
                <AddTagToTaskForm tags={tags} selectedTagIds={[1]} />
            </SettingGroup>
        </div>
    )
}

export default TodoDetail;