import React, { useState } from "react";
import "./App.css";
import Header from "./components/header/Header";
import Settings from "./sections/Settings";
import Inbox from "./sections/Inbox";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import "./sections/Section.css";

const MainPage = () => {
  return (
    <Router>
      <Switch>
        <Route exact path="/">
          <Header />
          <Inbox />
        </Route>
        <Route path="/settings">
          <Header nosearchbar />
          <Settings />
        </Route>
      </Switch>
    </Router>
  );
};

export default MainPage;
