import { useState } from 'react';
import { Modal } from '@mui/material';
import Box from '@mui/material/Box';
import { IconBrandPaypal } from '@tabler/icons-react';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import './ModalConfirmBooking.scss';

const ModalConfirmBooking = ({ open }) => {
    // const [open, setOpen] = useState(false);
    // const handleOpen = () => setOpen(true);
    // const handleClose = () => setOpen(false);
    return (
        <Modal open={open}>
            <div className="modal-container">
                <div className="modal-content modal-confirm-booking">
                    <div className="modal-confirm-booking__header">
                        <Typography variant="h6">Thông báo xác nhận đặt phòng</Typography>
                    </div>
                    <div className="modal-confirm-booking__content">
                        <Typography style={{ marginTop: 5 }}>
                            Bạn có muốn đặt phòng với thông tin và hình thức thanh toán sau?
                        </Typography>
                        <div className="modal-confirm-booking__payment">
                            <IconBrandPaypal />
                            <span>Thanh toán Paypal</span>
                        </div>
                    </div>
                    <div className="modal-confirm-booking__footer">
                        <Button variant="outlined">Hủy</Button>
                        <Button variant="contained">Xác nhận đặt phòng</Button>
                    </div>
                </div>
            </div>
        </Modal>
    );
};

export default ModalConfirmBooking;
