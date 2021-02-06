import React from "react";
import "./Forms.css";
import SubmitError from "./SubmitError";
import GradientButton from "../buttons/GradientButton";
import Spinner from "../spinner/Spinner";

export default function SubmitButton({ text, requestError, hasErrors, reducedsize, loading }) {
  let content = loading ? <Spinner dark /> : text;
  return (
    <>
      <br />
      <GradientButton
        className={ reducedsize ? "" : "SubmitButton"}
        type="submit"
        disabled={hasErrors}
      >{content}</GradientButton>
      <SubmitError error={requestError !== "" && requestError} />
    </>
  );
}
