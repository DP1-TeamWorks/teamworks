import { useState, useEffect } from "react";
import Autosuggest from "react-autosuggest";
import UserApiUtils from "../../utils/api/UserApiUtils";
import "./InputAutocomplete.css";

const InputAutocompleteUser = ({ name, placeholder, onUserSelected, val, onChangeHandler }) =>
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

    const [users, setUsers] = useState([]);
    const [selectedUserId, setSelectedUserId] = useState(-1);
    const [suggestions, setSuggestions] = useState([]);

    useEffect(() =>
    {
        UserApiUtils.getAllUsers()
            .then(res => setUsers(res))
            .catch(err => console.error(err));
    }, []);

    const inputProps = {
        name,
        placeholder,
        value: val,
        onChange,
        onBlur,
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