import React from "react";
import { useEffect, useState } from "react/cjs/react.development";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLongArrowAltDown } from "@fortawesome/free-solid-svg-icons";

const FilePreview = () => {
  return (
    <div className="FilePreview" onClick={null}>
      <FontAwesomeIcon icon={faLongArrowAltDown} />
    </div>
  );
};

export default FilePreview;
