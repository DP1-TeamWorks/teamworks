import { useState } from "react";
import "./App.css";
import Login from "./components/Auth/Login/Login";

function App() {
  const [session, setSession] = useState({});
  return (
    <div className={"background"}>
      {session!=={} && <Login setSession={setSession} />}
    </div>
  );
}

export default App;
