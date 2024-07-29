import './SliderListAccomWaiting.scss';
import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import DeleteOutlinedIcon from '@mui/icons-material/DeleteOutlined';
import defaultHotelImage from '~/assets/img/defaultHotelImage.png';
import { IconButton } from '@mui/material';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import { useSnackbar } from 'notistack';

export default function SliderListAccomWaiting({ accomWaiting, setAccomWaiting }) {
    const [settings, setSettings] = useState({
        infinite: true,
        speed: 500,
        slidesToShow: 3,
        slidesToScroll: 1
    });
    const { enqueueSnackbar } = useSnackbar();

    useEffect(() => {
        if (accomWaiting.length < 3 && accomWaiting.length > 0) {
            setSettings({
                ...settings,
                slidesToShow: accomWaiting.length
            });
        }
    }, [accomWaiting]);
    const handleDelete = (accomId) => {
        partnerManageAccomAPI.deleteAccomWaiting(accomId).then((res) => {
            if (res.statusCode === 200) {
                enqueueSnackbar('Xóa chỗ nghỉ thành công', { variant: 'success' });
                setAccomWaiting(accomWaiting.filter((item) => item.accomId !== accomId));
            } else {
                enqueueSnackbar('Xóa chỗ nghỉ thất bại', { variant: 'error' });
            }

        })
    };

    return (
        <div className="slider-list-accom-waiting">
            <div className="slider-list-accom-waiting__content">
                <Slider {...settings}>
                    {accomWaiting.map((item, index) => (
                        <div key={index} className="slider-list-accom-waiting__item">
                            <div className="slider-list-accom-waiting__item__image">
                                <img src={item?.logo ? item?.logo : defaultHotelImage} alt="accom" className="image" />
                            </div>
                            <div className="slider-list-accom-waiting__item__overlay">
                                <div className="slider-list-accom-waiting__item__overlay__progress">
                                    {item.progress}%
                                </div>
                                <IconButton
                                    onClick={() => handleDelete(item.accomId)}
                                    className="slider-list-accom-waiting__item__overlay__delete"
                                >
                                    <DeleteOutlinedIcon />
                                </IconButton>
                                <Link
                                    to={`createHotel/generalInfo/${item.accomId}`}
                                    className="slider-list-accom-waiting__item__overlay__button"
                                >
                                    Tiếp tục
                                </Link>
                            </div>
                        </div>
                    ))}
                </Slider>
            </div>
        </div>
    );
}
