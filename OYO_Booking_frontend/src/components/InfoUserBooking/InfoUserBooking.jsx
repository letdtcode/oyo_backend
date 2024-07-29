import { useState } from 'react';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import bookingSlice from '~/redux/bookingSlice';
import { useDispatch, useSelector } from 'react-redux';
import { t } from 'i18next';

const InfoUserBooking = () => {
    const dispatch = useDispatch();
    const dataBooking = useSelector((state) => state.booking.info);
    const [open, setOpen] = useState(false);
    const [name, setName] = useState(dataBooking.nameCustomer);
    const [phoneNumber, setPhoneNumber] = useState(dataBooking.phoneNumberCustomer);
    const onClose = () => {
        setOpen(false);
    };
    const handleSave = () => {
        dispatch(bookingSlice.actions.updateInfoUserBooking({ name, phoneNumber }));
        setOpen(false);
    };
    return (
        <>
            <div className="info__booking">
                <div className="info__booking__title">
                    <p className="customer-count__title">{t('title.bookingOfYou.infoOfYou')}</p>
                </div>
                <div className="info__booking__content">
                    <div className="info__booking__content__left">
                        <li>{dataBooking.nameCustomer}</li>
                        <li>{dataBooking.phoneNumberCustomer}</li>
                    </div>
                    <div className="info__booking__content__right">
                        <p onClick={() => setOpen((open) => !open)} className="edit-date">
                            {t('common.edit')}
                        </p>
                    </div>
                </div>
            </div>
            <Dialog open={open} onClose={onClose}>
                <DialogTitle>{t('title.bookingOfYou.infoOfYou')}</DialogTitle>
                <DialogContent>
                    <TextField
                        label={t('title.bookingOfYou.name')}
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        fullWidth
                        margin="dense"
                    />
                    <TextField
                        label={t('title.bookingOfYou.phone')}
                        value={phoneNumber}
                        onChange={(e) => setPhoneNumber(e.target.value)}
                        fullWidth
                        margin="dense"
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose} variant="contained">
                        {t('common.cancel')}
                    </Button>
                    <Button onClick={handleSave} variant="contained" color="primary">
                        {t('common.save')}
                    </Button>
                </DialogActions>
            </Dialog>
        </>
    );
};

export default InfoUserBooking;
