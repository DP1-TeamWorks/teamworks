import React from "react";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import Login from "./Login";
import SignUp from "./SignUp";

export default function AuthRouter({ setUserSession }) {
  return (
    <>
      <Router>
        {/* A <Switch> looks through its children <Route>s and
            renders the first one that matches the current URL. */}
        <Switch>
          <Route path="/">
            <Login />
          </Route>
          <Route path="/users">
            <SignUp />
          </Route>
        </Switch>
      </Router>
    </>
  );
}
