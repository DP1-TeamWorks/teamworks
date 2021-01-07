import React from "react";
import { BrowserRouter as Router, Switch, Route, Redirect } from "react-router-dom";
import Login from "./Login/Login";
import SignUp from "./SignUp/SignUp";

const LoginPage = ({ onLoginChanged }) =>
{
  return (
    <Router>
      <Switch>
        <Route exact path="/">
          <Login onLoginChanged={onLoginChanged} />
        </Route>
        <Route path="/signup">
          <SignUp />
        </Route>
        <Route path="/">
          <Redirect to="/" />
        </Route>
      </Switch>
    </Router>

  );
}

export default LoginPage;
