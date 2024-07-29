import { useEffect, useState } from 'react';
import './FavoritesPage.scss';

import wishAPI from '~/services/apis/clientAPI/clientWishAPI';
import formatPrice from '~/utils/formatPrice';
import { useNavigate } from 'react-router-dom';
import FramePage from '~/components/FramePage/FramePage';
import FavoriteOutlinedIcon from '@mui/icons-material/FavoriteOutlined';
import LinearProgress from '@mui/material/LinearProgress';
import { transLateListTitle } from '~/services/thirdPartyAPI/translateAPI';
import { useSnackbar } from 'notistack';
import AOS from 'aos';
import 'aos/dist/aos.css';
import { t } from 'i18next';
import emtyIcon from '~/assets/img/empty-favorite.jpg';
AOS.init();

const FavoritesPage = () => {
    const [listDataFavorites, setListDataFavorites] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    const { enqueueSnackbar } = useSnackbar();

    useEffect(() => {
        wishAPI.getAllFavoritesRoom().then(async (dataResponse) => {
            const data = await Promise.all(
                dataResponse.data.content.flatMap((item) => {
                    return transLateListTitle(item);
                })
            );
            setListDataFavorites(data);

            setLoading(false);
        });
    }, []);

    const handleLinkToDetail = (idRoom) => {
        navigate(`/room-detail/${idRoom}`);
    };

    const handleDelete = (index) => {
        const newList = listDataFavorites.filter((_, i) => i !== index);
        setListDataFavorites(newList);
    };

    const handleFavorite = (idHome, index) => {
        wishAPI.likeFavoriteRoom(idHome).then((res) => {
            enqueueSnackbar(res.data.message, { variant: 'success' });
            handleDelete(index);
        });
    };

    return (
        <FramePage>
            <div className="favorites__page">
                {loading ? (
                    <div className="no__favorites">
                        <h1>{t('title.love')}</h1>
                        <h2>{t('title.firstLove')}</h2>
                        <p>{t('contentMain.love')}</p>
                        <LinearProgress />
                    </div>
                ) : listDataFavorites.length === 0 ? (
                    <div className="empty__favorites">
                        <img src={emtyIcon} className="empty__favorites__image" />
                        <span className="empty__favorites__title-empty">Danh sách rỗng</span>
                        <span className="empty__favorites__content-empty">
                            Bạn không có homestay nào trong danh sách yêu thích
                        </span>
                    </div>
                ) : (
                    <div className="yes__favorites">
                        <h1>{t('title.love')}</h1>
                        <div
                            data-aos="fade-up"
                            data-aos-duration="1200"
                            data-aos-easing="ease-in-out"
                            data-aos-mirror="true"
                            data-aos-once="false"
                            data-aos-anchor-placement="top-center"
                        >
                            <div className="row">
                                {listDataFavorites?.map((room, index) => (
                                    <div className="col l-4" key={index}>
                                        <div className="card-item__favorite paper">
                                            <div
                                                className="content__favorite"
                                                onClick={() => handleLinkToDetail(room?.id)}
                                            >
                                                <img src={room?.imageAccomsUrls[0]} alt="" />
                                                <span className="name__favorite">{room?.accomName}</span>
                                                <p className="price__favorite">{`${t('label.pricelove')} ${formatPrice(
                                                    room?.pricePerNight
                                                )}`}</p>
                                                <p className="book-now">{t('common.bookRightNow')}</p>
                                            </div>
                                            <div
                                                className="card-like"
                                                onClick={() => {
                                                    handleFavorite(room?.id, index);
                                                }}
                                            >
                                                <FavoriteOutlinedIcon className={'icon_love__true'} />
                                                <p style={{ fontWeight: 300 }}>{t('common.unlove')} </p>
                                            </div>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </FramePage>
    );
};

export default FavoritesPage;
