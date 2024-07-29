import React, { useState, useEffect } from 'react';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormHelperText from '@mui/material/FormHelperText';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

import { getListProvices } from '~/services/apis/provinceApi';
import { ProvinceModel } from '~/models/province';
import { t } from 'i18next';
import { useDispatch } from 'react-redux';
import setupOwnerSlice from '../setupOwnerSlice';
import publicProvinceAPI from '~/services/apis/publicAPI/publicProvinceAPI';

export default function SelectedLocate(props) {
    const [age, setAge] = useState(
        props?.dataFilterDefauld?.provinceCode ? props?.dataFilterDefauld?.provinceCode : ''
    );
    const [proviceList, setProvinceList] = useState([]);
    const dispatch = useDispatch();

    useEffect(() => {
        publicProvinceAPI.getAllProvinceDetails().then((dataResponse) => {
            if (dataResponse.data) {
                setProvinceList(dataResponse.data);
            }
        });
    }, []);

    const handleChange = (event) => {
        setAge(event.target.value);
        props.setValueStepOne(event.target.value);
        const provinceName = proviceList.find((pro) => pro.provinceCode === event.target.value);
        if (provinceName) {
            dispatch(setupOwnerSlice.actions.addProvinceNameRoom(provinceName.name));
        }
    };

    return (
        <div className="selected-locate">
            <FormControl sx={{ m: 1, minWidth: 120, width: '50%' }}>
                <InputLabel
                    id="demo-simple-select-helper-label"
                    style={{ zIndex: '80', background: 'white', paddingRight: '5px' }}
                >
                    Tỉnh thành
                </InputLabel>
                <Select
                    labelId="demo-simple-select-helper-label"
                    id="demo-simple-select-helper"
                    value={age}
                    label="Age"
                    onChange={handleChange}
                >
                    <MenuItem value="">
                        <em>None</em>
                    </MenuItem>
                    {proviceList?.map((province, index) => (
                        <MenuItem value={province.provinceCode} style={{ fontSize: '15px' }} key={index}>
                            {province.provinceName}
                        </MenuItem>
                    ))}
                </Select>
                <FormHelperText sx={{ fontSize: '10px' }}>{t('contentMess.province')}</FormHelperText>
            </FormControl>
        </div>
    );
}
