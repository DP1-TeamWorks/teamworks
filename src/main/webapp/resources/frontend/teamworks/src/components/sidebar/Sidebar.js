import './Sidebar.css';

const Sidebar = (props) => {
    return (
        <div className="Sidebar">
            {props.children}
        </div>
    )
}

export default Sidebar;
