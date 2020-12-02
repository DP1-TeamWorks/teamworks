import React from 'react';
import Sidebar from './Sidebar';
import GradientButton from "../buttons/GradientButton"

class SettingsSidebar extends React.Component
{
    constructor(props)
    {
        super(props);
    }

    render()
    {
        return (
            <Sidebar>
                <GradientButton className="Sidebar__PaddedElement--Top">Add a new user</GradientButton>
            </Sidebar>
        )
    }
}

export default SettingsSidebar;
