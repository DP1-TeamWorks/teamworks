import { withRouter } from "react-router-dom";
import Button from "./Button";
import GradientButton from "./GradientButton";

const LinkButton = ({history, path, children, className, gradient}) =>
{
    if (gradient)
    {
        return <GradientButton className={className} onClick={() => history.push(path)}>{children}</GradientButton>
    }
    else
    {
        return <Button className={className} onClick={() => history.push(path)}>{children}</Button>
    }
}

export default withRouter(LinkButton);