import "./Section.css"

const Section = (props) =>
{

    return (
        <div className={`Section ${props.className}`}>
            {props.children}
        </div>
    )
}

export default Section