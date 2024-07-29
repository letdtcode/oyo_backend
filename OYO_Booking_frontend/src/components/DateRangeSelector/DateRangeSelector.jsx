import './DateRangeSelector.scss';
import { t } from 'i18next';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { enGB } from 'date-fns/locale';
import { DateRangePicker } from 'react-nice-dates';
import moment from 'moment';
import 'react-nice-dates/build/style.css';
import { useRef } from 'react';

export default function DateRangeSelector({ dateBook, setDataDay }) {
    const startDateRef = useRef(null);
    const endDateRef = useRef(null);

    const handleCheckinClick = () => {
        setTimeout(() => {
            if (startDateRef.current) {
                startDateRef.current.focus();
            }
        }, 0);
    };

    const handleCheckoutClick = () => {
        setTimeout(() => {
            if (endDateRef.current) {
                endDateRef.current.focus();
            }
        }, 0);
    };

    return (
        <div className="date-range-selector">
            <DateRangePicker
                startDate={moment(dateBook[0], 'DD/MM/yyyy').toDate()}
                endDate={moment(dateBook[1], 'DD/MM/yyyy').toDate()}
                onStartDateChange={(e) => {
                    if (e) {
                        setDataDay(moment(e, 'DD/MM/yyyy').toDate(), moment(dateBook[1], 'DD/MM/yyyy').toDate());
                    }
                }}
                onEndDateChange={(e) => {
                    if (e) {
                        if (moment(e, 'DD/MM/yyyy').toDate() < moment(dateBook[0], 'DD/MM/yyyy').toDate()) {
                            setDataDay(moment(e, 'DD/MM/yyyy').toDate(), moment(dateBook[0], 'DD/MM/yyyy').toDate());
                        } else {
                            setDataDay(moment(dateBook[0], 'DD/MM/yyyy').toDate(), moment(e, 'DD/MM/yyyy').toDate());
                        }
                    }
                }}
                minimumDate={new Date()}
                format="dd/MM/yyyy"
                locale={enGB}
            >
                {({ startDateInputProps, endDateInputProps, focus }) => (
                    <div className="info_date">
                        <div
                            className={`checkin ${focus === 'startDate' ? 'focused' : ''}`}
                            onClick={handleCheckinClick}
                        >
                            <div className="title__checkin">{t('contentMain.fromDay')}</div>
                            <input
                                className="date__checkin__input"
                                {...startDateInputProps}
                                placeholder={dateBook[0]}
                                ref={startDateRef}
                            />
                            <div className="date__checkin">{dateBook[0]}</div>
                            <ExpandMoreIcon className="icon__expand" />
                        </div>
                        <div
                            className={`checkout ${focus === 'endDate' ? 'focused' : ''}`}
                            onClick={handleCheckoutClick}
                        >
                            <div className="title__checkout">{t('contentMain.toDay')}</div>
                            <input
                                className="date__checkout__input"
                                {...endDateInputProps}
                                placeholder={dateBook[1]}
                                ref={endDateRef}
                            />
                            <div className="date__checkout">{dateBook[1]}</div>
                            <ExpandMoreIcon className="icon__expand" />
                        </div>
                    </div>
                )}
            </DateRangePicker>
        </div>
    );
}
