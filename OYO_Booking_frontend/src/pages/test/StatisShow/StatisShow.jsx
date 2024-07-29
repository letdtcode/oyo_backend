import { ChangeEvent, useEffect, useState } from 'react';

import LineChart from '~/components/Chart/LineChart/LineChart';
import PieChart from '~/components/Chart/PieChart/PieChart';
import statisticApi from '~/services/apis/partnerAPI/statisticApi';

import './StatisShow.scss';
import TableDataStatis from './TableDataStatis';

const StatisShow = () => {
    const [dataStatis, setDataStatis] = useState();
    const currentYear = new Date().getFullYear();
    const [year, setYear] = useState(currentYear);
    const [reCall, setReCall] = useState(false);

    useEffect(() => {
        statisticApi.getStatisticOfHost('').then((dataResponse) => {
            setDataStatis(dataResponse.data);
        });
    }, []);

    const handleChangeYear = (event) => {
        setYear(parseInt(event.currentTarget?.value));
    };

    const handleStatistic = () => {
        setReCall(!reCall);
        statisticApi.getStatisticOfHost(`${year !== 0 ? `?year=${year}` : ''}`).then((dataResponse) => {
            setDataStatis(dataResponse.data);
        });
    };
    return (
        <div className="statis-show">
            <div className="choose-year">
                <h2>Chọn năm thống kê:</h2>
                <input
                    type="number"
                    min={2000}
                    max={2100}
                    defaultValue={currentYear}
                    className="input-year"
                    onChange={handleChangeYear}
                />
                <button onClick={handleStatistic} className="btn-statistic">
                    Thống kê
                </button>
            </div>
            <hr /> <br />
            <div className="row">
                <div className="col l-6">
                    <div className="card-statis">
                        <h3>{`Tổng số khách đã đặt phòng: ${dataStatis?.totalNumberOfBooking}`}</h3>
                    </div>
                </div>
                <div className="col l-6">
                    <div className="card-statis">
                        <h3>{`Tổng số khách đã hoàn thành dịch vụ (không hủy): ${dataStatis?.totalNumberOfBookingFinish}`}</h3>
                    </div>
                </div>
            </div>
            <br /> <br />
            <div className="row">
                <div className="col l-4">
                    <h2 style={{ marginLeft: '20px' }}>Thông số đặt phòng của từng nhà</h2>
                    <div style={{ padding: '15px' }}>
                        <PieChart data={dataStatis?.homeStatistic} />
                    </div>
                </div>
                <div className="col l-8">
                    <h2>Doanh thu theo tháng</h2>
                    <LineChart data={dataStatis?.revenueStatistics} />
                </div>
            </div>
            <br /> <br /> <hr /> <br />
            <TableDataStatis data={[]} year={year} reCall={reCall} />
        </div>
    );
};

export default StatisShow;
