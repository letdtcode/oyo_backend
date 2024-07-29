import { useNavigate, useParams } from 'react-router-dom';
import './CountRoomSetting.scss';
import publicAccomPlaceAPI from '~/services/apis/publicAPI/publicAccomPlaceAPI';
import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import CountRoomDetailSetting from '~/pages/partner/ManagerRoom/CountRoomSetting/CountRoomDetailSetting/CountRoomDetailSetting';

const CountRoomSetting = ({ accomId }) => {
    const navigate = useNavigate();
    const [allAccomCategory, setAllAccomCategory] = useState([]);

    const {
        handleSubmit,
        register,
        setValue,
        getValues,
        formState: { isSubmitting }
    } = useForm();

    useEffect(() => {
        publicAccomPlaceAPI.getAllAccomCategoryInfo().then((res) => {
            setAllAccomCategory(res.data);
        });
    }, []);

    return (
        <div className="setting-count__room">
            <div className="header-setting__count__room">
                <h3>Thiết lập phòng</h3>
            </div>
            <div className="content-count__room">
                <CountRoomDetailSetting allAccomCategory={allAccomCategory} />
            </div>
        </div>
    );
};

export default CountRoomSetting;
