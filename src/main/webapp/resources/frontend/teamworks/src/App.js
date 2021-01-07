import React, { useEffect, useState } from "react";
import LoginPage from "./components/Auth/LoginPage";
import MainPage from "./MainPage"
import "./sections/Section.css";
import "./App.css";
import AuthApiUtils from "./utils/api/AuthApiUtils";

function App()
{
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [hasLoaded, setHasLoaded] = useState(false);

  useEffect(() =>
  {
    if (!isLoggedIn && !hasLoaded)
    {
      AuthApiUtils.islogged()
        .then(() =>
        {
          setIsLoggedIn(true);
          setHasLoaded(true);
        })
        .catch(() =>
        {
          setIsLoggedIn(false);
          setHasLoaded(true);
        });
    }
  });

  let page;
  if (hasLoaded)
  {
    if (isLoggedIn)
    {
      page = <MainPage onLoginChanged={setIsLoggedIn} />;
    }
    else
    {
      page = <LoginPage onLoginChanged={setIsLoggedIn} />;
    }
  }


  return (
    <div className="App">
      {page}
    </div>
  )
}

export default App;
