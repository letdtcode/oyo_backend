import { useState, useEffect } from 'react';
import * as React from 'react';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import { t } from 'i18next';
import publicProvinceAPI from '~/services/apis/publicAPI/publicProvinceAPI';
import './SelectAddress.scss';

export default function SelectAddress(props) {
    const [selectedProvince, setSelectedProvince] = useState(null);
    const [selectedDistrict, setSelectedDistrict] = useState(null);
    const [selectedWard, setSelectedWard] = useState(null);

    const [districts, setDistricts] = useState([]);
    const [wards, setWards] = useState([]);
    const [provinces, setProvinces] = useState(() => {
        const storedData = localStorage.getItem('allProvinces');
        return storedData ? JSON.parse(storedData) : [];
    });
    useEffect(() => {
        if (provinces.length === 0) {
            publicProvinceAPI
                .getAllProvinceDetails()
                .then((res) => {
                    setProvinces(res.data);
                    localStorage.setItem('allProvinces', JSON.stringify(res.data));
                })
                .catch((error) => {
                    console.error('Lỗi khi lấy thông tin tỉnh thành:', error);
                });
        }
    }, [provinces]);

    useEffect(() => {
        const temp =
            provinces.find(
                (option) =>
                    option.provinceCode === props?.data.provinceCode || option.provinceName === props?.data.provinceName
            ) || null;
        if (temp !== null) {
            setSelectedProvince(temp);
            setDistricts(temp.districtSet);
        }
    }, [provinces.length, props?.data.provinceCode, props?.data.provinceName]);

    useEffect(() => {
        const temp =
            districts.find(
                (option) =>
                    option.districtCode === props?.data.districtCode || option.districtName === props?.data.districtName
            ) || null;
        if (temp !== null) {
            setSelectedDistrict(temp);
            setWards(temp.wardSet);
        }
    }, [districts.length]);

    useEffect(() => {
        const temp =
            wards.find(
                (option) => option.wardCode === props?.data.wardCode || option.wardName === props?.data.wardName
            ) || null;
        if (temp !== null) {
            setSelectedWard(temp);
        }
    }, [wards]);

    const handleProvinceChange = (event, newValue) => {
        setSelectedProvince(newValue);
        setSelectedWard(null);
        setSelectedDistrict(null);
        props?.setData((prevData) => ({
            provinceCode: newValue?.provinceCode || null,
            provinceName: newValue?.provinceName || null,
            districtCode: null,
            wardCode: null
        }));

        setDistricts(newValue ? newValue.districtSet : []);
        setWards([]);
    };

    const handleDistrictChange = (event, newValue) => {
        setSelectedDistrict(newValue);
        setSelectedWard(null);
        props?.setData((prevData) => ({
            ...prevData,
            districtCode: newValue?.districtCode || null,
            districtName: newValue?.districtName || null,
            wardCode: null
        }));

        setWards(newValue ? newValue.wardSet : []);
    };
    const handleWardChange = (newValue) => {
        setSelectedWard(newValue);
        props?.setData((prevData) => ({
            ...prevData,
            wardCode: newValue?.wardCode || null,
            wardName: newValue?.wardName || null
        }));
    };

    return (
        <>
            <div className="container__address">
                <Autocomplete
                    name="provinceCode"
                    className="input__address"
                    value={selectedProvince}
                    onChange={handleProvinceChange}
                    options={provinces}
                    getOptionLabel={(option) => option.provinceName}
                    noOptionsText={'Không có kết quả phù hợp'}
                    renderInput={(params) => (
                        <TextField
                            {...params}
                            label={t('label.selectProvince')}
                            InputLabelProps={{
                                shrink: false,
                                style: {
                                    display: params.inputProps?.value ? 'none' : 'block'
                                }
                            }}
                        />
                    )}
                />

                <Autocomplete
                    className="input__address"
                    name="districtCode"
                    value={selectedDistrict}
                    onChange={handleDistrictChange}
                    options={districts}
                    getOptionLabel={(option) => option.districtName}
                    noOptionsText={'Không có kết quả phù hợp'}
                    renderInput={(params) => (
                        <TextField
                            {...params}
                            label={t('label.selectDistrict')}
                            InputLabelProps={{
                                shrink: false,
                                style: {
                                    display: params.inputProps?.value ? 'none' : 'block'
                                }
                            }}
                        />
                    )}
                />
                <Autocomplete
                    className="input__address"
                    name="wardCode"
                    value={selectedWard}
                    onChange={(event, newValue) => handleWardChange(newValue)}
                    options={wards}
                    getOptionLabel={(option) => option.wardName}
                    noOptionsText={'Không có kết quả phù hợp'}
                    renderInput={(params) => (
                        <TextField
                            {...params}
                            label={t('label.selectWard')}
                            InputLabelProps={{
                                shrink: false,
                                style: {
                                    display: params.inputProps?.value ? 'none' : 'block'
                                }
                            }}
                        />
                    )}
                />
            </div>
        </>
    );
}
