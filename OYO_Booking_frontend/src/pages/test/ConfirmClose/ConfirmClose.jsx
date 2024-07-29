import * as React from 'react';
import { t } from 'i18next';
import { useNavigate } from 'react-router-dom';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';

import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

export default function ConfirmClose() {
    const [open, setOpen] = React.useState(false);
    const navigate = useNavigate();

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleConfirm = () => {
        setOpen(false);
        navigate('/host');
    };

    return (
        <div className="dialog-convenient">
            <p className="btn-out" onClick={handleClickOpen} style={{ margin: 0 }}>
                {t('common.close')}
            </p>
            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
                fullWidth={true}
                maxWidth="xs"
            >
                <div>
                    <DialogTitle id="alert-dialog-title" sx={{ fontSize: '18px', fontWeight: 'bold' }}>
                        {t('title.confirmClose')}
                    </DialogTitle>
                    <DialogContent
                        sx={{
                            fontSize: '16px',
                            fontWeight: 'bold',
                            paddingBottom: '0px',
                        }}
                    >
                        <DialogContentText
                            id="alert-dialog-description"
                            sx={{
                                fontSize: '14px',
                                paddingBottom: '0px',
                            }}
                        >
                            {t('contentMain.confirmClose')}
                        </DialogContentText>
                    </DialogContent>
                </div>

                <DialogActions>
                    <Button onClick={handleClose} color="error" sx={{ fontSize: '14px', textTransform: 'none' }}>
                        Close
                    </Button>
                    <Button onClick={handleConfirm} autoFocus sx={{ fontSize: '14px', textTransform: 'none' }}>
                        Confirm
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}
