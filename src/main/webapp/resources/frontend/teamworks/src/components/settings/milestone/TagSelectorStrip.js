import "./TodoTable.css";
import TagTab from "./TagTab";
import { useState } from "react/cjs/react.development";
import { Fragment } from "react";

const TagSelectorStrip = ({ tags }) =>
{

    const [selectedIndex, setSelectedIndex] = useState(0);

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
                        {x.name}
                    </TagTab>
                    <div className="TagSeparator" />
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