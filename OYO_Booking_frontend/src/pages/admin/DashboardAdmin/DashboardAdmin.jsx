import { useState, useEffect } from 'react';
import cmsStatisticAPI from '~/services/apis/adminAPI/cmsStatisticAPI';
import StatusCard from '~/components/Admin/StatusCard/StatusCard';
import format from 'date-fns/format';
import TabsChart from './TabsChart/TabsChart';
import TabPanel from '@mui/lab/TabPanel';
import Table from '~/components/Table/Table';
import TabContext from '@mui/lab/TabContext';
import Tab from '@mui/material/Tab';
import TabList from '@mui/lab/TabList';
import DateForStatistic from '~/components/Admin/DateForStatistic/DateForStatistic';
import Box from '@mui/material/Box';
import formatPrice from '~/utils/formatPrice';
import format3Dots from '~/utils/format3Dots';
import './DashboardAdmin.scss';

const customerHeader = {
    head: ['Tên khách hàng', 'Email', 'Số điện thoại', 'Tổng lượt đặt', 'Tổng chi tiêu']
};

const renderCusomerHead = (item, index) => <th key={index}>{item}</th>;

const renderCusomerBody = (item, index) => (
    <tr key={index}>
        <td>{item.fullName}</td>
        <td>{item.email}</td>
        <td>{item.phoneNumber ? item.phoneNumber : '--'}</td>
        <td>{item.numberOfBooking ? item.numberOfBooking : '--'}</td>
        <td>{formatPrice(item.totalCost)}</td>
    </tr>
);

const hostHeader = {
    head: ['Tên chủ nhà', 'Email', 'Số lượng homestay', 'Tổng lượt đặt', 'Doanh thu']
};

const renderHostHead = (item, index) => <th key={index}>{item}</th>;

const renderHostBody = (item, index) => (
    <tr key={index}>
        <td>{item.fullName}</td>
        <td>{item.email}</td>
        <td>{item.numberOfAccom ? item.numberOfAccom : '--'}</td>
        <td>{item.numberOfBooking ? item.numberOfBooking : '--'}</td>
        <td>{formatPrice(item.totalRevenue)}</td>
    </tr>
);

const homestayHeader = {
    head: ['Tên homestay', 'Tên chủ nhà', 'Lượt xem', 'Lượt đặt phòng', 'Doanh thu', 'Lượt đánh giá', 'Đánh giá sao']
};

const renderHomestayHead = (item, index) => <th key={index}>{item}</th>;

const renderHomestayBody = (item, index) => (
    <tr key={index}>
        <td>{item.accomName}</td>
        <td>{item.hostName}</td>
        <td>{item.numberOfView ? item.numberOfView : '--'}</td>
        <td>{item.numberOfBooking ? item.numberOfBooking : '--'}</td>
        <td>{formatPrice(item.totalRevenue)}</td>
        <td>{item.numberOfReview ? item.numberOfReview : '--'}</td>
        <td>{item.averageGradeRate}</td>
    </tr>
);

const transactionHeader = {
    head: ['Tên khách hàng', 'Tên chủ nhà', 'Tổng thanh toán', 'Tiền thụ hưởng admin', 'Tên homestay', 'Ngày tạo']
};

const renderTransactionHead = (item, index) => <th key={index}>{item}</th>;

const renderTransactionBody = (item, index) => (
    <tr key={index}>
        <td>{item.customerName}</td>
        <td>{item.ownerName}</td>
        <td>{formatPrice(item.totalCost)}</td>
        <td>{formatPrice(item.adminCost)}</td>
        <td>{item.homeName}</td>
        <td>{format3Dots(item.createdDate, 24)}</td>
    </tr>
);

