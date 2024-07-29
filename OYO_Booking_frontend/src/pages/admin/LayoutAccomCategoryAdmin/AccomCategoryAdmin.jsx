import { useState } from 'react';
import Table from '~/components/Table/Table';
import { useSnackbar } from 'notistack';
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';
import cmsAccomCategoryAPI from '~/services/apis/adminAPI/cmsAccomCategoryAPI';
import UpdateForm from '~/components/Admin/UpdateForm/UpdateForm';
import AddForm from '~/components/Admin/AddForm/AddForm';
import DeleteOutlineIcon from '@mui/icons-material/DeleteOutline';
import './AccomCategoryAdmin.scss';

const customerTableHead = ['', 'Tên loại hình cho thuê', 'Mô tả', 'Icon', 'Trạng thái'];

const fieldData = [
    {
        title: 'Tên loại hình cho thuê',
        nameRegister: 'accomCateName',
        nameRequire: 'Tên loại hình cho thuê là bắt buộc',
        placeholder: 'Vd: Nhà trên cây...'
    },
    {
        title: 'Mô tả',
        nameRegister: 'description',
        nameRequire: 'Mô tả loại hình cho thuê là bắt buộc',
        placeholder: 'Vd: Gần bãi biển...'
    },
    {
        title: 'Link hình ảnh',
        nameRegister: 'icon',
        nameRequire: 'Link hình ảnh là bắt buộc',
        placeholder: 'Vd: http://image...'
    },
    {
        title: 'Trạng thái',
        nameRegister: 'status',
        nameRequire: 'Vui lòng chọn status'
    }
];

const renderHead = (item, index) => <th key={index}>{item}</th>;

const AccomCategoryAdmin = (props) => {
    const [onAdd, setOnAdd] = useState(false);

    const { enqueueSnackbar } = useSnackbar();

    const renderBody = (item, index, initNum) => (
        <tr key={index}>
            <td>{index + 1 + initNum}</td>
            <td>{item.accomCateName}</td>
            <td>{item.description}</td>
            <td>{item.icon}</td>
            <td>{item.status}</td>
            <td>
                <UpdateForm fieldData={fieldData} data={item} updateData={handleUpdate} />
            </td>
            <td>
                <Popup
                    trigger={
                        <DeleteOutlineIcon
                            className="icon__btn"
                            sx={{ color: 'red', cursor: 'pointer', fontSize: 25 }}
                        />
                    }
                    position="bottom center"
                >
                    <div>
                        <p style={{ margin: '0', padding: '5px', fontSize: '14px' }}>
                            Bạn chắc chắn muốn xóa dữ liệu này không?
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
                            onClick={() => handleDelete(item.id)}
                        >
                            Yes
                        </p>
                    </div>
                </Popup>
            </td>
        </tr>
    );

    const handleAddData = (data) => {
        const dataAdd = {
            ...data
        };
        cmsAccomCategoryAPI
            .addAccomCategory(dataAdd)
            .then((dataResponse) => {
                props.setList([dataResponse.data, ...props.data]);
                enqueueSnackbar('Thêm mới thành công', { variant: 'success' });
            })
            .catch((error) => {
                enqueueSnackbar(`Thêm mới thất bại\n ${error.response.data.detail}`, { variant: 'error' });
            });
    };

    const handleDelete = (idDelete) => {
        cmsAccomCategoryAPI
            .deleteAccomCategory(idDelete)
            .then(() => {
                const dataAfterDelete = props.data.filter((dataDelete) => {
                    return dataDelete.id !== idDelete;
                });
                props.setList([...dataAfterDelete]);
                enqueueSnackbar('Xóa thành công', { variant: 'success' });
            })
            .catch((error) => {
                enqueueSnackbar(error.response?.data.message, { variant: 'error' });
            });
    };

    const Update = (id, data) => {
        props.setList(
            props.data?.map((item) => {
                if (item.id === id) {
                    item = data;
                }
                return item;
            })
        );
    };

    const handleUpdate = (data, id) => {
        cmsAccomCategoryAPI
            .updateAccomCategory(data, id)
            .then((dataResponse) => {
                Update(dataResponse.data.id, dataResponse.data);
                enqueueSnackbar('Cập nhật thành công', { variant: 'success' });
            })
            .catch((error) => {
                enqueueSnackbar(error.response?.data.message, { variant: 'error' });
            });
    };

    return (
        <div className="accom__category__admin">
            <div className="header__customer">
                <h2 className="page-header">Loại hình cho thuê</h2>
                <button className="btn__add-customer__admin" onClick={() => setOnAdd(!onAdd)}>
                    <p className="text__admin">{onAdd ? 'Danh sách loại hình cho thuê' : 'Thêm mới'}</p>
                </button>
            </div>

            {!onAdd ? (
                <div className="row">
                    <div className="col l-12">
                        <div className="card-admin">
                            <div className="card__body">
                                <Table
                                    limit="10"
                                    headData={customerTableHead}
                                    renderHead={(item, index) => renderHead(item, index)}
                                    bodyData={props?.data}
                                    renderBody={(item, index, initNum) => renderBody(item, index, initNum)}
                                />
                            </div>
                        </div>
                    </div>
                </div>
            ) : (
                <AddForm fieldData={fieldData} addDataNew={handleAddData} />
            )}
        </div>
    );
};

export default AccomCategoryAdmin;
