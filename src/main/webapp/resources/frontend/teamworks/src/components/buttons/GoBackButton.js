import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowLeft } from "@fortawesome/free-solid-svg-icons";
import "./GoBackButton.css";
import LinkButton from "./LinkButton";

const GoBackButton = ({ path, darker, anchored }) =>
{
    return (
        <LinkButton className={`GoBack ${darker ? "Button--Darker" : ""} ${anchored ? "GoBack--Anchored" : ""}`} path={path}>
            <span className="GoBackText">
                <FontAwesomeIcon icon={faArrowLeft} style={{ color: "#A6CE56" }} className="BackArrow" />
                {"      "} GO BACK
            </span>
        </LinkButton>
    );
};

export default GoBackButton;