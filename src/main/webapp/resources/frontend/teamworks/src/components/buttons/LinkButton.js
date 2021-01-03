import { withRouter } from "react-router-dom";
import Button from "./Button";

const LinkButton = ({history, path, children, className}) =>
{
    return <Button className={className} onClick={() => history.push(path)}>{children}</Button>
}

export default withRouter(LinkButton);