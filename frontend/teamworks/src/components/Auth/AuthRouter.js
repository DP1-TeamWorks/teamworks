import React from "react";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import Login from "./Login/Login";
import SignUp from "./SignUp/SignUp";

export default function AuthRouter({ setSession }) {
  return (
    <>
      <Router>
        <Switch>
          <Route exact path="/">
            <Login setSession={setSession} />
          </Route>
          <Route path="/login">
            <Login setSession={setSession} />
          </Route>
          <Route path="/signup">
            <SignUp />
          </Route>
        </Switch>
      </Router>
    </>
  );
}
