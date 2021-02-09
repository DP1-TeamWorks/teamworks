import "./Spinner.css";

const Spinner = ({dark, red, green, className}) =>
{
    return <div className={`Spinner ${dark ? 'Spinner--Dark' : ''} ${red ? 'Spinner--Red' : ''} ${green ? 'Spinner--Green' : ''} ${className}`}></div>;
}

export default Spinner;