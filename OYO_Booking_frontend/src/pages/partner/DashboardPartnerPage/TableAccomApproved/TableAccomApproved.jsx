import './TableAccomApproved.scss';
import React from 'react';
import { t } from 'i18next';
import { DataGrid } from '@mui/x-data-grid';
import SettingsIcon from '@mui/icons-material/Settings';
import IconButton from '@mui/material/IconButton';
import { useNavigate } from 'react-router-dom';
import nodata from '~/assets/img/no-data.jpg';

const NoRowsOverlay = () => (
    <div className="nodata-overlay">
        <img src={nodata} alt="No data" />
        <p>Chưa có dữ liệu để hiển thị  </p>
    </div>
);

export default function TableAccomApproved({ accomApproved, loading }) {
    const navigate = useNavigate();

    const handleSetting = (id) => () => {
        navigate(`/host/setting/${id}`);
    };
    
    console.log(loading, accomApproved)

    const columns = [
        { field: 'stt', headerName: 'STT', flex: 1 },
        {
            field: 'image',
            headerName: 'Hình ảnh',
            flex: 1,
            renderCell: (params) => (
                <img
                    src={params.row.image}
                    alt="accommodation"
                    style={{ width: '50px', height: '50px', objectFit: 'cover' }}
                />
            )
        },
        { field: 'name', headerName: 'Tên chỗ nghỉ', flex: 4 },
        { field: 'address', headerName: 'Địa chỉ', flex: 5 },
        { field: 'status', headerName: 'Trạng thái', flex: 1 },
        {
            field: 'action',
            headerName: 'Hành động',
            flex: 1,
            renderCell: (params) => (
                <IconButton onClick={handleSetting(params?.row?.id)}>
                    <SettingsIcon />
                </IconButton>
            )
        }
    ];

    const rows = accomApproved.map((item, index) => ({
        id: item.id,
        stt: index + 1,
        image: item.imageAccomsUrls[0],
        name: item.accomName,
        address: item.addressDetail,
        status: item.status === 'APPROVED' ? t('Đã duyệt') : t('Chờ duyệt')
    }));

    return (
        <div className="table-accom-approved paper">
            <DataGrid
                autoHeight
                loading={loading === 'loading'}
                disableRowSelectionOnClick
                rows={rows}
                columns={columns}
                pageSize={5}
                pageSizeOptions={[5]}
                initialState={{
                    pagination: {
                        paginationModel: {
                            pageSize: 10
                        }
                    }
                }}
                slots={{
                    noRowsOverlay: NoRowsOverlay
                }}
            />
        </div>
    );
}
