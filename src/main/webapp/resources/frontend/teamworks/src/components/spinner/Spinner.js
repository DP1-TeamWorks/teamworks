import "./Spinner.css";

const Spinner = ({dark, red}) =>
{
    return <div className={`Spinner ${dark ? 'Spinner--Dark' : ''} ${red ? 'Spinner--Red' : ''}`}></div>;
}

export default Spinner;