const DashboardAdmin = () => {
    const date = new Date();
    const firstDay = format(new Date(date.getFullYear(), date.getMonth(), 1), 'yyyy-MM-dd');
    const lastDay = format(new Date(date.getFullYear(), date.getMonth() + 1, 0), 'yyyy-MM-dd');
    const [dateStatistic, setDateStatistic] = useState([firstDay, lastDay]);
    const [year, setYear] = useState(new Date().getFullYear());
    const [tabCurrent, setTabCurrent] = useState('1');
    const [dataGuests, setDataGuests] = useState([]);
    const [dataHost, setDataHost] = useState([]);
    const [dataHomestay, setDataHomestay] = useState([]);
    const [dataTransaction, setDataTransaction] = useState([]);

    const [numberStatis, setNumberStatis] = useState([
        {
            icon: 'bx bx-user',
            count: 0,
            title: 'Tổng số khách hàng'
        },
        {
            icon: 'bx bx-cart',
            count: 0,
            title: 'Tổng lượt đặt phòng'
        },
        {
            icon: 'bx bx-dollar-circle',
            count: 0,
            title: 'Tổng doanh thu'
        },
        {
            icon: 'bx bx-home-circle',
            count: 0,
            title: 'Tổng số chủ nhà'
        }
    ]);

    useEffect(() => {
        cmsStatisticAPI.getStatisticOfAdmin(year).then((dataResponse) => {
            const dataStatistic = [
                {
                    icon: 'bx bx-user',
                    count: dataResponse.data.numberOfGuest,
                    title: 'Tổng số khách hàng'
                },
                {
                    icon: 'bx bx-cart',
                    count: dataResponse.data.numberOfBooking,
                    title: 'Tổng lượt đặt phòng'
                },
                {
                    icon: 'bx bx-dollar-circle',
                    count: `${new Intl.NumberFormat('vi-VN').format(parseInt(dataResponse.data.totalOfRevenue))}`,
                    title: 'Tổng doanh thu'
                },
                {
                    icon: 'bx bx-home-circle',
                    count: dataResponse.data.numberOfOwner,
                    title: 'Tổng số chủ nhà'
                }
            ];
            setNumberStatis(dataStatistic);
        });

        cmsStatisticAPI.getStatisticForGuestOfAdmin(dateStatistic).then((dataResponse) => {
            setDataGuests(dataResponse?.data?.content);
        });

        cmsStatisticAPI.getStatisticForHostOfAdmin(dateStatistic).then((dataResponse) => {
            setDataHost(dataResponse?.data?.content);
        });

        cmsStatisticAPI.getStatisticForAccomPlaceOfAdmin(dateStatistic).then((dataResponse) => {
            setDataHomestay(dataResponse.data.content);
        });

        cmsStatisticAPI.getStatisticForTransactionOfAdmin(dateStatistic).then((dataResponse) => {
            setDataTransaction(dataResponse.data.content);
        });
    }, [dateStatistic, year]);

    const handleChangeDayStatistic = (value) => {
        const dateFrom = value[0].startDate;
        const dateTo = value[0].endDate;
        setDateStatistic([dateFrom, dateTo]);
    };

    const handleChangeYear = (event) => {
        setYear(parseInt(event.target.value));
    };

    const handleChangeTab = (event, newValue) => {
        setTabCurrent(newValue);
    };
    return (
        <div className="dashboard__admin">
            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                <h2 className="page-header">Dashboard</h2>
                <div className="choose-year">
                    <h2>Chọn năm thống kê:</h2>
                    <input
                        type="number"
                        min={2000}
                        max={2100}
                        defaultValue={year}
                        className="input-year"
                        onChange={handleChangeYear}
                    />
                </div>
            </div>
            <div className="row">
                <div className="col l-5">
                    <div className="row">
                        {numberStatis?.map((item, index) => (
                            <div className="col l-6" key={index}>
                                <StatusCard icon={item.icon} count={item.count} title={item.title} />
                            </div>
                        ))}
                    </div>
                </div>

                <div className="col l-7" style={{ paddingBottom: '30px' }}>
                    <Box sx={{ flexGrow: 1, bgcolor: 'background.paper', display: 'flex', height: '100%' }}>
                        <TabsChart year={year} />
                    </Box>
                </div>

                <div className="col l-12">
                    <Box sx={{ width: '100%', typography: 'body1' }}>
                        <TabContext value={tabCurrent}>
                            <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                                <TabList onChange={handleChangeTab} aria-label="lab API tabs example">
                                    <Tab
                                        label="Thống kê khách"
                                        value="1"
                                        sx={{ textTransform: 'none', fontSize: '14px' }}
                                    />
                                    <Tab
                                        label="Thống kê chủ nhà"
                                        value="2"
                                        sx={{ textTransform: 'none', fontSize: '14px' }}
                                    />
                                    <Tab
                                        label="Thống kê nhà"
                                        value="3"
                                        sx={{ textTransform: 'none', fontSize: '14px' }}
                                    />
                                    <Tab label="Giao dịch" value="4" sx={{ textTransform: 'none', fontSize: '14px' }} />
                                </TabList>
                            </Box>
                            <TabPanel value="1">
                                <div className="card-admin">
                                    <div className="card__header">
                                        <h3>Khách hàng thân thiết</h3>
                                        <DateForStatistic
                                            setDataDay={handleChangeDayStatistic}
                                            dateStart={firstDay}
                                            dateEnd={lastDay}
                                        />
                                    </div>
                                    <div className="card__body">
                                        <Table
                                            limit="5"
                                            headData={customerHeader.head}
                                            renderHead={(item, index) => renderCusomerHead(item, index)}
                                            bodyData={dataGuests}
                                            renderBody={(item, index) => renderCusomerBody(item, index)}
                                        />
                                    </div>
                                </div>
                            </TabPanel>
                            <TabPanel value="2">
                                <div className="card-admin">
                                    <div className="card__header">
                                        <h3>Chủ nhà cho thuê tốt nhất</h3>
                                        <DateForStatistic
                                            setDataDay={handleChangeDayStatistic}
                                            dateStart={firstDay}
                                            dateEnd={lastDay}
                                        />
                                    </div>
                                    <div className="card__body">
                                        <Table
                                            limit="5"
                                            headData={hostHeader.head}
                                            renderHead={(item, index) => renderHostHead(item, index)}
                                            bodyData={dataHost}
                                            renderBody={(item, index) => renderHostBody(item, index)}
                                        />
                                    </div>
                                </div>
                            </TabPanel>
                            <TabPanel value="3">
                                <div className="card-admin">
                                    <div className="card__header">
                                        <h3>Thống kê nhà</h3>
                                        <DateForStatistic
                                            setDataDay={handleChangeDayStatistic}
                                            dateStart={firstDay}
                                            dateEnd={lastDay}
                                        />
                                    </div>
                                    <div className="card__body">
                                        <Table
                                            limit="5"
                                            headData={homestayHeader.head}
                                            renderHead={(item, index) => renderHomestayHead(item, index)}
                                            bodyData={dataHomestay}
                                            renderBody={(item, index) => renderHomestayBody(item, index)}
                                        />
                                    </div>
                                </div>
                            </TabPanel>
                            <TabPanel value="4">
                                <div className="card-admin">
                                    <div className="card__header">
                                        <h3>Thống kê giao dịch</h3>
                                        <DateForStatistic
                                            setDataDay={handleChangeDayStatistic}
                                            dateStart={firstDay}
                                            dateEnd={lastDay}
                                        />
                                    </div>
                                    <div className="card__body">
                                        <Table
                                            limit="5"
                                            headData={transactionHeader.head}
                                            renderHead={(item, index) => renderTransactionHead(item, index)}
                                            bodyData={dataTransaction}
                                            renderBody={(item, index) => renderTransactionBody(item, index)}
                                        />
                                    </div>
                                </div>
                            </TabPanel>
                        </TabContext>
                    </Box>
                </div>
            </div>
        </div>
    );
};

export default DashboardAdmin;
