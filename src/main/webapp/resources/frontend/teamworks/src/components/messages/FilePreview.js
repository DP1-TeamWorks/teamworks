import React from "react";
import { useEffect, useState } from "react";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLongArrowAltDown } from "@fortawesome/free-solid-svg-icons";

const FilePreview = (url) => {
  console.log(url.url);
  return (
    <a href={"" + url.url} target="_blank">
      <div className="FilePreview" onClick={null}>
        <FontAwesomeIcon icon={faLongArrowAltDown} />
      </div>
    </a>
  );
};

export default FilePreview;
