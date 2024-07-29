import { useEffect, useState } from 'react';
import { useSnackbar } from 'notistack';
import { useNavigate } from 'react-router-dom';
import FramePage from '~/components/FramePage/FramePage';
import FmdGoodIcon from '@mui/icons-material/FmdGood';
import formatPrice from '~/utils/formatPrice';
import { guests } from '~/utils/formatForm';
import { dayGap } from '~/utils/calculates';
import CheckBoxPaymentPolicy from '~/components/CheckBoxPayment/CheckBoxPaymentPolicy';
import CheckBoxPaymentMethod from '~/components/CheckBoxPayment/CheckBoxPaymentMethod';
import InfoUserBooking from '~/components/InfoUserBooking/InfoUserBooking';
import DateBooking from '~/components/DateBooking/DateBooking';
import publicAccomPlaceAPI from '~/services/apis/publicAPI/publicAccomPlaceAPI';
import bookingAPI from '~/services/apis/clientAPI/clientBookingAPI';
import { useDispatch, useSelector } from 'react-redux';
import { validateBooking } from '~/utils/validate';
import './BookingPage.scss';
import { t } from 'i18next';
import bookingSlice from '~/redux/bookingSlice';
import globalSlice from '~/redux/globalSlice';
import { transLateRoom } from '~/services/thirdPartyAPI/translateAPI';
import { showRefundPolicy } from '~/utils/showRefundPolicy';
import CountDownTimer from '~/components/CountDownTimer/CountDownTimer';
import ModalTimeUp from '~/components/ModalTimeUp/ModalTimeUp';

