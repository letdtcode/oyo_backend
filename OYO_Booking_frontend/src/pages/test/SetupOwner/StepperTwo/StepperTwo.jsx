import React, { useEffect, useState } from 'react';
import { t } from 'i18next';

import ConfirmClose from '~/components/ConfirmClose/ConfirmClose';
import CountNumber from '~/components/CountNumber/CountNumber';

import publicAccomPlaceAPI from '~/services/apis/publicAPI/publicAccomPlaceAPI';
import CustomInput from '~/assets/custom/CustomInput';
import MenuItem from '@mui/material/MenuItem';
import './StepperTwo.scss';

const StepperTwo = (props) => {
    const [data, setData] = useState([]);
    useEffect(() => {
        publicAccomPlaceAPI.getRoomCategory().then((dataResponse) => {
            setData(dataResponse?.data);
        });
    }, []);
    const onChangeCateAccom = (event) => {
        props.setAccomCate(event.target.value);
    };
    return (
        <div className="step-two">
            <div className="row">
                <div className="col l-6 m-6">
                    <div className="require-step2"></div>
                </div>
                <div className="col l-6 m-6">
                    <div className="info-count__room">
                        <CustomInput
                            className="cateName"
                            select={true}
                            size="small"
                            id="cateName"
                            value={props.accomCate}
                            onChange={onChangeCateAccom}
                            title={t(`title.category`)}
                            width={520}
                            content={data.map((cate, index) => (
                                <MenuItem key={index} value={cate.accomCateName}>
                                    {cate.accomCateName}
                                </MenuItem>
                            ))}
                        ></CustomInput>
                        <div className="count tenant">
                            <p>{t('setupOwner.client')}</p>
                            <input
                                value={props.countGuest}
                                type="number"
                                className="input__count_guest"
                                min={1}
                                onChange={(e) => {
                                    const newValue = parseInt(e.target.value, 10);
                                    if (newValue >= 1) {
                                        props.setCountGuest(newValue);
                                    }
                                }}
                            />
                        </div>
                        {/* {props.dataStep2?.map((room, index) => (
                            <div key={index}>
                                <div className="count ">
                                    <p>{room.name}</p>
                                    <CountNumber
                                        keyType={room.key}
                                        data={props.dataStep2}
                                        setData={props.setDataStep2}
                                        number={room.number}
                                    />
                                </div>
                            </div>
                        ))} */}
                        {props.dataStep2?.map((room, index) => (
                            <div key={index}>
                                <div className="count">
                                    <p>{room.name}</p>
                                    <input
                                        value={room.number}
                                        type="number"
                                        className="input__count_guest"
                                        min={1}
                                        onChange={(e) => {
                                            const newValue = parseInt(e.target.value, 10);
                                            if (newValue >= 0 ) {
                                                const newDataStep2 = [...props.dataStep2];
                                                newDataStep2[index] = { ...room, number: newValue };
                                                props.setDataStep2(newDataStep2);
                                            }
                                        }}
                                    />
                                </div>
                            </div>
                        ))}
                    </div>
                    <ConfirmClose />
                </div>
            </div>
        </div>
    );
};

export default StepperTwo;
