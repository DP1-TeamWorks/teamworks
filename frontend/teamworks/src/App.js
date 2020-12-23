import logo from './logo.svg';
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
import AuthRouter from "./components/Auth/AuthRouter";
import "./sections/Section.css";

function App()
{
  const [session, setSession] = useState();

  return (
    <Router>
      <Switch>
        <Route path="/login">
          <div className="App">
            {!session && <AuthRouter setSession={setSession} />}
            {session && <p>Holowo {session.name} {session.lastname}</p>}
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

export default App;
