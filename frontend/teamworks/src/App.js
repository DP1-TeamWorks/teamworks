import { useState } from "react";
import "./App.css";
import AuthRouter from "./components/Auth/AuthRouter";
import Login from "./components/Auth/Login/Login";

function App() {
  const [session, setSession] = useState();
  return (
    <div className={"background"}>
      {!session && <AuthRouter setSession={setSession} />}
      {session && <p>Holowo {session.name} {session.lastname}</p>}
    </div>
  );
}

export default App;
