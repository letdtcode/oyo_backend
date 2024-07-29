import { useEffect, useRef, useState } from 'react';
import { DateRangePicker } from 'react-date-range';
import format from 'date-fns/format';
import 'react-date-range/dist/styles.css';
import 'react-date-range/dist/theme/default.css';
import './DateBooking.scss';
import { useDispatch } from 'react-redux';
import bookingSlice from '~/redux/bookingSlice';
import { t } from 'i18next';
import { useSelector } from 'react-redux';
import publicAccomPlaceAPI from '~/services/apis/publicAPI/publicAccomPlaceAPI';

const DateBooking = (props) => {
    const [messageWarningBooking, setMessageWarningBooking] = useState(false);
    const dataBooking = useSelector((state) => state.booking.info);
    const [range, setRange] = useState([
        {
            startDate: new Date(),
            endDate: new Date(),
            key: 'selection'
        }
    ]);
    const [open, setOpen] = useState(false);
    const refOne = useRef(null);
    const dispatch = useDispatch();

    useEffect(() => {
        document.addEventListener('keydown', hideOnEscape, true);
        document.addEventListener('click', hideOnClickOutside, true);
    }, []);

    const hideOnEscape = (e) => {
        if (e.key === 'Escape') {
            setOpen(false);
        }
    };

    const hideOnClickOutside = (e) => {
        if (refOne.current && !refOne.current.contains(e.target)) {
            setOpen(false);
        }
    };

    const handleChangeDayBooking = async (value) => {
        const checkIn = format(value[0].startDate, 'dd/MM/yyyy');
        const checkOut = format(value[0].endDate, 'dd/MM/yyyy');
        dispatch(bookingSlice.actions.addDay({ checkIn, checkOut }));
    };

    return (
        <div className="date__booking">
            <div className="info-day">
                <div className="day">
                    <p style={{ fontWeight: 'bold', marginTop: '10px' }}>{t('title.bookingOfYou.day')}</p>
                    <p className="info_date" style={{ display: 'flex', justifyContent: 'space-between', width: 330 }}>
                        <span>
                            {props.checkIn} -- {props.checkOut}
                        </span>
                        {dataBooking?.canBooking === false && (
                            <span className="error" style={{ fontSize: 14, fontWeight: 500, color: 'red' }}>
                                {t('common.candontBooking')}
                            </span>
                        )}
                    </p>
                </div>

                <p onClick={() => setOpen((open) => !open)} className="edit-date">
                    {t('common.edit')}
                </p>
            </div>
            <div ref={refOne}>
                {open && (
                    <DateRangePicker
                        onChange={(item) => {
                            setRange([item.selection]);
                            handleChangeDayBooking([item.selection]);
                        }}
                        editableDateInputs={true}
                        moveRangeOnFirstSelection={false}
                        ranges={range}
                        months={2}
                        minDate={new Date()}
                        direction={props.size}
                        className="calendarElement"
                        showDateDisplay={false}
                    />
                )}
            </div>
        </div>
    );
};

export default DateBooking;
