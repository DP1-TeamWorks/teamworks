import { useState } from "react";
import "./App.css";
import Login from "./components/Auth/Login/Login";

function App() {
  const [userSession, setUserSession] = useState({});
  return (
    <div className={"background"}>
      {userSession!=={} && <Login setUserSession={setUserSession} />}
    </div>
  );
}

export default App;
