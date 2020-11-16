import "./App.css";
import { Chat } from "./components/Chat/Chat";
import { ServerHeader } from "./components/ServerHeader/ServerHeader";

function App() {
  return (
    <div className={"background"}>
      <ServerHeader
        numberOfPeople={5}
        serverUrl={"red-panda.colorlounge.com"}
      />

      <Chat />
    </div>
  );
}

export default App;
