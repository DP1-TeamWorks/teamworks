import React, { useState } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import "./App.css";
import Header from "./components/header/Header";
import ScrollToTop from "./components/routing/ScrollToTop";
import Inbox from "./sections/Inbox";
import "./sections/Section.css";
import Settings from "./sections/Settings";

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
