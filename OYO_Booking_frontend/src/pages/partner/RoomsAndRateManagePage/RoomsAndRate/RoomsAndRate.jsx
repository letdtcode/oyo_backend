import './RoomsAndRate.scss';
import React, { useState, useEffect } from 'react';
import moment from 'moment';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import arrowRight from '~/assets/svg/arrow-right.svg';
import arrowLeft from '~/assets/svg/arrow-left.svg';
import { useSnackbar } from 'notistack';
import Skeleton from '@mui/material/Skeleton';
import { useDispatch } from 'react-redux';
import managerAccomSlice from '~/redux/managerAccomSlice';
import SearchIcon from '@mui/icons-material/Search';

export default function RoomsAndRate({ accomPriceCustom }) {
    const dispatch = useDispatch();
    const [loading, setLoading] = useState(true);
    const [currentWeek, setCurrentWeek] = useState([]);
    const [accommodations, setAccommodations] = useState([]);
    const [changePrice, setChangePrice] = useState({});
    const [searchTerm, setSearchTerm] = useState('');
    const { enqueueSnackbar } = useSnackbar();

    useEffect(() => {
        setCurrentWeek(getCurrentWeekDates());

        const data = accomPriceCustom.map((item) => {
            const transformedPriceList = item.priceCustomForAccomList.reduce((acc, current) => {
                acc[current.dateApply] = { priceApply: current.priceApply };
                return acc;
            }, {});

            return {
                ...item,
                priceCustomForAccomList: transformedPriceList
            };
        });

        setAccommodations(data);
        setLoading(false);
    }, [accomPriceCustom]);

    const getCurrentWeekDates = () => {
        let today = moment();
        let days = [];
        for (let i = 0; i < 7; i++) {
            days.push(moment(today).add(i, 'days').format('YYYY-MM-DD'));
        }
        return days;
    };

    const renderTableHeader = () => {
        return currentWeek.map((date) => {
            const dayOfWeek = moment(date).day(); 
            const isWeekend = dayOfWeek === 0 || dayOfWeek === 6; 
            return (
                <th key={date} className={isWeekend ? 'weekend' : ''}>
                    {`${dayOfWeek === 0 ? 'CN' : `T${dayOfWeek + 1}`}, ${moment(date).format('DD')}`}
                </th>
            );
        });
    };

    const getPriceAccommodation = (accommodation, date) => {
        if (changePrice[accommodation.accomId] && changePrice[accommodation.accomId][date] !== undefined) {
            return changePrice[accommodation.accomId][date];
        }
        if (accommodation.priceCustomForAccomList[date] !== undefined) {
            return accommodation.priceCustomForAccomList[date].priceApply;
        } else {
            return accommodation.pricePerNight;
        }
    };

    const getPriceClass = (accommodation, date) => {
        const defaultPrice = accommodation.pricePerNight;
        const customPrice =
            changePrice[accommodation.accomId]?.[date] ?? accommodation.priceCustomForAccomList[date]?.priceApply;

        if (customPrice === undefined || customPrice === defaultPrice) {
            return 'default-price';
        } else if (customPrice > defaultPrice) {
            return 'higher-price';
        } else {
            return 'lower-price';
        }
    };

    const renderAccommodationRows = () => {
        const filteredAccommodations = accommodations.filter((accommodation) =>
            accommodation.accomName.toLowerCase().includes(searchTerm.toLowerCase())
        );
        return filteredAccommodations.map((accommodation, index) => (
            <tr key={accommodation.accomId} className="rate-room">
                <td className="rate-room__name">{accommodation.accomName}</td>
                {currentWeek.map((date) => (
                    <td key={`${accommodation.accomId}-${date}`} className="rate-room__td">
                        <input
                            type="text"
                            value={getPriceAccommodation(accommodation, date)?.toLocaleString('vi-VN')}
                            className={`rate-room__input ${getPriceClass(accommodation, date)}`}
                            onChange={(event) => handlePriceChange(accommodation.accomId, date, event)}
                        />
                    </td>
                ))}
            </tr>
        ));
    };

    const handlePriceChange = (id, date, event) => {
        let newChangePrice = parseInt(event.target.value.replace(/\D/g, ''));
        if (newChangePrice < 0 || isNaN(newChangePrice)) {
            newChangePrice = 0;
        }
        setChangePrice((prevChangePrice) => ({
            ...prevChangePrice,
            [id]: {
                ...prevChangePrice[id],
                [date]: newChangePrice
            }
        }));
    };

    const getPreviousWeekDates = () => {
        return currentWeek.map((date) => moment(date).subtract(7, 'days').format('YYYY-MM-DD'));
    };

    const getNextWeekDates = () => {
        return currentWeek.map((date) => moment(date).add(7, 'days').format('YYYY-MM-DD'));
    };

    const handleSave = () => {
        const data = Object.keys(changePrice).reduce((acc, accomId) => {
            return [
                ...acc,
                ...Object.keys(changePrice[accomId]).map((date) => ({
                    accomId: parseInt(accomId),
                    dateApply: moment(date).format('DD/MM/YYYY'),
                    priceApply: changePrice[accomId][date]
                }))
            ];
        }, []);
        partnerManageAccomAPI.updatePriceCustom(data).then((res) => {
            enqueueSnackbar('Cập nhật thành công', { variant: 'success' });
        });
        dispatch(managerAccomSlice.actions.reloadResources());
    };

    return (
        <div className="rooms-and-rate-container">
            {loading ? (
                <Skeleton variant="rectangular" width="100%" height={800} />
            ) : (
                <>
                    <div className="week-navigation">
                        <button
                            className="week-navigation__button"
                            onClick={() => setCurrentWeek(getPreviousWeekDates())}
                            disabled={moment(currentWeek[0]).isBefore(moment())}
                        >
                            <img src={arrowLeft} alt="arrow-right" className="week-navigation__icon" />
                        </button>
                        <button className="week-navigation__button" onClick={() => setCurrentWeek(getNextWeekDates())}>
                            <img src={arrowRight} alt="arrow-right" className="week-navigation__icon" />
                        </button>

                        <div className="search-input-home">
                            <input
                                type="text"
                                placeholder="Tìm kiếm theo tên"
                                className="search-bar"
                                value={searchTerm}
                                onChange={(e) => setSearchTerm(e.target.value)}
                            />

                            <div className="searchIcon-clear"></div>
                            <div className="searchIcon">
                                <SearchIcon />
                            </div>
                        </div>
                        <div style={{ marginLeft: 'auto' }}></div>
                        <button className="week-navigation__save" onClick={handleSave}>
                            Lưu
                        </button>
                    </div>
                    <div className="table-container">
                        <table>
                            <thead>
                                <tr>
                                    <th className="currentWeek">
                                        {currentWeek[0]} &rarr; {currentWeek[6]}
                                    </th>
                                    {renderTableHeader()}
                                </tr>
                            </thead>
                            <tbody>{renderAccommodationRows()}</tbody>
                        </table>
                    </div>
                </>
            )}
        </div>
    );
}
