import { useSnackbar } from 'notistack';

import Popup from 'reactjs-popup';
import LockIcon from '@mui/icons-material/Lock';
import LockOpenIcon from '@mui/icons-material/LockOpen';
import Table from '~/components/Table/Table';
import cmsAccomPlaceAPI from '~/services/apis/adminAPI/cmsAccomPlaceAPI';
import './AccomAdmin.scss';
import formatPrice from '~/utils/formatPrice';

const customerTableHead = ['', 'Tên nhà', 'Chủ nhà', 'Địa chỉ', 'Loại hình cho thuê', 'Giá theo đêm', 'Trạng thái'];

const renderHead = (item, index) => <th key={index}>{item}</th>;

const AccomAdmin = (props) => {
    const { enqueueSnackbar } = useSnackbar();
    const handleChangeStatusAccom = (accomId, accomStatus) => {
        const status = accomStatus === 'BANNED' ? 'APPROVED' : 'BANNED';
        cmsAccomPlaceAPI
            .changeStatusAccomPlace(status, accomId)
            .then((response) => {
                const updatedAccomList = props.data.map((accom) => {
                    if (accom.id === accomId) {
                        return { ...accom, status: status };
                    }
                    return accom;
                });
                props.setListAccom(updatedAccomList);
                enqueueSnackbar(`${status === 'DISABLE' ? 'Khóa' : 'Mở khóa'} chỗ ở thành công`, {
                    variant: 'success'
                });
            })
            .catch((error) => {
                enqueueSnackbar(error.response?.data.message, { variant: 'error' });
            });
    };
    const renderBody = (item, index) => (
        <tr key={index}>
            <td>{index + 1}</td>
            <td>{item.accomName}</td>
            <td>{item.nameHost}</td>
            <td>{item.addressGeneral}</td>
            <td>{item.accomCateName}</td>
            <td>{formatPrice(item.pricePerNight)}</td>
            <td>{item.status}</td>
            <td>
                <Popup
                    trigger={
                        item.status === 'BANNED' ? (
                            <LockOpenIcon
                                className="icon__btn"
                                sx={{ color: 'red', cursor: 'pointer', fontSize: '18px' }}
                            />
                        ) : (
                            <LockIcon
                                className="icon__btn"
                                sx={{ color: 'red', cursor: 'pointer', fontSize: '18px' }}
                            />
                        )
                    }
                    position="bottom center"
                >
                    <div>
                        <p style={{ margin: '0', padding: '5px', fontSize: '14px' }}>
                            {`Bạn chắc chắn muốn ${item.status === 'BANNED' ? 'mở khóa' : 'khóa'} chỗ ở này không?`}
                        </p>
                        <p
                            style={{
                                background: '#ef5350',
                                margin: '0',
                                width: 'auto',
                                paddingLeft: '15px',
                                paddingTop: '5px',
                                paddingBottom: '5px',
                                marginLeft: '75%',
                                cursor: 'pointer',
                                color: 'white'
                            }}
                            onClick={() => handleChangeStatusAccom(item.id, item.status)}
                        >
                            Yes
                        </p>
                    </div>
                </Popup>
            </td>
        </tr>
    );

    return (
        <div className="user__admin">
            <div className="header__customer">
                <h2 className="page-header">Nơi ở được đăng kí</h2>
            </div>

            <div className="row">
                <div className="col l-12">
                    <div className="card-admin">
                        <div className="card__body">
                            <Table
                                limit="10"
                                headData={customerTableHead}
                                renderHead={(item, index) => renderHead(item, index)}
                                bodyData={props?.data}
                                renderBody={(item, index) => renderBody(item, index)}
                            />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AccomAdmin;
