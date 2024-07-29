import { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import { useSnackbar } from 'notistack';
import './DialogCountOfRoom.scss';
import CountNumberBedOfRoom from '../CountNumber/CountNumberBedOfRoom';
import cmsTypeBedAPI from '~/services/apis/adminAPI/cmsTypeBedAPI';

export default function DialogCountOfRoom(props) {
    const [open, setOpen] = useState(false);
    const [dataSetBedCount, setDataSetBedCount] = useState([]);
    const [listBedOfRoom, setListBedOfRoom] = useState([]);

    const { enqueueSnackbar } = useSnackbar();

    useEffect(() => {
        cmsTypeBedAPI.getAllTypeBedWithPaging().then((dataBed) => {
            setListBedOfRoom(dataBed?.data?.content);
        });
    }, [props.roomOfHomeId]);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const Update = (id, data) => {
        props.setListRoomOfHome(
            props.listRoomOfHome?.map((item) => {
                if (item.id === id) {
                    item.descriptionOfBed = data;
                }
                return item;
            })
        );
    };

    const handleSetDataBedCount = (value) => {
        if (!dataSetBedCount.some((check) => check.categoryId === value.categoryId)) {
            setDataSetBedCount([...dataSetBedCount, { ...value, roomOfHomeId: props?.roomOfHomeId }]);
        } else {
            for (const obj of dataSetBedCount) {
                if (obj.categoryId === value.categoryId) {
                    obj.amount = value.amount;
                    break;
                }
            }
        }
    };

    const handleSave = () => {
        const newCount = {
            listBedOfHomeDetail: dataSetBedCount
        };
        roomOfHomeApi
            .saveCountBedOfHome(newCount)
            .then((data) => {
                enqueueSnackbar('Lưu thành công', { variant: 'success' });
                setOpen(false);
                Update(props.roomOfHomeId, data.data.bedDescription);
            })
            .catch((error) => {
                enqueueSnackbar(error.response?.data.message, { variant: 'error' });
            });
    };

    return (
        <div className="dialog-count-of__room">
            <p onClick={handleClickOpen} className="edit-count-bed" style={{ color: 'black' }}>
                Thêm
            </p>
            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <div>
                    <DialogTitle
                        id="alert-dialog-title"
                        sx={{ fontSize: '18px', fontWeight: 'bold', width: '600px', marginBottom: '20px' }}
                    >
                        {'Khách có thể sử dụng những khu vực nào?'}
                    </DialogTitle>
                    <DialogContent sx={{ fontWeight: 'bold' }}>
                        {listBedOfRoom?.map((bed, index) => (
                            <div key={index}>
                                <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                                    <p style={{ fontSize: '15px' }}>{bed?.name}</p>
                                    <CountNumberBedOfRoom
                                        handleSetDataBedCount={handleSetDataBedCount}
                                        categoryId={bed?.id}
                                        countInit={bed?.numberOfRoom}
                                    />
                                </div>
                                <hr />
                            </div>
                        ))}
                    </DialogContent>
                </div>

                <DialogActions>
                    <Button onClick={handleClose} color="error" sx={{ fontSize: '14px', textTransform: 'none' }}>
                        Close
                    </Button>
                    <Button onClick={handleSave} autoFocus sx={{ fontSize: '14px', textTransform: 'none' }}>
                        Lưu
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}
