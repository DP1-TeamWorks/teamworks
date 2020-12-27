import React, { useEffect, useState } from "react";
import LoginPage from "./components/Auth/LoginPage";
import MainPage from "./MainPage"
import "./sections/Section.css";
import "./App.css";
import AuthApiUtils from "./utils/api/AuthApiUtils";

function App()
{
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  
  // TODO: Loading state to prevent flicker
  
  useEffect(() =>
  {
    if (!isLoggedIn)
    {
      AuthApiUtils.islogged()
      .then(() => setIsLoggedIn(true))
      .catch(() => setIsLoggedIn(false));
    }
  });

  let page;
  if (isLoggedIn)
  {
    page = <MainPage onLoginChanged={setIsLoggedIn} />;
  }
  else
  {
    page = <LoginPage onLoginChanged={setIsLoggedIn} />;
  }

  return (
    <div className="App">
      {page}
    </div>
  )
}

export default App;
