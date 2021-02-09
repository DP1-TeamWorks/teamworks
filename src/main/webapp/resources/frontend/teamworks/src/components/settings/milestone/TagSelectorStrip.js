import "./TodoTable.css";
import TagTab from "./TagTab";
import { useEffect, useState } from "react";
import { Fragment } from "react";

const TagSelectorStrip = ({ tags, onSelectedIndexChanged }) =>
{

    const [selectedIndex, setSelectedIndex] = useState(0);

    useEffect(() =>
    {
        if (onSelectedIndexChanged)
        {
            if (selectedIndex === 0)
                onSelectedIndexChanged(-1);
            else
                onSelectedIndexChanged(tags[selectedIndex-1].id);
        }
    }, [selectedIndex, onSelectedIndexChanged, tags]);

    if (tags)
    {
        let tagElements = tags.map((x, i) =>
        {
            return (
                <Fragment key={x.id}>
                    <TagTab
                        selected={selectedIndex - 1 === i}
                        color={x.color}
                        onClick={() => setSelectedIndex(i+1)}>
                        {x.title}
                    </TagTab>
                    {i < tags.length - 1 ? <div className="TagSeparator" /> : ""}
                </Fragment>
            );
        });
        return (
            <div className="TagTabContainer">
                <TagTab
                    selected={selectedIndex === 0}
                    onClick={() => setSelectedIndex(0)}>
                    All tasks
                    </TagTab>
                <div className="TagSeparator" />
                {tagElements}
            </div>
        );
    } else
    {
        return;
    }
}

export default TagSelectorStrip;