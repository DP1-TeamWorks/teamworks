import { useState } from "react";
import "./App.css";
import AuthRouter from "./components/Auth/AuthRouter";

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
