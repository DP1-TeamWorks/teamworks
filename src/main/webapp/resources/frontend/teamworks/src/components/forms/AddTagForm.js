import React from "react";
import TagApiUtils from "../../utils/api/TagApiUtils";
import AddForm from "./AddForm";
import "./AddUserForm.css";
import Input from "./Input";

const AddTagForm = ({projectId, onTagAdded}) =>
{

  function hslToHex(h, s, l) {
    l /= 100;
    const a = s * Math.min(l, 1 - l) / 100;
    const f = n => {
      const k = (n + h / 30) % 12;
      const color = l - a * Math.max(Math.min(k - 3, 9 - k, 1), -1);
      return Math.round(255 * color).toString(16).padStart(2, '0');   // convert to Hex and prefix "0" if needed
    };
    return `#${f(0)}${f(8)}${f(4)}`;
  }
  
  function addTag(tag)
  {
    // copy so we dont modify the original object
    const post = 
    {
      title: tag.title,
      color: hslToHex(Math.random() * 360, 100, 50)
    }; 
    return new Promise((resolve, reject) =>
    {
      TagApiUtils.addNewTag(projectId, post)
      .then(() => 
      {
        resolve();
        if (onTagAdded)
          onTagAdded();
      })
      .catch((err) => reject(err));
    });
  }

  return (
    <AddForm submitText="Add tag" postFunction={addTag} alreadyExistsErrorText="A tag with that name already exists in this project." tooManyErrorText="There can only be a maximum of 6 tags per project.">
      <p className="InputTitle">Tag</p>
      <Input name="title" placeholder="Development" />
    </AddForm>
  );
};

export default AddTagForm;