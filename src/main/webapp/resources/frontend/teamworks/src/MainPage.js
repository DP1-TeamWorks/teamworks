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
import LoginPage from "./components/Auth/LoginPage";
import "./sections/Section.css";

const MainPage = (props) =>
{
  
  return (
    <Router>
      <Switch>
        <Route path="/settings">
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
