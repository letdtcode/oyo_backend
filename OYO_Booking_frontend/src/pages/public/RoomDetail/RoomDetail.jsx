import './RoomDetail.scss';
import { t } from 'i18next';
import moment from 'moment';
import format from 'date-fns/format';
import FavoriteBorderOutlinedIcon from '@mui/icons-material/FavoriteBorderOutlined';
import FavoriteIcon from '@mui/icons-material/Favorite';
import FmdGoodOutlinedIcon from '@mui/icons-material/FmdGoodOutlined';
import { Button } from '@mui/material';
import { useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useSnackbar } from 'notistack';
import { addDays } from 'date-fns';
import iconStar from '~/assets/svg/star.svg';
import ListImage from '~/components/ListImage/ListImage';
import Facility from './Facility/Facility';
import DialogConvenient from '~/components/DialogConvenient/DialogConvenient';
import DrawerHome from '~/components/DrawerHome/DrawerHome';
import DateRangeSelector from '~/components/DateRangeSelector/DateRangeSelector';
import Dropdown from '~/components/Dropdown/Dropdown';
import NecessaryInformation from './NecessaryInformation/NecessaryInformation';
import GeneralPolicy from './GeneralPolicy/GeneralPolicy';
import CommentReview from '~/components/CommentReview/CommentReview';
import FramePage from '~/components/FramePage/FramePage';
import DateIsBooking from '~/components/DateIsBooking/DateIsBooking';
import publicAccomPlaceAPI from '~/services/apis/publicAPI/publicAccomPlaceAPI';
import SkeletonRoomDetail from '~/components/Skeleton/SkeletonRoomDetail';
import formatPrice from '~/utils/formatPrice';
import wishAPI from '~/services/apis/clientAPI/clientWishAPI';
import bookingSlice from '~/redux/bookingSlice';
import { guestsModel } from '~/models/booking';
import { transLateRoom } from '~/services/thirdPartyAPI/translateAPI';
import { dayGap } from '~/utils/calculates';
import GoogleMap from '~/components/GoogleMap/GoogleMap';
import PopoverPrice from '~/components/PopoverPrice/PopoverPrice';
import RemoveRedEyeOutlinedIcon from '@mui/icons-material/RemoveRedEyeOutlined';

