import { useState, useEffect } from 'react';
import FramePage from '~/components/FramePage/FramePage';
import './StatisticPage.scss';
import partnerStatisticAPI from '~/services/apis/partnerAPI/partnerStatisticAPI';
import LineChart from '~/components/Chart/LineChart/LineChart';
import PieChart from '~/components/Chart/PieChart/PieChart';
import TableDataStatistic from '~/pages/partner/StatisticPage/TableDataStatistic/TableDataStatistic';

const StatisticPage = () => {
    const [dataStatis, setDataStatis] = useState();
    const currentYear = new Date().getFullYear();
    const [year, setYear] = useState(currentYear);
    const [reCall, setReCall] = useState(false);

    useEffect(() => {
        partnerStatisticAPI.getStatisticOfHost(year).then((dataResponse) => {
            setDataStatis(dataResponse.data);
        });
    }, []);

    const handleChangeYear = (event) => {
        setYear(parseInt(event.currentTarget?.value));
    };

    const handleStatistic = () => {
        setReCall(!reCall);
        partnerStatisticAPI
            .getStatisticOfHost(`${year !== 0 && !isNaN(year) ? `${year}` : `${currentYear}`}`)
            .then((dataResponse) => {
                setDataStatis(dataResponse.data);
            });
    };
    const bannerData = {
        title: 'Thống kê',
        subtitle: 'Xem và phân tích các số liệu thống kê về hoạt động của bạn.',
        imgSrc: 'https://images.prismic.io/impactio-blog/21d9a519-4bfa-4345-8810-50301c8bb84f_Methods+for+Presenting+Statistical+Data+in+an+Easy+to+Read+Way.png?auto=compress,format' // Replace with the actual path to your image
    };
    return (
        <FramePage ownerPage={true} bannerData={bannerData}>
            <div className="statis-page">
                <h2 style={{ textTransform: 'uppercase' }}>Thống kê</h2>
                <div className="choose-year">
                    <h3>Chọn năm thống kê: </h3>
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
                <TableDataStatistic data={[]} year={year} reCall={reCall} />
            </div>
        </FramePage>
    );
};

export default StatisticPage;
