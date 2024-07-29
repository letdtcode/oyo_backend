import './RoomSetting.scss';
import { t } from 'i18next';
import { useEffect, useState } from 'react';
import { typeRoom } from '~/models/roomHome';
import publicAccomPlaceAPI from '~/services/apis/publicAPI/publicAccomPlaceAPI';
import CustomInput from '~/assets/custom/CustomInput';
import MenuItem from '@mui/material/MenuItem';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import { roomHomeFormData } from '~/models/roomHome';

export default function RoomSetting({ id, save, doneSave }) {
    const [data, setData] = useState(roomHomeFormData);
    const [dataStep2, setDataStep2] = useState(typeRoom);
    const [countGuest, setCountGuest] = useState(1);
    const [loading, setLoading] = useState(false);
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        publicAccomPlaceAPI.getRoomCategory().then((dataResponse) => {
            setCategories(dataResponse?.data);
        });
        if (id) {
            partnerManageAccomAPI.getRoomSetting(id).then((res) => {
                setData({ ...data, ...res.data, numBedRoom: res.data.bedRooms.total });
                setLoading(false);
            });
        }
    }, []);
    useEffect(() => {
        if (save) {
            const tmpdata = {
                typeBedCodes: Array.from({ length: data.numBedRoom }, () => 'TYPE_BED_001'),
                numBathRoom: data.numBathRoom,
                numKitchen: data.numKitchen,
                accomCateName: data.accomCateName,
                numPeople: data.numPeople
            };
            partnerManageAccomAPI
                .updateRoomSetting({ id, data: tmpdata })
                .then((res) => {
                    doneSave(true);
                })
                .catch(() => {
                    doneSave(false);
                });
        }
    }, [save]);
    const onChangeData = (event) => {
        setData({ ...data, [event.target.name]: event.target.value });
    };
    return (
        <div className="room-setting">
            <div className="info-count__room">
                <CustomInput
                    className="accomCateName"
                    name="accomCateName"
                    select={true}
                    size="small"
                    value={data.accomCateName}
                    onChange={onChangeData}
                    title={t(`title.category`)}
                    width={520}
                    content={categories.map((cate, index) => (
                        <MenuItem key={index} value={cate.accomCateName}>
                            {cate.accomCateName}
                        </MenuItem>
                    ))}
                ></CustomInput>
                <div className="count tenant">
                    <p>{t('setupOwner.client')}</p>
                    <input
                        value={data?.numPeople || 0}
                        type="number"
                        name="numPeople"
                        className="input__count_guest"
                        min={1}
                        onChange={(e) => {
                            const newValue = parseInt(e.target.value, 10);
                            if (newValue >= 1) {
                                onChangeData(e);
                            }
                        }}
                    />
                </div>

                {dataStep2?.map((room, index) => (
                    <div key={index}>
                        <div className="count">
                            <p>{room.name}</p>
                            <input
                                value={data[room.key] || 0}
                                name={room.key}
                                type="number"
                                className="input__count_guest"
                                min={1}
                                onChange={(e) => {
                                    const newValue = parseInt(e.target.value, 10);
                                    if (newValue >= 0) {
                                        onChangeData(e);
                                    }
                                }}
                            />
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}
