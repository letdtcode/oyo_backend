import React from 'react';
import SkeletonRoomItem from '~/components/Skeleton/SkeletonRoomItem';
import Slider from 'react-slick';
import FmdGoodIcon from '@mui/icons-material/FmdGood';
import { t } from 'i18next';
import iconStar from '~/assets/svg/star.svg';
import formatPrice from '~/utils/formatPrice';
import './HomestayRecommend.scss';
import { useNavigate } from 'react-router-dom';

const HomestayRecommend = ({ homestayRecommends }) => {
    const navigate = useNavigate();
    const settings = {
        dots: true,
        fade: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1
    };
    const stars = (gradeRate) => {
        const stars = [];
        for (let i = 0; i < gradeRate; i++) {
            stars.push(<img key={i} src={iconStar} alt="icon__star" className="star" />);
        }
        return stars;
    };

    const handleLinkToDetail = (idRoom) => {
        navigate(`/room-detail/${idRoom}`);
    };
    return (
        <div className="homestay__recommend">
            <h1>{t('title.homestayRecommend')}</h1>
            <div className="row">
                {homestayRecommends.map((home, index) => (
                    <div className="col l-3 m-6 c-12 " key={index}>
                        <div className="container__homestay__recommend paper">
                            <Slider {...settings}>
                                {home?.imageAccomsUrls.length !== 0 &&
                                    home?.imageAccomsUrls?.map((image, index) => (
                                        <div key={index} onClick={() => handleLinkToDetail(home?.accomId)}>
                                            <img src={image} alt="room_hot" className="image-home" />
                                        </div>
                                    ))}
                            </Slider>

                            <div className="info__room">
                                <h2 onClick={() => handleLinkToDetail(home?.accomId)}>{home?.accomName}</h2>
                                <div className="obility__room">
                                    <p>{home.accomCateName}</p> {stars(home?.gradeRate)}
                                </div>
                                <div className="locate__room">
                                    <FmdGoodIcon className="icon_locate" />
                                    <span>{home?.addressGeneral ? home?.addressGeneral : null}</span>
                                </div>
                                <div className="price__room">
                                    <span className="price__room__value">{`${formatPrice(home?.pricePerNight)}`}</span>
                                    <span>{`${t('numberCount.priceDay')}`}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default HomestayRecommend;
