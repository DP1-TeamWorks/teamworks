import logo from './logo.svg';
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

function App()
{
  return (
    <Router>
      <Switch>
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
