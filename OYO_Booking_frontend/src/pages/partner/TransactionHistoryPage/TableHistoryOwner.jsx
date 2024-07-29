import { DataGrid } from '@mui/x-data-grid';
import { useEffect, useState } from 'react';
import partnerManageBookingAPI from '~/services/apis/partnerAPI/partnerManageBookingAPI';
import formatPrice from '~/utils/formatPrice';
import nodata from '~/assets/img/no-data.jpg';
import './TableHistoryOwner.scss';

const TableHistoryOwner = () => {
    const [dataListHistory, setDataListHistory] = useState([]);

    useEffect(() => {
        partnerManageBookingAPI.getHistoryBooking().then((dataResponse) => {
            setDataListHistory(dataResponse.data.content);
        });
    }, []);

    const rows = dataListHistory.map((item, index) => ({
        id: index + 1,
        bookingCode: item.bookingCode,
        nameCustomer: item.nameCustomer || '',
        totalBill: item.totalBill ? formatPrice(item.totalBill) : '',
        checkIn: item.checkIn || '',
        checkOut: item.checkOut || '',
        guests: item.numAdult || '0',
        accomName: item.accomName || '',
        status: item.status || ''
    }));

    return (
        <div className="listdata_history">
            {rows.length > 0 ? (
                <DataTable rows={rows} />
            ) : (
                <div className="no-data">
                    <img src={nodata} alt="No Data" />
                    <p>Không có dữ liệu</p>
                </div>
            )}
        </div>
    );
};

const columns = [
    { field: 'id', headerName: 'STT', width: 50 },
    { field: 'bookingCode', headerName: 'Mã giao dịch', width: 400, hide: true },
    { field: 'nameCustomer', headerName: 'Tên khách hàng', width: 200 },
    { field: 'totalBill', headerName: 'Tổng thanh toán', width: 160 },
    { field: 'checkIn', headerName: 'Ngày đặt phòng', width: 160 },
    { field: 'checkOut', headerName: 'Ngày trả phòng', width: 160 },
    { field: 'guests', headerName: 'Khách', width: 80 },
    { field: 'accomName', headerName: 'Tên nhà thuê', width: 300 },
    { field: 'status', headerName: 'Tình trạng', width: 120 }
];

function DataTable({ rows }) {
    return (
        <div style={{ height: 500, width: '100%', marginBottom: '50px' }}>
            <DataGrid
                rows={rows}
                columns={columns}
                pageSize={6}
                rowsPerPageOptions={[6]}
                sx={{ fontSize: '17px', overflowX: 'hidden' }}
            />
        </div>
    );
}

export default TableHistoryOwner;
