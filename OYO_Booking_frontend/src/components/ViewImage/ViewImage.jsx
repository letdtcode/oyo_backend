import { useEffect, useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import globalSlice from '~/redux/globalSlice';
import { useSelector, useDispatch } from 'react-redux';
import './ViewImage.scss';
import { t } from 'i18next';

export default function ViewIamge(props) {
    const viewImages = useSelector((state) => state.global.viewImages);
    const dispatch = useDispatch();
    const [open, setOpen] = useState(false);
    useEffect(() => {
        if (props.viewImages.length > 0) {
            setOpen(true);
        }
    }, [props.viewImages]);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        dispatch(globalSlice.actions.setViewImg([]));
        setOpen(false);
    };
    return (
        <div>
            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
                fullWidth={true}
                maxWidth="fullWidth"
                PaperProps={{ style: { background: 'transparent', boxShadow: 'none' } }}
            >
                <div className="dialog__imgs" onClick={handleClose}>
                    {/* <DialogTitle
                        id="alert-dialog-title"
                        style={{ fontSize: '18px', fontWeight: 'bold', marginBottom: '20px' }}
                    >
                        <Button onClick={handleClose} style={{ fontSize: '14px' }}>
                            X
                        </Button>
                    </DialogTitle> */}
                    <DialogContent className="container__imgs">
                        {props?.viewImages?.map((img, index) => (
                            <img key={index} className="img" src={img}></img>
                        ))}
                    </DialogContent>
                </div>
            </Dialog>
        </div>
    );
}
