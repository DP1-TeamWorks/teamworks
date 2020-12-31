import React, { useState } from "react";
import "./App.css";
import Header from "./components/header/Header";
import Inbox from "./sections/Inbox";
import Settings from "./sections/Settings";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import LoginPage from "./components/Auth/LoginPage";
import "./sections/Section.css";

const MainPage = (props) => {
  return (
    <Router>
      <Switch>
        <Route exact path="/">
          <div className="App">
            <Header />
            <Inbox />
          </div>
        </Route>

        <Route path="/settings">
          <div className="App">
            <Header />
            <Settings />
          </div>
        </Route>
      </Switch>
    </Router>
  );
};

export default MainPage;