const BookingPage = () => {
    const dispatch = useDispatch();
    const { enqueueSnackbar } = useSnackbar();
    const navigate = useNavigate();

    const dataBooking = useSelector((state) => state.booking.info);
    const [loading, setLoading] = useState(true);
    const [dataDetailHomeBooking, setDataDetailHomeBooking] = useState();
    const pricePerNightOrigin = dataDetailHomeBooking?.pricePerNight;
    const pricePerNightCurrent = dataDetailHomeBooking?.pricePerNight * (1 - dataDetailHomeBooking?.discount);
    const discount = dataDetailHomeBooking?.discount;
    const priceCustomForAccomList = dataBooking?.priceCustomForAccomList;
    const countDateBookingHaveDefaultPrice =
        dayGap({
            start: dataBooking.checkIn,
            end: dataBooking.checkOut
        }) - priceCustomForAccomList.length;
    const costRentHomestay =
        priceCustomForAccomList.reduce((total, item) => total + item.priceApply, 0) +
        pricePerNightCurrent * countDateBookingHaveDefaultPrice;
    const costDiscountHomestay = pricePerNightOrigin * discount * countDateBookingHaveDefaultPrice;
    const costDiscountForPayment =
        dataBooking.paymentMethod === 'DIRECT' ? 0 : (costRentHomestay + dataBooking.surcharge) * 0.1;
    const totalBill = costRentHomestay + dataBooking.surcharge - costDiscountForPayment;
    const totalTransfer = dataBooking?.paymentPolicy === 'PAYMENT_FULL' ? totalBill : totalBill * 0.5;
    const [errors, setErrors] = useState({});

    // 15 phút
    const TIME_COUNT_DOWN = 15 * 60 * 1000;
    const [targetTimeCountDown, setTargetTimeCountDown] = useState(new Date().getTime() + TIME_COUNT_DOWN);
    const [open, setOpen] = useState(false);

    const handleContinueBooking = async () => {
        setTargetTimeCountDown(new Date().getTime() + TIME_COUNT_DOWN);
        setOpen(false);
        // navigate('/booking');
    };

    const handleShowModalTimeUp = () => {
        setOpen(true);
    };

    const handleBookingRoom = () => {
        let idAccom = dataBooking.accomId;
        setErrors({});
        const checkValidate = validateBooking(dataBooking);
        if (Object.keys(checkValidate).length === 0) {
            dispatch(globalSlice.actions.setLoading(true));
            const bookingRequest = {
                nameCustomer: dataBooking.nameCustomer,
                phoneNumberCustomer: dataBooking.phoneNumberCustomer,
                checkIn: dataBooking.checkIn,
                checkOut: dataBooking.checkOut,
                numAdult: dataBooking.numAdult,
                numChild: dataBooking.numChild,
                numBornChild: dataBooking.numBornChild,
                paymentPolicy: dataBooking.paymentPolicy,
                paymentMethod: dataBooking.paymentMethod,
                accomId: dataBooking.accomId
            };

            bookingAPI.createBooking(bookingRequest).then((dataResponse) => {
                // console.log(dataResponse);
                window.open(dataResponse.data.bookingPaypalCheckoutLink, 'haha', 'width=500,height=800');
                // if (dataResponse?.statusCode === 200) {
                //     enqueueSnackbar(t('message.bookingSuccess'), { variant: 'success' });
                //     dispatch(bookingSlice.actions.clearInfoBooking());
                //     dispatch(globalSlice.actions.setLoading(false));
                //     navigate(`/room-detail/${idAccom}`);
                // } else {
                //     enqueueSnackbar(t('message.bookingFail'), { variant: 'error' });
                // }
            });
        } else {
            setErrors(checkValidate);
        }
    };
    useEffect(() => {
        const checkValidate = validateBooking(dataBooking);
        setErrors(checkValidate);
    }, [dataBooking.phoneNumberCustomer]);

    useEffect(() => {
        setLoading(true);
        publicAccomPlaceAPI.getRoomDetail(dataBooking.accomId).then(async (dataResponse) => {
            const data = await transLateRoom(dataResponse.data);
            setDataDetailHomeBooking(data);
            setLoading(false);
        });
    }, []);
    useEffect(() => {
        const dataCheck = {
            checkIn: dataBooking.checkIn,
            checkOut: dataBooking.checkOut,
            accomId: dataBooking.accomId,
            numAdult: dataBooking.numAdult
        };
        publicAccomPlaceAPI.checkBooking(dataCheck).then((response) => {
            dispatch(bookingSlice.actions.updateInfoBooking(response.data));
        });
    }, [dataBooking.checkIn, dataBooking.checkOut]);

    return (
        <FramePage>
            <ModalTimeUp open={open} handleContinueBooking={handleContinueBooking} />
            <CountDownTimer targetDate={targetTimeCountDown} handleShowModalTimeUp={handleShowModalTimeUp} />
            <div className="booking__page content">
                <div className="content-booking">
                    <h1>{t('title.bookingOfYou.tilte')}</h1>
                    <div className="row">
                        <div className="col l-8" style={{ paddingRight: '50px' }}>
                            <h2>{t('title.bookingOfYou.drive')}</h2>
                            <DateBooking
                                size="horizontal"
                                checkIn={dataBooking.checkIn}
                                checkOut={dataBooking.checkOut}
                                idHome={dataBooking.accomId}
                            />
                            <hr className="line" />

                            <div className="count-customer">
                                <div>
                                    <p className="customer-count__title">{t('title.bookingOfYou.client')}</p>
                                    <p className="count">{guests(dataBooking)}</p>
                                </div>
                            </div>
                            <InfoUserBooking />
                            {errors?.phoneNumberCustomer && <p className="error">{errors.phoneNumberCustomer}</p>}
                            <hr className="line" />
                            <div className="count-customer">
                                <div>
                                    <p className="customer-count__title">{t('title.bookingOfYou.payOnline')}</p>
                                    <p className="count">{`${t('title.bookingOfYou.payBefore')}: ${formatPrice(
                                        totalTransfer
                                    )}`}</p>
                                </div>
                            </div>

                            <hr className="line" />

                            <div style={{ display: 'flex', justifyContent: 'flex-start' }}>
                                <div style={{ width: 'fit-content', marginRight: 125 }}>
                                    <p style={{ fontSize: 14, fontWeight: 'bold' }}>
                                        {t('title.bookingOfYou.paymentMethod')}
                                    </p>
                                    <CheckBoxPaymentMethod paymentMethod={dataBooking.paymentMethod} />
                                </div>

                                <div style={{ width: 'fit-content' }}>
                                    <p style={{ fontSize: 14, fontWeight: 'bold' }}>
                                        {t('title.bookingOfYou.paymentPolicy')}
                                    </p>
                                    <CheckBoxPaymentPolicy paymentPolicy={dataBooking.paymentPolicy} />
                                </div>
                            </div>
                            <button
                                disabled={Object.keys(errors).length !== 0 || !dataBooking.canBooking}
                                onClick={handleBookingRoom}
                            >
                                {t('common.booking')}
                            </button>

                            {/* {dataBooking.paymentMethod === 'PAYPAL' ? (
                                <div className="payment__paypal"></div>
                            ) : dataBooking.paymentMethod === 'VNPAY' ? (
                                <div className="btn__booking">
                                    <VNPay
                                        pricePayment={totalTransfer}
                                        booking={handleBookingRoom}
                                        canBooking={dataBooking.canBooking}
                                        errors={errors}
                                    />
                                </div>
                            ) : (
                                <div className="btn__booking">
                                    <button
                                        disabled={Object.keys(errors).length !== 0 || !dataBooking.canBooking}
                                        onClick={handleBookingRoom}
                                    >
                                        {t('common.booking')}
                                    </button>
                                </div>
                            )} */}
                        </div>
                        <div className="col l-4">
                            <div className="card-booking__room paper">
                                {loading ? (
                                    <></>
                                ) : (
                                    <>
                                        <div className="header-room__booking">
                                            <div className="image-room__booking">
                                                <img src={dataDetailHomeBooking?.imageAccomsUrls[0]} alt="" />
                                            </div>
                                            <div className="desc-room__booking">
                                                <p className="desc-all">{t('title.bookingOfYou.fullHome')}</p>
                                                <p className="name-room-booking">
                                                    {dataDetailHomeBooking?.accomCateName}
                                                </p>
                                                <div className="locate-room-booking">
                                                    <p>
                                                        <FmdGoodIcon className="icon-locate-booking" />
                                                        {dataDetailHomeBooking.addressDetail}
                                                    </p>
                                                </div>
                                                <p className="name-host-room">{`${t('title.bookingOfYou.owner')} ${
                                                    dataDetailHomeBooking?.nameHost
                                                }`}</p>
                                            </div>
                                        </div>
                                        <hr className="line-card" />
                                        <div className="policy-booking">
                                            <a>{t('title.bookingOfYou.cancelPolicy')}</a>
                                            <a>
                                                {showRefundPolicy({
                                                    cancellationPolicy: dataDetailHomeBooking.cancellationPolicy,
                                                    cancellationFeeRate: dataDetailHomeBooking.cancellationFeeRate
                                                })}
                                            </a>
                                        </div>

                                        <hr className="line-card" />
                                        <div className="price-booking">
                                            <div className="price-room-booking">
                                                <p style={{ color: '#757575' }}>{t('title.bookingOfYou.price')}</p>
                                                <p style={{ fontWeight: '550' }}>
                                                    {formatPrice(costRentHomestay)}/
                                                    {dayGap({
                                                        start: dataBooking.checkIn,
                                                        end: dataBooking.checkOut
                                                    })}{' '}
                                                    {t('title.bookingOfYou.day')}
                                                </p>
                                            </div>
                                        </div>
                                        <div className="card-surcharge">
                                            <p>
                                                {t('title.bookingOfYou.surcharges')}: {dataBooking.surcharge}
                                            </p>
                                            {dataDetailHomeBooking?.surchargeList?.map((sur, index) => (
                                                <li key={index}>
                                                    {sur?.surchargeName}: {formatPrice(sur.cost)}
                                                </li>
                                            ))}
                                        </div>
                                        <div className="price-booking">
                                            {dataBooking.discount !== 0 && (
                                                <div className="price-total-booking">
                                                    <p style={{ color: '#757575' }}>
                                                        {t('common.discountFrom')} {dataDetailHomeBooking.accomCateName}
                                                    </p>
                                                    <p style={{ fontWeight: '300' }}>
                                                        {`-` + formatPrice(costDiscountHomestay)}
                                                    </p>
                                                </div>
                                            )}
                                            <div className="price-total-booking">
                                                <p style={{ color: '#757575' }}>
                                                    {dataBooking.paymentMethod === 'DIRECT'
                                                        ? t('title.bookingOfYou.direct')
                                                        : dataBooking.paymentMethod === 'PAYPAL'
                                                        ? t('title.bookingOfYou.paypal')
                                                        : t('title.bookingOfYou.vnPay')}
                                                </p>
                                                <p style={{ fontWeight: '300' }}>
                                                    {formatPrice(costDiscountForPayment)}
                                                </p>
                                            </div>

                                            <div className="price-total-booking">
                                                <p style={{ color: '#757575' }}>{t('title.bookingOfYou.totalPrice')}</p>
                                                <p style={{ fontWeight: '550' }}>{formatPrice(totalBill)}</p>
                                            </div>
                                        </div>
                                    </>
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </FramePage>
    );
};

export default BookingPage;
