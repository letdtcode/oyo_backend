import { useEffect, useState } from 'react';
import Slider from 'react-slick';
import './RoomPopular.scss';
import FmdGoodIcon from '@mui/icons-material/FmdGood';
import { useNavigate } from 'react-router-dom';
import SkeletonRoomItem from '../Skeleton/SkeletonRoomItem';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import IconLove from './IconLove';
import publicAccomPlaceAPI from '../../services/apis/publicAPI/publicAccomPlaceAPI';
import wishAPI from '~/services/apis/clientAPI/clientWishAPI';
import formatPrice from '~/utils/formatPrice';
import iconStar from '~/assets/svg/star.svg';
import { t } from 'i18next';
import numViewIcon from '~/assets/img/icons8-eye-64.png';
import { transLateListTitle } from '~/services/thirdPartyAPI/translateAPI';

export default function RoomPopular() {
    const settings = {
        dots: true,
        fade: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1
    };
    const navigate = useNavigate();
    const [loading, setLoading] = useState(true);
    const [listHome, setListHome] = useState();

    const stars = (gradeRate) => {
        const stars = [];
        for (let i = 0; i < gradeRate; i++) {
            stars.push(<img key={i} src={iconStar} alt="icon__star" className="star" />);
        }
        return stars;
    };
    useEffect(() => {
        setLoading(true);
        publicAccomPlaceAPI.getTophome({ number: 0, size: 8 }).then(async (dataResponse) => {
            const data = await Promise.all(
                dataResponse.data.content.flatMap(async (item) => {
                    item.isFavorite = await wishAPI.checkWish(item.id);
                    return transLateListTitle(item);
                })
            );
            setListHome(dataResponse.data);
            setLoading(false);
        });
    }, []);
    const handleLinkToDetail = (idRoom) => {
        navigate(`/room-detail/${idRoom}`);
    };
    return (
        <div className="room__popular">
            <h1>{t('title.popularRoom')}</h1>
            <div className="row">
                {loading ? (
                    <SkeletonRoomItem />
                ) : (
                    listHome.content?.map((home, index) => (
                        <div className="col l-3 m-6 c-12 " key={index}>
                            <div className="container__room__popular paper">
                                <Slider {...settings}>
                                    {home?.imageAccomsUrls.length !== 0 &&
                                        home?.imageAccomsUrls?.map((image, index) => (
                                            <div key={index} onClick={() => handleLinkToDetail(home?.id)}>
                                                <img src={image} alt="room_hot" className="image-home" />
                                            </div>
                                        ))}
                                </Slider>
                                {home.isFavorite !== null && (
                                    <IconLove idHome={home?.id} isFavorite={home?.isFavorite} />
                                )}

                                <div className="info__room">
                                    <h2 onClick={() => handleLinkToDetail(home?.id)}>{home?.accomName}</h2>
                                    <div className="obility__room">
                                        <p>{home.accomCateName}</p> {stars(home?.gradeRate)}
                                    </div>
                                    <div className="locate__room">
                                        <FmdGoodIcon className="icon_locate" />
                                        <span>{home?.addressGeneral ? home?.addressGeneral : null}</span>
                                    </div>
                                    <div className="price__room">
                                        <span style={{ display: 'inline-block' }}>
                                            <span className="price__room__value">{`${formatPrice(
                                                home?.pricePerNight
                                            )}`}</span>
                                            <span>{`${t('numberCount.priceDay')}`}</span>
                                        </span>
                                        <span style={{ display: 'flex', alignContent: 'space-between' }}>
                                            <img
                                                style={{ width: 17, height: 17 }}
                                                src={numViewIcon}
                                                alt={`${t('numberCount.viewInDetail')}`}
                                            />
                                            <span style={{ marginLeft: 3 }}> {home?.numView}</span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}
