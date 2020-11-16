import React from "react";
import "./header.css";
export function ServerHeader({ numberOfPeople, serverUrl }) {
  return (
    <React.Fragment>
      <div className={"container"}>
        <header className={"serverBar"}>
          <p>
            {numberOfPeople} connected in <a href={serverUrl}>{serverUrl}</a>
          </p>
        </header>
      </div>
    </React.Fragment>
  );
}
