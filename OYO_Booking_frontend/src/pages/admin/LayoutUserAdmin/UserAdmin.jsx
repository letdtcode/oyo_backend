import { useSnackbar } from 'notistack';

import Popup from 'reactjs-popup';
import LockIcon from '@mui/icons-material/Lock';
import LockOpenIcon from '@mui/icons-material/LockOpen';

import Table from '~/components/Table/Table';
import cmsUserAPI from '~/services/apis/adminAPI/cmsUserAPI';
import './UserAdmin.scss';

const customerTableHead = ['', 'Tên đầy đủ', 'Email', 'Ngày sinh', 'Số điện thoại', 'User name', 'Trạng thái'];

const renderHead = (item, index) => <th key={index}>{item}</th>;

const UserAdmin = (props) => {
    const { enqueueSnackbar } = useSnackbar();
    const handleChangeStatusAccount = (userMail, userStatus) => {
        const status = userStatus === 'BANNED' ? 'ENABLE' : 'BANNED';
        cmsUserAPI
            .changeStatusUser(status, userMail)
            .then((response) => {
                const updatedUserList = props.data.map((user) => {
                    if (user.mail === userMail) {
                        return { ...user, status: status };
                    }
                    return user;
                });
                props.setListUser(updatedUserList);
                enqueueSnackbar(`${status === 'BANNED' ? 'Khóa' : 'Mở khóa'} tài khoản thành công`, {
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
            <td>
                {item.firstName} {item.lastName}
            </td>
            <td>{item.mail}</td>
            <td>{item.dateOfBirth}</td>
            <td>{item.phone ? item.phone : 'Chưa cập nhật'}</td>
            <td>{item.userName}</td>
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
                    <div style={{ backgroundColor: '#CBE2F2', borderRadius: 4 }}>
                        <p style={{ marginBottom: 0, padding: '5px', fontSize: '14px' }}>
                            {`Bạn chắc chắn muốn ${item.status === 'BANNED' ? 'mở khóa' : 'khóa'} tài khoản này không?`}
                        </p>
                        <p
                            style={{
                                borderRadius: 4,
                                background: '#ef5350',
                                margin: '0',
                                width: 'auto',
                                paddingLeft: '15px',
                                paddingTop: '5px',
                                paddingBottom: '5px',
                                marginLeft: '75%',
                                marginRight: '5px',
                                marginBottom: '20px',
                                cursor: 'pointer',
                                color: 'white'
                            }}
                            onClick={() => handleChangeStatusAccount(item.mail, item.status)}
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
                <h2 className="page-header">Người dùng</h2>
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

export default UserAdmin;
