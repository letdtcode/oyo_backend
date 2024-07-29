import { DataGrid } from '@mui/x-data-grid';
import { useState, useEffect } from 'react';
import formatPrice from '~/utils/formatPrice';
import { useSnackbar } from 'notistack';
import ModalConfirm from '~/components/ModalConfirm/ModalConfirm';
import partnerManageBookingAPI from '~/services/apis/partnerAPI/partnerManageBookingAPI';

const TableDataHostSummary = (props) => {
    const { enqueueSnackbar } = useSnackbar();
    const [rows, setRows] = useState([]);
    const [confirm, setConfirm] = useState(false);
    const [openConfirm, setOpenConfirm] = useState(false);
    const [selectedId, setSelectedId] = useState(null);
    const [refreshSelection, setRefreshSelection] = useState(false);
    useEffect(() => {
        const newRows = props.data.map((item, index) => ({
            id: index + 1,
            bookingCode: item.bookingCode,
            accomName: item.accomName || '',
            nameCustomer: item.nameCustomer || '',
            checkIn: item.checkIn || '',
            checkOut: item.checkOut || '',
            guests: item.numAdult || '1',
            totalBill: item.totalBill ? formatPrice(item.totalBill) : '1 đ',
            totalTransfer: item.totalTransfer ? formatPrice(item.totalTransfer) : '0 đ'
        }));

        setRows(newRows);
    }, [props.data]);

    const handleCheck = (id) => {
        setOpenConfirm(true);
        setSelectedId(id);
    };
    useEffect(() => {
        if (confirm) {
            if (props.idTab === 'Check In') {
                partnerManageBookingAPI.setCheckIn(selectedId).then((res) => {
                    enqueueSnackbar('Check in thành công', { variant: 'success' });
                    if (res.statusCode === 200) {
                        props.setLoad((prevLoad) => !prevLoad);
                        setRefreshSelection((prev) => !prev);
                    }
                });
            } else if (props.idTab === 'Check Out') {
                partnerManageBookingAPI.setCheckOut(selectedId).then((res) => {
                    enqueueSnackbar('Check out thành công', { variant: 'success' });
                    if (res.statusCode === 200) {
                        props.setLoad((prevLoad) => !prevLoad);
                        setRefreshSelection((prev) => !prev);
                    }
                });
            }
            setConfirm(false);
            setOpenConfirm(false);
        }
    }, [confirm, props.idTab, selectedId]);

    return (
        <div className="listdata_summary">
            <DataTable rows={rows} handleCheck={handleCheck} idTab={props.idTab} refreshSelection={refreshSelection} />
            {openConfirm && (
                <ModalConfirm
                    setOpen={setOpenConfirm}
                    setConfirm={setConfirm}
                    title={`Xác nhận ${props.idTab}`}
                    content={`Bạn có chắc chắn muốn ${
                        props.idTab === 'Check In' ? 'check-in' : 'check-out'
                    } mã đặt chỗ ${selectedId} không?`}
                />
            )}
        </div>
    );
};

function DataTable(props) {
    const columns = [
        { field: 'id', headerName: 'STT', width: 50 },

        { field: 'accomName', headerName: 'Nhà / phòng cho thuê', width: 300 },
        { field: 'nameCustomer', headerName: 'Tên khách hàng', width: 200 },
        {
            field: 'checkIn',
            headerName: 'Nhận phòng',
            width: 130
        },
        {
            field: 'checkOut',
            headerName: 'Trả phòng',
            width: 130
        },
        // {
        //     field: 'guests',
        //     headerName: 'Khách',
        //     width: 80
        // },
        { field: 'totalBill', headerName: 'Tổng tiền', width: 130 },
        { field: 'totalTransfer', headerName: 'Đã thanh toán', width: 140 },
        {
            field: 'CheckIn',
            headerName: props.idTab,
            width: 140,
            renderCell: (params) => (
                <button onClick={(e) => props.handleCheck(params.row.bookingCode)} className="btn-check-status">
                    {props.idTab}
                </button>
            )
        }
    ];

    const [loading, setLoading] = useState(false);
    useEffect(() => {
        if (props.refreshSelection) {
            setLoading(true);
            setTimeout(() => {
                setLoading(false);
            }, 1000); // Thời gian chờ 1 giây tương ứng với thời gian của animation CSS
        }
    }, [props.refreshSelection]);
    return (
        <div className="refresh-container" style={{ height: 400, width: '100%', marginBottom: '30px' }}>
            <div style={{ height: 400, width: '100%', marginBottom: '30px' }}>
                <DataGrid
                    rows={loading ? [] : props.rows} // Hiển thị một mảng trống khi đang loading
                    key={props.refreshSelection}
                    columns={columns}
                    pageSize={5}
                    rowsPerPageOptions={[5]}
                    sx={{ fontSize: '17px', overflowX: 'hidden' }}
                    onRowSelectionModelChange={(ids) => {
                        const selectedIDs = new Set(ids);
                        const selectedRows = props.rows.filter((row) => selectedIDs.has(row.id));
                    }}
                />
            </div>
        </div>
    );
}

export default TableDataHostSummary;
