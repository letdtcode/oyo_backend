import { useEffect, useState } from 'react';

import AOS from 'aos';

import 'aos/dist/aos.css';
import { t } from 'i18next';
import FmdGoodIcon from '@mui/icons-material/FmdGood';
import LinearProgress from '@mui/material/LinearProgress';
import DoneIcon from '@mui/icons-material/Done';

import FormEvaluate from '~/components/FormEvaluate/FormEvaluate';
import ModalConfirmDelete from '~/components/ModalConfirmDelete/ModalConfirmDelete';

import FramePage from '~/components/FramePage/FramePage';
import bookingAPI from '~/services/apis/clientAPI/clientBookingAPI';
import { transLateHistoryBooking } from '~/services/thirdPartyAPI/translateAPI';
import formatPrice from '~/utils/formatPrice';
import { useNavigate } from 'react-router-dom';
import { showRefundPolicy } from '~/utils/showRefundPolicy';
import './HistoryBookingPage.scss';

AOS.init();

const HistoryBookingPage = () => {
    const [dataHistory, setDataHistory] = useState([]);
    const [loading, setLoading] = useState(true);
    const [showFormReview, setShowFormReview] = useState(false);
    const [idBooking, setIdBooking] = useState('');
    const navigate = useNavigate();
    const [reload, setReload] = useState(false);
    useEffect(() => {
        bookingAPI.getHistoryBooking().then(async (dataResponse) => {
            const data = await Promise.all(
                dataResponse?.data?.content.flatMap((item) => {
                    return transLateHistoryBooking(item);
                })
            );

            setDataHistory(data);
            setLoading(false);
        });
    }, [reload]);

    const handleReview = (value) => {
        setIdBooking(value);
        setShowFormReview(true);
    };

    const handleCloseReview = () => {
        setShowFormReview(false);
    };

    const handleView = (id) => {
        navigate(`/room-detail/${id}`);
    };
    const handleReload = () => {
        setReload(!reload);
    };
    return (
        <FramePage>
            <div className="history-booking__page content">
                <h1>{t('title.history')}</h1>
                {loading && <LinearProgress />}
                <div
                    data-aos="fade-up"
                    data-aos-duration="1900"
                    data-aos-easing="ease-in-out"
                    data-aos-mirror="true"
                    data-aos-once="false"
                    data-aos-anchor-placement="top-center"
                >
                    <div className="list-booking-history">
                        {dataHistory.length === 0 && loading === false ? (
                            <div className="paper nodata">
                                <p>Bạn chưa đặt chỗ</p>
                                <img src="/src/assets/video/BookingNow.gif" className="color-filter"></img>
                            </div>
                        ) : (
                            <>
                                {dataHistory?.map((history, index) => {
                                    var status = '';
                                    if (history?.status === 'CANCELED') {
                                        status = t('contentMess.cancel');
                                    } else if (history?.status === 'CHECK_IN') {
                                        status = t('contentMess.checkin');
                                    } else if (history?.status === 'CHECK_OUT') {
                                        status = t('contentMess.checkout');
                                    }
                                    return (
                                        <div className="item__booking paper" key={index}>
                                            <div
                                                className="img-item__booking"
                                                onClick={(e) => handleView(history?.accomId)}
                                            >
                                                <img src={history.imageUrl} alt="img-booking" className="img-booking" />
                                            </div>
                                            <div className="info-history__booking">
                                                <p className="name-history__booking">{history?.accomName}</p>
                                                <p className="name-host-history__booking">{`(${t(
                                                    'title.bookingOfYou.owner'
                                                )} ${history?.fullNameHost})`}</p>
                                                <div className="locate-hictory__booking">
                                                    <FmdGoodIcon className="icon-locate-booking" />
                                                    <p>{history.generalAddress}</p>
                                                </div>
                                                <p className="guests-history___booking">{`${t('label.totalClient')} ${
                                                    history?.numAdult
                                                }`}</p>
                                                <div className="date-history__booking">
                                                    <p>{`${history?.checkIn} - ${history?.checkOut}`}</p>
                                                </div>
                                                <div className="date-history__booking">
                                                    <p>
                                                        {showRefundPolicy({
                                                            cancellationPolicy: history.cancellationPolicy,
                                                            cancellationFeeRate: history.cancellationFeeRate
                                                        })}
                                                    </p>
                                                </div>
                                            </div>
                                            <div className="price-history__booking">
                                                <div className="price-day__booking">
                                                    <p>{t('label.priceNight')}</p>
                                                    <p style={{ paddingLeft: '5px', fontWeight: '600' }}>
                                                        {formatPrice(history?.pricePerNight)}
                                                    </p>
                                                </div>
                                                <div className="price-total__booking">
                                                    <p>{t('label.totalPrice')}</p>
                                                    <p style={{ paddingLeft: '5px', fontWeight: '700', color: 'red' }}>
                                                        {formatPrice(history?.totalBill)}
                                                    </p>
                                                </div>
                                            </div>
                                            <div className="btn__booking">
                                                <div style={{ justifyContent: 'left', width: '130px' }}>
                                                    <p className={history?.status}>{status}</p>
                                                    {history?.status === 'CHECK_OUT' && (
                                                        <>
                                                            {history.reviewed === true ? (
                                                                <div style={{ display: 'flex' }}>
                                                                    <h3 className="reviewed">
                                                                        {t('common.reviewed')}
                                                                        <DoneIcon />
                                                                    </h3>
                                                                </div>
                                                            ) : (
                                                                <button
                                                                    onClick={() => handleReview(history.bookingCode)}
                                                                    className="review"
                                                                >
                                                                    {t('common.review')}
                                                                </button>
                                                            )}
                                                        </>
                                                    )}
                                                    {history?.status === 'WAITING' && (
                                                        <ModalConfirmDelete
                                                            idRemove={history.bookingCode}
                                                            handleReload={handleReload}
                                                            data={history}
                                                        />
                                                    )}
                                                </div>
                                            </div>
                                        </div>
                                    );
                                })}
                            </>
                        )}
                        <FormEvaluate
                            showFormReview={showFormReview}
                            handleCloseReview={handleCloseReview}
                            handleReload={handleReload}
                            bookingCode={idBooking}
                        />
                    </div>
                </div>
            </div>
        </FramePage>
    );
};

export default HistoryBookingPage;
