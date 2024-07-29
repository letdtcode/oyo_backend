import React, { useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import CloseIcon from '@mui/icons-material/Close';
import './DialogConvenient.scss';
import { t } from 'i18next';

export default function DialogConvenient(props) {
    const [open, setOpen] = useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };
    return (
        <div className="dialog-convenient">
            <Button variant="outlined" onClick={handleClickOpen} className="btn-show">
                {t('common.convenient')}
            </Button>
            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
                fullWidth={true}
                maxWidth="md"
            >
                <DialogTitle id="alert-dialog-title" style={{ fontSize: '18px', fontWeight: 'bold', width: '600px' }}>
                    {t('title.convenient')}
                    <Button
                        className="closeDialog"
                        style={{ position: 'absolute', right: '8px', top: '8px' }}
                        onClick={handleClose}
                    >
                        <CloseIcon />
                    </Button>
                </DialogTitle>
                <div>
                    {props?.listConvenient?.map((faciCate, index) => (
                        <DialogContent
                            style={{
                                fontSize: '16px',
                                fontWeight: 'bold',
                                textDecorationLine: `${!faciCate.isConfig ? 'none' : 'line-through'}`
                            }}
                            key={index}
                        >
                            {faciCate?.faciCateName}
                            {faciCate?.infoFacilityList.map((facility, i) => (
                                <DialogContentText
                                    id="alert-dialog-description"
                                    style={{
                                        fontSize: '14px',
                                        marginTop: '20px',
                                        textDecorationLine: `${!facility.isConfig ? 'none' : 'line-through'}`
                                    }}
                                    key={i}
                                >
                                    <img src={facility.imageUrl} />
                                    {facility.facilityName}
                                </DialogContentText>
                            ))}
                        </DialogContent>
                    ))}
                </div>
            </Dialog>
        </div>
    );
}
