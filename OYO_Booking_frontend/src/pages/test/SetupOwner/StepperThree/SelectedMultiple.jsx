import React, { useMemo, useState } from 'react';
import Select from 'react-select';

const customStyles = {
    menuList: (provided, state) => ({
        ...provided,
        width: '100%',
        display: 'flex',
        flexWrap: 'wrap',
        height: 'auto',
        maxHeight: '250px'
    }),

    menuPortal: (provided, state) => ({
        ...provided,
        width: 40
    }),

    option: (provided, state) => ({
        ...provided,
        width: '33.33333333%',
        borderBottom: '1px dotted pink',
        color: state.selectProps.menuColor,
        padding: 40,
        fontSize: '20px',
        textAlign: 'center',
        menuList: (provided, state) => ({
            ...provided,
            width: '100%',
            display: 'flex',
            flexWrap: 'wrap'
        })
    })
};
export default function SelectedMultiple(props) {
    const [selectedOptions, setSelectedOptions] = useState(Array(props.dataList.length).fill([]));

    const amenityArrays = useMemo(() => props.dataList.map(() => []), [props.dataList]);

    return (
        <div className="selected-multiple-step3" style={{ width: '100%', padding: '0 40px', marginTop: '100px', height:'575px', overflowX:'hidden' }}>
            {props.dataList?.map((listCate, index) => {
                listCate.infoFacilityList.map((convi, conviIndex) => {
                    const option = { label: convi.facilityName, value: conviIndex };
                    

                    if (!amenityArrays[index].some((person) => person.value === conviIndex)) {
                        amenityArrays[index].push(option);
                    }

                    return null;
                });

                return (
                    <div key={listCate.id} style={{display:'flex'}}>
                        <p style={{ fontSize: '16px', width:'200px' }}>{listCate.faciCateName}</p>
                        <div style={{width:'100%'}}>
                        <Select
                            defaultValue={selectedOptions[index]}
                            onChange={(event) => {
                                const updatedSelectedOptions = [...selectedOptions];
                                updatedSelectedOptions[index] = event;
                                setSelectedOptions(updatedSelectedOptions);

                                // Combine all selected options from different lists
                                const allSelectedOptions = updatedSelectedOptions.flat();
                                if (props?.setDataStep3) {
                                    props?.setDataStep3(allSelectedOptions);
                                }
                            }}
                            options={amenityArrays[index]}
                            isMulti={true}
                            styles={customStyles}
                            closeMenuOnSelect={false}
                        />
                        </div>
                       
                    </div>
                );
            })}
        </div>
    );
}
