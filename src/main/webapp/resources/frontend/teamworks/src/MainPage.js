import React, { useState } from "react";
import './App.css';
import Header from './components/header/Header';
import Settings from './sections/Settings';
import
{
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import "./sections/Section.css";

const MainPage = () =>
{
  
  return (
    <Router>
      <Switch>
        <Route path="/settings/team">
          <div className="App">
            <Header />
            <Settings />
          </div>
        </Route>
        <Route path="/">
          <div className="App">
            <Header />
            <Settings />
          </div>
        </Route>
      </Switch>
    </Router>
  );
}

export default MainPage;
