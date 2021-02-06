import { withRouter } from "react-router-dom";
import Button from "./Button";
import GradientButton from "./GradientButton";

const LinkButton = ({history, path, children, className, gradient, back, reset}) =>
{
    function onButtonClicked()
    {
        if (back)
        {
            history.goBack()
        } else
        {
            history.push(path)
        }
    }

    if (gradient)
    {
        return <GradientButton className={className} onClick={onButtonClicked}>{children}</GradientButton>
    }
    else
    {
        return <Button className={className} onClick={onButtonClicked}>{children}</Button>
    }
}

export default withRouter(LinkButton);