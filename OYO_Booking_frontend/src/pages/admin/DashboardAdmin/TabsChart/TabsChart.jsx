import { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import Chart from 'react-apexcharts';
import Tab from '@mui/material/Tab';
import Tabs from '@mui/material/Tabs';
import Typography from '@mui/material/Typography';
import cmsStatisticAPI from '~/services/apis/adminAPI/cmsStatisticAPI';
import Box from '@mui/material/Box';
import './TabsChart.scss';

function a11yProps(index) {
    return {
        id: `vertical-tab-${index}`,
        'aria-controls': `vertical-tabpanel-${index}`
    };
}

function TabPanel({ children, value, index, ...other }) {
    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`vertical-tabpanel-${index}`}
            aria-labelledby={`vertical-tab-${index}`}
            {...other}
            style={{ width: '100%' }}
        >
            {value === index && (
                <Box sx={{ padding: '6px 16px 0px 16px', height: '100%' }}>
                    <Typography sx={{ height: '100%' }}>{children}</Typography>
                </Box>
            )}
        </div>
    );
}

const TabsChart = ({ year }) => {
    const [value, setValue] = useState(0);
    const [dataChartBooking, setDataChartBooking] = useState([]);
    const [dataChartRevenue, setDataChartRevenue] = useState([]);
    const themeReducer = useSelector((state) => state.global);

    useEffect(() => {
        cmsStatisticAPI.getStatisticChart(year, 'BOOKING').then((dataResponse) => {
            setDataChartBooking(dataResponse?.data?.revenueStatistics);
        });

        cmsStatisticAPI.getStatisticChart(year, 'REVENUE').then((dataResponse) => {
            setDataChartRevenue(dataResponse?.data?.revenueStatistics);
        });
    }, [year]);

    const chartOptions = {
        options: {
            chart: {
                id: 'basic-bar'
            },
            xaxis: {
                categories: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
            }
        },
        series: [
            {
                name: 'Booking',
                data: [
                    dataChartBooking.length !== 0 ? dataChartBooking[0]?.amount : 0,
                    dataChartBooking.length !== 0 ? dataChartBooking[1]?.amount : 0,
                    dataChartBooking.length !== 0 ? dataChartBooking[2]?.amount : 0,
                    dataChartBooking.length !== 0 ? dataChartBooking[3]?.amount : 0,
                    dataChartBooking.length !== 0 ? dataChartBooking[4]?.amount : 0,
                    dataChartBooking.length !== 0 ? dataChartBooking[5]?.amount : 0,
                    dataChartBooking.length !== 0 ? dataChartBooking[6]?.amount : 0,
                    dataChartBooking.length !== 0 ? dataChartBooking[7]?.amount : 0,
                    dataChartBooking.length !== 0 ? dataChartBooking[8]?.amount : 0,
                    dataChartBooking.length !== 0 ? dataChartBooking[9]?.amount : 0,
                    dataChartBooking.length !== 0 ? dataChartBooking[10]?.amount : 0,
                    dataChartBooking.length !== 0 ? dataChartBooking[11]?.amount : 0
                ]
            }
        ]
    };

    const chartOptionsRevenue = {
        options: {
            chart: {
                id: 'basic-bar'
            },
            xaxis: {
                categories: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
            }
        },
        series: [
            {
                name: 'Doanh thu',
                data: [
                    dataChartRevenue.length !== 0 ? dataChartRevenue[0]?.amount : 0,
                    dataChartRevenue.length !== 0 ? dataChartRevenue[1]?.amount : 0,
                    dataChartRevenue.length !== 0 ? dataChartRevenue[2]?.amount : 0,
                    dataChartRevenue.length !== 0 ? dataChartRevenue[3]?.amount : 0,
                    dataChartRevenue.length !== 0 ? dataChartRevenue[4]?.amount : 0,
                    dataChartRevenue.length !== 0 ? dataChartRevenue[5]?.amount : 0,
                    dataChartRevenue.length !== 0 ? dataChartRevenue[6]?.amount : 0,
                    dataChartRevenue.length !== 0 ? dataChartRevenue[7]?.amount : 0,
                    dataChartRevenue.length !== 0 ? dataChartRevenue[8]?.amount : 0,
                    dataChartRevenue.length !== 0 ? dataChartRevenue[9]?.amount : 0,
                    dataChartRevenue.length !== 0 ? dataChartRevenue[10]?.amount : 0,
                    dataChartRevenue.length !== 0 ? dataChartRevenue[11]?.amount : 0
                ]
            }
        ]
    };

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (
        <div className="tabs-chart">
            <Box sx={{ flexGrow: 1, bgcolor: 'background.paper', display: 'flex', height: '100%' }}>
                <Tabs
                    orientation="vertical"
                    variant="scrollable"
                    value={value}
                    onChange={handleChange}
                    aria-label="Vertical tabs example"
                    sx={{ borderRight: 1, borderColor: 'divider' }}
                >
                    <Tab
                        label="Đặt phòng"
                        {...a11yProps(0)}
                        sx={{ textTransform: 'none', padding: '5px', fontSize: '12px', fontFamily: 'Roboto' }}
                    />
                    <Tab
                        label="Doanh thu"
                        {...a11yProps(1)}
                        sx={{ textTransform: 'none', padding: '5px', fontSize: '12px', fontFamily: 'Roboto' }}
                    />
                </Tabs>
                <TabPanel value={value} index={0}>
                    <div className="card-admin-chart" style={{ height: '100%' }}>
                        <Chart
                            options={chartOptions.options}
                            series={chartOptions.series}
                            type="line"
                            height="100%"
                            width="100%"
                        />
                    </div>
                </TabPanel>
                <TabPanel value={value} index={1}>
                    <div className="card-admin-chart">
                        <Chart
                            options={chartOptionsRevenue.options}
                            series={chartOptionsRevenue.series}
                            type="line"
                            height="100%"
                            width="100%"
                        />
                    </div>
                </TabPanel>
            </Box>
        </div>
    );
};

export default TabsChart;
