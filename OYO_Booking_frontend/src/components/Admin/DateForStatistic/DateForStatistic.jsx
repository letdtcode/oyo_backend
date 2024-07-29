import { useState } from 'react';
import 'react-date-range/dist/styles.css';
import 'react-date-range/dist/theme/default.css';
import './DateForStatistic.scss';

const DateForStatistic = ({ dateStart, dateEnd, setDataDay }) => {
    const [range, setRange] = useState([
        {
            startDate: dateStart,
            endDate: dateEnd,
            key: 'selection'
        }
    ]);
    return (
        <div className="calendar-statistic">
            <div className="info_date">
                <div className="output start" style={{ display: 'flex', alignItems: 'center' }}>
                    <p
                        style={{
                            fontWeight: 'bold',
                            margin: '0',
                            marginBottom: '5px',
                            fontSize: '13px',
                            paddingTop: '4px'
                        }}
                    >
                        Từ ngày
                    </p>
                    <input
                        type="date"
                        className=" input-date-statistic"
                        value={range[0].startDate}
                        onChange={(e) => {
                            setRange([
                                {
                                    ...range[0],
                                    startDate: e.target.value
                                }
                            ]);
                            setDataDay([
                                {
                                    ...range[0],
                                    startDate: e.target.value
                                }
                            ]);
                        }}
                    />
                </div>

                <div className="output end" style={{ display: 'flex', alignItems: 'center' }}>
                    <p
                        style={{
                            fontWeight: 'bold',
                            margin: '0',
                            marginBottom: '5px',
                            fontSize: '13px',
                            paddingTop: '4px',
                            paddingRight: '8px'
                        }}
                    >
                        Đến ngày
                    </p>
                    <input
                        type="date"
                        className=" input-date-statistic"
                        value={range[0].endDate}
                        onChange={(e) => {
                            setRange([
                                {
                                    ...range[0],
                                    endDate: e.target.value
                                }
                            ]);
                            setDataDay([
                                {
                                    ...range[0],
                                    endDate: e.target.value
                                }
                            ]);
                        }}
                    />
                </div>
            </div>
        </div>
    );
};

export default DateForStatistic;