export default function RoomDetail() {
    const { enqueueSnackbar } = useSnackbar();
    const navigate = useNavigate();
    const roomId = useParams();
    const dispatch = useDispatch();
    const user = useSelector((state) => state.user.current);
    const [loading, setLoading] = useState(true);
    const [dataDetailHome, setDataDetalHome] = useState('');
    const [guests, setGuests] = useState(guestsModel);
    const [dateBook, setDateBook] = useState([
        moment().format('DD/MM/yyyy'),
        moment(addDays(new Date(), 1)).format('DD/MM/yyyy')
    ]);

    const [priceCustomForAccomList, setPriceCustomForAccomList] = useState([]);
    const [disBooking, setDisBooking] = useState(true);
    const [love, setLove] = useState(null);
    const [openDrawer, setOpenDrawer] = useState(false);
    const [dataComment, setDataComment] = useState([]);

    const pricePerNightOrigin = dataDetailHome?.pricePerNight;
    const pricePerNightCurrent = dataDetailHome?.pricePerNight * (1 - dataDetailHome?.discount);
    const discount = dataDetailHome?.discount * 100;
    const dayGapBooking =
        dayGap({
            start: dateBook[0],
            end: dateBook[1]
        }) - priceCustomForAccomList?.length;
    const costRentHomestay =
        priceCustomForAccomList?.reduce((total, item) => total + item.priceApply, 0) +
        pricePerNightCurrent * dayGapBooking;

    const surcharge = dataDetailHome?.surchargeList
        ? dataDetailHome?.surchargeList.reduce((total, item) => total + item.cost, 0)
        : 0;

    const stars = [];

    for (let i = 0; i < dataDetailHome.gradeRate; i++) {
        stars.push(<img key={i} src={iconStar} alt="icon__star" className="star" />);
    }
    useEffect(() => {
        setLoading(true);
        wishAPI.checkWish(roomId.id).then((res) => setLove(res));
        publicAccomPlaceAPI.getReviewHome(roomId.id).then((res) => {
            setDataComment(res.data);
        });
        publicAccomPlaceAPI.getRoomDetail(roomId.id).then(async (dataResponse) => {
            const data = await transLateRoom(dataResponse.data);
            setDataDetalHome(data);
            setLoading(false);
        });
    }, [roomId?.id]);
    useEffect(() => {
        const dataCheck = {
            checkIn: dateBook[0],
            checkOut: dateBook[1],
            accomId: roomId.id,
            numAdult: guests.numAdult
        };
        publicAccomPlaceAPI.checkBooking(dataCheck).then((response) => {
            setPriceCustomForAccomList(response?.data?.priceCustomForAccomList);
            if (response?.statusCode === 200) {
                setDisBooking(false);
            } else {
                setDisBooking(true);
            }
        });
    }, [guests.numAdult, dateBook]);

    const handleBooking = () => {
        if (user === null || user === undefined) {
            enqueueSnackbar(t('message.warningSignin'), { variant: 'warning' });
        } else {
            const dataBooking = {
                checkIn: dateBook[0],
                checkOut: dateBook[1],
                accomId: roomId.id,
                guests: guests,
                surcharge: surcharge,
                nameCustomer: user.firstName + ' ' + user.lastName,
                phoneNumberCustomer: user.phone,
                discount: dataDetailHome?.discount,
                priceCustomForAccomList: priceCustomForAccomList
            };
            dispatch(bookingSlice.actions.addInfoBooking(dataBooking));
            navigate('/booking');
        }
    };

    const handleChangeDayBooking = (startDate, endDate) => {
        const checkIn = format(startDate, 'dd/MM/yyyy');
        const checkOut = format(endDate, 'dd/MM/yyyy');
        setDateBook([checkIn, checkOut]);
    };
    const handleChangeGuests = (value) => {
        setGuests(value);
    };
    const handleLove = () => {
        wishAPI.likeFavoriteRoom(roomId.id).then((res) => {
            if (res.data.message === 'Add wish item success') {
                enqueueSnackbar(t('message.love'), { variant: 'success' });
            } else {
                enqueueSnackbar(t('message.unlove'), { variant: 'success' });
            }
            setLove(!love);
        });
    };
    return (
        <FramePage>
            {loading ? (
                <SkeletonRoomDetail />
            ) : (
                <>
                    <div className="content detail-room">
                        <div className="info-room">
                            <div className="header-room">
                                <h1>{dataDetailHome.accomName}</h1>
                                <div className="heading">
                                    <div className="heading__left">
                                        <div className="star-rating__home">{stars}</div>
                                        <div className="locate__room">
                                            <FmdGoodOutlinedIcon className="icon_locate" />
                                            <p>{dataDetailHome.addressDetail}</p>
                                        </div>
                                    </div>
                                    <div className="heading__right">
                                        <div className="card-like__container" onClick={handleLove}>
                                            {love !== null && (
                                                <div className="card-like">
                                                    {love === true ? (
                                                        <>
                                                            <FavoriteIcon className="icon_love" />
                                                            <p>{t('common.liked')}</p>
                                                        </>
                                                    ) : (
                                                        <>
                                                            <FavoriteBorderOutlinedIcon className="icon_love" />
                                                            <p>{t('common.like')}</p>
                                                        </>
                                                    )}
                                                </div>
                                            )}
                                        </div>

                                        <div className="view">
                                            <RemoveRedEyeOutlinedIcon className="view__icon" />
                                            {`${dataDetailHome?.numView} ${t('numberCount.viewInDetail')}`}
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <ListImage
                                listImage={dataDetailHome.imageAccomsUrls}
                                setOpen={setOpenDrawer}
                                cldVideoId={dataDetailHome.cldVideoId}
                            />
                            <div className="about-room">
                                <span style={{ fontWeight: '600', fontSize: 'large' }}>
                                    {dataDetailHome?.accomCateName}:{' '}
                                </span>
                                <span>
                                    {dataDetailHome.numPeople} {t('title.bookingOfYou.client')},{' '}
                                    {dataDetailHome.numBedRoom} {t('label.bedroom')}, {dataDetailHome.numKitchen}{' '}
                                    {t('label.kitchen')}, {''}
                                    {dataDetailHome.numBathRoom} {t('label.bathroom')} , {t('home.acreage')}:{' '}
                                    {dataDetailHome.acreage} m²
                                </span>

                                <div className="row">
                                    <div className="col l-8 m-7 c-12">
                                        <hr className="divider" />
                                        <h2>{t('contentMain.convenient')}</h2>
                                        <Facility data={dataDetailHome.facilityCategoryList} />
                                        <DialogConvenient listConvenient={dataDetailHome.facilityCategoryList} />
                                        <hr className="divider" />
                                        <div className=" title-room">
                                            <div className="desc-room">
                                                <h2>{t('contentMain.descHome')}</h2>
                                                <p>{dataDetailHome.description}</p>
                                                <p>
                                                    {t('home.addressDetail')}: {dataDetailHome.addressDetail}
                                                </p>
                                                <div style={{ padding: 0 }}>
                                                    <span style={{ fontWeight: 500 }}>Chủ nhà:</span>{' '}
                                                    <span>{dataDetailHome?.nameHost}</span>
                                                </div>
                                            </div>

                                            <hr className="divider" />
                                            <NecessaryInformation />
                                            <GeneralPolicy
                                                cancellationPolicy={dataDetailHome.cancellationPolicy}
                                                cancellationFeeRate={dataDetailHome.cancellationFeeRate}
                                            />
                                        </div>
                                        <DateIsBooking bookedDates={dataDetailHome.bookedDates} />
                                    </div>

                                    <div className="col l-4 m-5 c-12">
                                        <div className="card-book__detail paper">
                                            <div className="price-room">
                                                <span className="price-room__value">
                                                    {formatPrice(pricePerNightCurrent)}/
                                                </span>
                                                <span className="price-room__unit">{t('numberCount.priceDay')}</span>
                                                {dataDetailHome.discount > 0 && (
                                                    <div style={{ display: 'flex' }}>
                                                        <div className="price-room root" style={{ paddingRight: 9 }}>
                                                            <span className="price-room__value">
                                                                {formatPrice(pricePerNightOrigin)}/
                                                            </span>
                                                            <span className="price-room__unit">
                                                                {t('numberCount.priceDay')}
                                                            </span>
                                                        </div>
                                                        <div className="discount-percent">
                                                            <span>{`-${discount}%`}</span>
                                                        </div>
                                                    </div>
                                                )}
                                            </div>

                                            <DateRangeSelector
                                                dateBook={dateBook}
                                                setDataDay={handleChangeDayBooking}
                                            />
                                            <Dropdown
                                                guests={guests}
                                                setGuests={setGuests}
                                                handleChangeGuests={handleChangeGuests}
                                            />
                                            <div className="price-total">
                                                <div className="title-price">
                                                    <p className="name-surcharge" style={{ marginRight: 2 }}>
                                                        Giá phòng
                                                    </p>
                                                    <PopoverPrice
                                                        priceCustomForAccomList={priceCustomForAccomList}
                                                        discount={dataDetailHome?.discount}
                                                        pricePerNightOrigin={pricePerNightOrigin}
                                                        pricePerNightCurrent={pricePerNightCurrent}
                                                        dayGapBooking={dayGapBooking}
                                                    />
                                                </div>
                                                <div className="real-price ">
                                                    <p className="cost-surcharge">{formatPrice(costRentHomestay)}</p>
                                                </div>
                                            </div>

                                            {dataDetailHome?.surchargeList.map((sur, index) => (
                                                <div className="price-total" key={index}>
                                                    <div className="title-price">
                                                        <p className="name-surcharge">{`${sur?.surchargeName}`}</p>
                                                    </div>
                                                    <div className="real-price">
                                                        <p className="cost-surcharge">{formatPrice(sur?.cost)}</p>
                                                    </div>
                                                </div>
                                            ))}

                                            <hr style={{ width: '80%' }} />

                                            <div className="total-price">
                                                <div className="title-price">Tổng giá phòng</div>
                                                <p className="real-price ">
                                                    {formatPrice(costRentHomestay + surcharge)}
                                                </p>
                                            </div>
                                            <div className="btn-booking">
                                                <Button
                                                    variant="contained"
                                                    disabled={disBooking}
                                                    className="btn-booking-room"
                                                    onClick={handleBooking}
                                                    color="info"
                                                >
                                                    {t('common.booking')}
                                                </Button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <GoogleMap data={dataDetailHome} />

                            <CommentReview dataComment={dataComment} />
                        </div>
                        <DrawerHome
                            open={openDrawer}
                            setOpen={setOpenDrawer}
                            data={dataDetailHome}
                            stars={stars}
                            dataComment={dataComment}
                        />
                    </div>
                </>
            )}
        </FramePage>
    );
}
