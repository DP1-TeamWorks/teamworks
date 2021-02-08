import React from "react";
import Select from "react-select";
import InputError from "../../forms/InputError";
import CustomOptionComponent from "./CustomOptionComponent";

const NewMessageMultiSelect = ({
  name,
  placeholder,
  options,
  defaultOptions,
  changeHandler,
  className,
  error,
}) => {
  return (
    <div
      className={`InputContainer ${className}`}
      style={{
        marginBottom: "0px",
      }}
    >
      <p className="TitleNewMsg">{name}</p>
      <Select
        // components={{Option: CustomOptionComponent}}
        formatOptionLabel={CustomOptionComponent}
        options={options}
        defaultValue={defaultOptions}
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
      {error && <InputError error={error} />}
    </div>
  );
};

const customStyles = {
  option: (provided, state) => ({
    ...provided,
    color: "#a6ce56",
    background: state.isFocused ? "#ccc2" : null
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
