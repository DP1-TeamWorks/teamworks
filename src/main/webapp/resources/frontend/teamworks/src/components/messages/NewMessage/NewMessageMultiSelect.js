import React from "react";
import Select from "react-select";

const NewMessageMultiSelect = ({
  name,
  placeholder,
  options,
  changeHandler,
  className,
}) => {
  return (
    <div className={`InputContainer ${className}`}>
      <p className="TitleNewMsg">{name}</p>
      <Select
        options={options}
        onChange={(e) => {
          changeHandler(
            name,
            e.map((x) => {
              return x.value;
            })
          );
        }}
        styles={customStyles}
        name={name}
        placeholder={placeholder}
        isMulti
      ></Select>
    </div>
  );
};

const customStyles = {
  option: (provided, state) => ({
    ...provided,
    color: "#a6ce56",
  }),
  control: (base, state) => ({
    ...base,
    background: "#262626",
    borderRadius: 4,
    // Overwrittes the different states of border
    borderColor: "rgba(166, 206, 86, 0.8)",
    // Removes weird border around container
    boxShadow: state.isFocused ? null : null,
    marginBottom: 10,
  }),

  dropdownIndicator: (base) => ({
    ...base,
    fill: "#a6ce56",
    stroke: "#a6ce56",
  }),
  menu: (base) => ({
    ...base,
    backgroundColor: "#292d22",
  }),
  multiValue: (base) => ({
    ...base,
    backgroundColor: "#a6ce56",
  }),
  valueContainer: (base) => ({
    ...base,
    flexDirection: "row",
    flexWrap: "nowrap",
  }),
  multiValueLabel: (base) => ({
    ...base,
    color: "#262626",
  }),
};

export default NewMessageMultiSelect;
