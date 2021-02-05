import React, { useState } from "react";
import "./App.css";
import Header from "./components/header/Header";
import Settings from "./sections/Settings";
import Inbox from "./sections/Inbox";
import ScrollToTop from "./components/routing/ScrollToTop";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import "./sections/Section.css";

const MainPage = () => {
  const [search, setSearch] = useState("");
  return (
    <Router>
      <ScrollToTop />
      <Switch>
        <Route exact path="/">
          <Header handleSearch={setSearch} />
          <Inbox search={search} setSearch={setSearch} />
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
