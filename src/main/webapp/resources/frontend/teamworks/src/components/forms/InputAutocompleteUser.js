import { useState, useEffect } from "react";
import Autosuggest from "react-autosuggest";
import DepartmentApiUtils from "../../utils/api/DepartmentApiUtils";
import ProjectApiUtils from "../../utils/api/ProjectApiUtils";
import UserApiUtils from "../../utils/api/UserApiUtils";
import "./InputAutocomplete.css";

const InputAutocompleteUser = ({ name, placeholder, departmentId, projectId, onUserSelected, val, onChangeHandler }) =>
{
    function getUserAutocompleteSuggestions(value)
    {
        const inputValue = value.trim().toLowerCase();
        const inputLength = inputValue.length;
        return inputLength === 0 ? [] : users.filter(usr =>
            (usr.name + " " + usr.lastname).toLowerCase().slice(0, inputLength) === inputValue
        );
    }

    function getSuggestionValue(suggestion)
    {
        return suggestion.name + " " + suggestion.lastname;
    }

    function renderSuggestion(suggestion)
    {
        return <div>{suggestion.name} {suggestion.lastname}</div>
    }

    function onChange(event, { newValue })
    {
        onChangeHandler(name, newValue);
        if (selectedUserId !== -1)
        {
            setSelectedUserId(-1);
            if (onUserSelected)
                onUserSelected(name, newValue, -1);
        }
    }

    function onSuggestionFetchRequested({ value })
    {
        setSuggestions(getUserAutocompleteSuggestions(value));
    }

    function onSuggestionClearRequested()
    {
        setSuggestions([]);
    }

    function onSuggestionSelected(event, {suggestion})
    {
        if (onUserSelected)
            onUserSelected(name, getSuggestionValue(suggestion), suggestion.id);
        setSelectedUserId(suggestion.id);
    }

    function onBlur()
    {
        if (selectedUserId === -1)
            onChangeHandler(name, "");
    }

    const [users, setUsers] = useState(null);
    const [selectedUserId, setSelectedUserId] = useState(-1);
    const [suggestions, setSuggestions] = useState([]);

    useEffect(() =>
    {
        if (projectId)
        {
            ProjectApiUtils.getMembersFromProject(projectId)
            .then(res => 
                {
                    setUsers()
                })
            .catch(err => console.error(err));
        } else if (departmentId)
        {
            DepartmentApiUtils.getMembersFromDepartment(departmentId)
            .then(res => setUsers(res.map(x => {
                x.id = x.userId;
                return x;
            })))
            .catch(err => console.error(err));
        } else
        {
            UserApiUtils.getAllUsers()
            .then(res => setUsers(res))
            .catch(err => console.error(err));
        }
    }, []);

    let resultError = false;
    if (users && users.length === 0)
    {
        if (projectId)
        {
            resultError = "No users in this project.";
        } else if (departmentId)
        {
            resultError = "No users in this department.";
        } else
        {
            resultError = "No users found.";
        }
    }

    let placehold;
    if (resultError)
        placehold = resultError;
    else if (users)
        placehold = placeholder;
    else
        placehold = "Loading users...";

    const inputProps = {
        name,
        placeholder: placehold,
        value: val,
        onChange,
        onBlur,
        disabled: Boolean(resultError) || !users,
        className: "Input EditingInput",
    };

    const theme =
    {
        container: 'SuggestionsContainer',
        suggestionsList: 'SuggestionsList',
        suggestion: 'Suggestion',
        suggestionHighlighted: 'Suggestion--Highlighted'
    }

    return (
        <div className="EditableField EditableField--OnlyInput">
            <Autosuggest
                suggestions={suggestions}
                onSuggestionsFetchRequested={onSuggestionFetchRequested}
                onSuggestionsClearRequested={onSuggestionClearRequested}
                onSuggestionSelected={onSuggestionSelected}
                getSuggestionValue={getSuggestionValue}
                renderSuggestion={renderSuggestion}
                theme={theme}
                inputProps={inputProps} />
        </div>

    );
}

export default InputAutocompleteUser;