import { useEffect, useState } from 'react';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import { DataGrid } from '@mui/x-data-grid';
import partnerStatisticAPI from '~/services/apis/partnerAPI/partnerStatisticAPI';
import formatPrice from '~/utils/formatPrice';

const TableDataStatistic = ({ data, year, reCall }) => {
    const [dataStatisticMonth, setDataStatisticMonth] = useState([]);
    const [stopMonthFirst, setStopMonthFirst] = useState(false);
    const [stopMonthLast, setStopMonthLast] = useState(false);

    const [dateStatistic, setDateStatistic] = useState({
        year: year ? year : new Date().getFullYear(),
        month: new Date().getMonth() + 1
    });
    console.log(data);
    useEffect(() => {
        partnerStatisticAPI
            .getStatisticHomeByMonthAndYearOfHost(dateStatistic.year, dateStatistic.month)
            .then((dataRes) => {
                setDataStatisticMonth(dataRes.data.content);
            });
    }, [dateStatistic, reCall]);

    const rows = [];
    for (var i = 0; i < dataStatisticMonth.length; i++) {
        rows.push({
            id: i + 1,
            accomId: dataStatisticMonth[i].accomId,
            accomName: dataStatisticMonth[i]?.accomName ? dataStatisticMonth[i].accomName : '',
            numberOfBooking: dataStatisticMonth[i]?.numberOfBooking ? dataStatisticMonth[i].numberOfBooking : '0',
            numberOfReview: dataStatisticMonth[i]?.numberOfReview ? dataStatisticMonth[i].numberOfReview : '0',
            averageRate: dataStatisticMonth[i]?.averageRate ? dataStatisticMonth[i].averageRate : '0',
            numberOfView: dataStatisticMonth[i]?.numberOfView ? dataStatisticMonth[i].numberOfView : '0',
            reservationRate: dataStatisticMonth[i]?.reservationRate
                ? `${dataStatisticMonth[i].reservationRate.toFixed(2)} %`
                : '0 %',
            revenue: dataStatisticMonth[i]?.revenue ? formatPrice(dataStatisticMonth[i].revenue) : '0 đ'
        });
    }

    const DataTable = (props) => {
        return (
            <div style={{ height: 400, width: '100%', marginBottom: '30px' }}>
                <DataGrid
                    initialState={{
                        columns: {
                            columnVisibilityModel: { accomId: false }
                        }
                    }}
                    rows={props.rows}
                    columns={columns}
                    pageSize={5}
                    rowsPerPageOptions={[5]}
                    sx={{ fontSize: '17px', overflowX: 'hidden' }}
                    onSelectionModelChange={(ids) => {
                        const selectedIDs = new Set(ids);
                        const selectedRows = props.rows.filter((row) => selectedIDs.has(row.id));
                    }}
                />
            </div>
        );
    };

    const columns = [
        { field: 'id', headerName: 'ID', width: 50 },
        { field: 'accomId', headerName: 'ID', width: 70 },
        { field: 'accomName', headerName: 'Nhà / phòng cho thuê', width: 380 },
        {
            field: 'numberOfBooking',
            headerName: 'Tổng lượt đặt',
            width: 140
        },
        {
            field: 'numberOfReview',
            headerName: 'Số đánh giá',
            width: 140
        },
        { field: 'averageRate', headerName: 'Điểm rate', width: 140 },
        {
            field: 'numberOfView',
            headerName: 'Lượt xem',
            width: 140
        },
        { field: 'reservationRate', headerName: 'Tỉ lệ đặt phòng', width: 150 },
        { field: 'revenue', headerName: 'Doanh thu', width: 160 }
    ];

    return (
        <div className="listdata_summary">
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div style={{ display: 'flex', alignItems: 'center' }}>
                    {!stopMonthFirst ? (
                        <ArrowBackIosIcon
                            sx={{ cursor: 'pointer' }}
                            onClick={() => {
                                setStopMonthLast(false);
                                if (dateStatistic.month - 1 === 1) {
                                    setStopMonthFirst(true);
                                } else {
                                    setStopMonthFirst(false);
                                    setDateStatistic({
                                        year: year,
                                        month: dateStatistic.month - 1
                                    });
                                }
                            }}
                        />
                    ) : (
                        <></>
                    )}
                    <h4 style={{ margin: 0, fontSize: '14px' }}>Tháng {dateStatistic.month - 1}</h4>
                </div>

                <h4 style={{ margin: 0, fontSize: '14px' }}>Tháng {dateStatistic.month}</h4>

                <div style={{ display: 'flex', alignItems: 'center' }}>
                    <h4 style={{ margin: 0, fontSize: '14px', marginRight: '4px' }}>Tháng {dateStatistic.month + 1}</h4>
                    {!stopMonthLast ? (
                        <ArrowForwardIosIcon
                            sx={{ cursor: 'pointer' }}
                            onClick={() => {
                                setStopMonthFirst(false);
                                if (dateStatistic.month + 1 === 12) {
                                    setStopMonthLast(true);
                                } else {
                                    setStopMonthLast(false);
                                    setDateStatistic({
                                        year: year,
                                        month: dateStatistic.month + 1
                                    });
                                }
                            }}
                        />
                    ) : (
                        <></>
                    )}
                </div>
            </div>
            <br />
            <DataTable rows={rows} />
        </div>
    );
};

export default TableDataStatistic;
