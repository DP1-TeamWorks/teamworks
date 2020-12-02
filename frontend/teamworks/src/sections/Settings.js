import React from 'react';
import Section from "./Section"
import SettingsSidebar from "../components/sidebar/SettingsSidebar"

class Settings extends React.Component {
    constructor(props) 
    {
        super(props)
    }

    render()
    {
        return (
            <div className="Content">
                <SettingsSidebar />
                <Section className="Section--Lighter"></Section>
            </div>
        )
    }
}

export default Settings;