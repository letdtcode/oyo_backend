import { useEffect, useState } from 'react';
import format from 'date-fns/format';
import Popup from 'reactjs-popup';
import LockIcon from '@mui/icons-material/Lock';
import LockOpenIcon from '@mui/icons-material/LockOpen';
import RemoveRedEyeIcon from '@mui/icons-material/RemoveRedEye';
import { DataGrid } from '@mui/x-data-grid';
import NavbarOwner from '~/components/NavbarOwner/NavbarOwner';
import SearchHomeByOwner from '~/components/SearchHomeByOwner/SearchHomeByOwner';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import Footer from '~/components/Footer/Footer';
import './ListRoomOfHost.scss';

const ListRoomOfHost = () => {
    const [dataListhome, setDataListHome] = useState([]);
    useEffect(() => {
        partnerManageAccomAPI.getListAccomApproved().then((dataResponse) => {
            setDataListHome(dataResponse.data.content);
        });
    }, []);

    const handleSearchByHomeName = (value) => {
        // partnerManageAccomAPI.getListHomeOfPartner().then((dataResponse) => {
        //     setDataListHome(dataResponse.data.content);
        // });
    };

    const rows = [];
    for (var i = 0; i < dataListhome.length; i++) {
        rows.push({
            id: i + 1,
            idroom: dataListhome[i].id,
            name: dataListhome[i]?.accomName || '',
            status: dataListhome[i].status,
            bedroom: dataListhome[i].numBedRoom || '0',
            kitchenroom: dataListhome[i].numKitchen || '0',
            badroom: dataListhome[i].numBathRoom || '0',
            location: dataListhome[i].addressGeneral || '',
            editrecent: format(new Date(dataListhome[i].lastModifiedDate), 'dd/MM/yyyy'),
            view: dataListhome[i].id,
            remove: dataListhome[i]
        });
    }

    return (
        <div className="listroom__host">
            <NavbarOwner />
            <div className="welcome-card">
                <div className="welcome-content">
                    <h1>Quản lý hiệu quả</h1>
                    <p>Chào mừng bạn! Việc quản lý tốt sẽ giúp thu hút khách hàng hơn</p>
                </div>
            </div>
            <div className="header-listroom">
                <h1 style={{ marginRight: '10px' }}>{`${rows.length} nhà/phòng cho thuê`}</h1>
                <SearchHomeByOwner
                    placeholder="Tìm kiếm theo tên nhà"
                    handleSearchByHomeName={handleSearchByHomeName}
                />
            </div>
            <div className="data-table">
                <DataTable rows={rows} />
            </div>
            <Footer />
        </div>
    );
};

const columns = [
    // { field: 'id', headerName: 'ID', width: 70 },
    { field: 'id', headerName: 'STT', width: 70, hide: true },
    { field: 'name', headerName: 'Nhà / phòng cho thuê', width: 300 },
    { field: 'location', headerName: 'Vị trí', width: 300 },

    {
        field: 'bedroom',
        headerName: 'Phòng ngủ',
        type: 'number',
        width: 100,
        align: 'center'
    },
    {
        field: 'kitchenroom',
        headerName: 'Phòng bếp',
        type: 'number',
        width: 100,
        align: 'center'
    },
    {
        field: 'badroom',
        headerName: 'Phòng tắm',
        type: 'number',
        width: 100,
        align: 'center'
    },

    { field: 'editrecent', headerName: 'Sửa đổi gần nhất', width: 180, align: 'center' },
    { field: 'status', headerName: 'Trạng thái', width: 100 },
    {
        field: 'view',
        headerName: '',
        width: 10,
        renderCell: (params) => (
            <RemoveRedEyeIcon onClick={() => handleView(params.row.view)} sx={{ cursor: 'pointer' }} />
        )
    },
    {
        field: 'remove',
        headerName: '',
        width: 10,
        renderCell: (params) => (
            <Popup
                trigger={
                    params.row.remove.status === 'LOCK' ? (
                        <LockOpenIcon
                            className="icon__btn"
                            sx={{ color: 'red', cursor: 'pointer', fontSize: '18px' }}
                        />
                    ) : (
                        <LockIcon className="icon__btn" sx={{ color: 'red', cursor: 'pointer', fontSize: '18px' }} />
                    )
                }
                position="top right"
            >
                <div>
                    <p style={{ margin: '0', padding: '5px 10px', fontSize: '14px', marginBottom: '10px' }}>
                        {`Bạn chắc chắn muốn ${
                            params.row.remove.status === 'LOCK' ? 'mở khóa' : 'khóa'
                        } ngôi nhà này không?`}
                    </p>
                    <p
                        style={{
                            background: '#ef5350',
                            margin: '0',
                            width: 'auto',
                            paddingLeft: '15px',
                            paddingTop: '5px',
                            paddingBottom: '5px',
                            marginLeft: '70%',
                            marginBottom: '7px',
                            marginRight: '10px',
                            cursor: 'pointer',
                            color: 'white'
                        }}
                        onClick={() =>
                            handleDelete(params.row.remove.id, params.row.remove.status === 'LOCK' ? 'ACTIVE' : 'LOCK')
                        }
                    >
                        Yes
                    </p>
                </div>
            </Popup>
        )
    }
];

async function handleDelete(id, status) {
    // const dataActive = {
    //     homeId: id,
    //     status: status,
    // };
    // await homeApi.activeHome(dataActive).then((dataRes) => {
    //     window.location.href = `/host/setting`;
    // });
}

function handleView(id) {
    window.location.href = `/host/setting/${id}`;
}

function DataTable(props) {
    // const navigate = useNavigate();

    // const onCellClick = (params: GridCellParams, event: MuiEvent<React.MouseEvent>, details: GridCallbackDetails) => {
    //     navigate(`/host/setting/${params.row.idroom}`);
    // };

    return (
        <div style={{ height: 400, width: '100%', marginBottom: '30px' }}>
            <DataGrid
                rows={props.rows}
                columns={columns}
                pageSize={5}
                rowsPerPageOptions={[5]}
                disableSelectionOnClick={true}
                sx={{ fontSize: '17px', overflowX: 'hidden' }}
                // onCellClick={onCellClick}
            />
        </div>
    );
}

export default ListRoomOfHost;
