import { useState } from "react";
import NewMessageMultiSelect from "../messages/NewMessage/NewMessageMultiSelect";
import SubmitButton from "./SubmitButton";

const AddTagToTaskForm = ({ tags, selectedTagIds, onTagsUpdated, todo }) =>
{

    let options;
    if (tags && selectedTagIds)
    {
        options = tags.map(t =>
        {
            return {
                label: t.title,
                value: t.id,
                tagColor: t.color
            };
        });
    }

    const selectedOptions = options.filter(x => selectedTagIds.indexOf(x.value) >= 0);

    const [selectedTagIdList, setSelectedTagIdList] = useState(selectedTagIds);
    const [submitEnabled, setSubmitEnabled] = useState(false);

    function onChange(f, v)
    {
        setSelectedTagIdList(v);
        setSubmitEnabled(true);
    }

    function onSubmit(e)
    {
        e.preventDefault();
        console.log(selectedTagIdList);
        onTagsUpdated(todo, selectedTagIdList);
        setSubmitEnabled(false);
    }

    return (
        <form onSubmit={onSubmit}>
            <NewMessageMultiSelect
                name="Task tags"
                changeHandler={onChange}
                options={options}
                defaultOptions={selectedOptions} />
            <SubmitButton
                reducedsize
                hasErrors={!submitEnabled}
                text="Update tags"
            />
        </form>

    );
}

export default AddTagToTaskForm;