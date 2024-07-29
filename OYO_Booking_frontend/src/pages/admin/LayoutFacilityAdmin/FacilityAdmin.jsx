import { useState } from 'react';
import Table from '~/components/Table/Table';
import { useSnackbar } from 'notistack';
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';
import cmsFacilityAPI from '~/services/apis/adminAPI/cmsFacilityAPI';
import AddFormFacility from '~/components/Admin/AddFormFacility/AddFormFacility';
import DeleteOutlineIcon from '@mui/icons-material/DeleteOutline';
import './FacilityAdmin.scss';
import UpdateFormFacility from '~/components/Admin/UpdateFormFacility/UpdateFormFacility';

const customerTableHead = ['', 'Tên tiện ích', 'Mã tiện ích', 'Link hình ảnh', 'Loại tiện ích', 'Trạng thái'];

const fieldData = [
    {
        title: 'Tên tiện ích',
        nameRegister: 'facilityName',
        nameRequire: 'Tên tiện ích là bắt buộc',
        placeholder: 'Vd: Giữ xe free...'
    },
    {
        title: 'Link hình ảnh',
        nameRegister: 'imageUrl',
        nameRequire: 'Link hình ảnh là bắt buộc',
        placeholder: 'Vd: http://...'
    },
    {
        title: 'Loại tiện ích',
        nameRegister: 'facilityCateName',
        nameRequire: 'Tên loại tiện ích là bắt buộc',
        placeholder: 'Vd: Tiêu chuẩn...'
    },

    {
        title: 'Mã loại tiện ích',
        nameRegister: 'facilityCateCode',
        nameRequire: 'mã loại tiện ích là bắt buộc',
        placeholder: 'Mã loại tiện ích tương ứng'
    },
    {
        title: 'Trạng thái',
        nameRegister: 'status',
        nameRequire: 'Vui lòng chọn trạng thái'
    }
];

const renderHead = (item, index) => <th key={index}>{item}</th>;

const FacilityAdmin = (props) => {
    const [onAdd, setOnAdd] = useState(false);

    const { enqueueSnackbar } = useSnackbar();

    const renderBody = (item, index, initNum) => (
        <tr key={index}>
            <td>{index + 1 + initNum}</td>
            <td>{item.facilityName}</td>
            <td>{item.facilityCode}</td>
            <td>{item.imageUrl}</td>
            <td>{item.facilityCateName}</td>
            <td>{item.status}</td>
            <td>
                <UpdateFormFacility
                    fieldData={fieldData}
                    data={item}
                    updateData={handleUpdate}
                    dataFacilityCategory={props.dataFacilityCategory}
                />
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
        cmsFacilityAPI
            .addFacility(dataAdd)
            .then((dataResponse) => {
                props.setList([dataResponse.data, ...props.data]);
                enqueueSnackbar('Thêm mới thành công', { variant: 'success' });
            })
            .catch((error) => {

                enqueueSnackbar(`Thêm mới thất bại. ${error.response.data.detail}`, { variant: 'error' });
            });
    };

    const handleDelete = (idDelete) => {
        cmsFacilityAPI
            .deleteFacility(idDelete)
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

        cmsFacilityAPI
            .updateFacility(data, id)
            .then((dataResponse) => {
                Update(dataResponse.data.id, dataResponse.data);
                enqueueSnackbar('Cập nhật thành công', { variant: 'success' });
            })
            .catch((error) => {
                enqueueSnackbar(error.response?.data.detail, { variant: 'error' });
            });
    };

    return (
        <div className="type__bed__admin">
            <div className="header__customer">
                <h2 className="page-header">Tiện ích</h2>
                <button className="btn__add-customer__admin" onClick={() => setOnAdd(!onAdd)}>
                    <p className="text__admin">{onAdd ? 'Danh sách loại giường' : 'Thêm mới'}</p>
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
                <AddFormFacility
                    fieldData={fieldData}
                    addDataNew={handleAddData}
                    dataFacilityCategory={props.dataFacilityCategory}
                />
            )}
        </div>
    );
};

export default FacilityAdmin;
