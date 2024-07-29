import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Fade from '@mui/material/Fade';
import bookingAPI from '~/services/apis/clientAPI/clientBookingAPI';
import { cancellationPolicyToTime } from '~/utils/cancellationPolicy';
import './ModalConfirmDelete.scss';
import { useState, useEffect } from 'react';
import { useSnackbar } from 'notistack';
import { t } from 'i18next';

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 500,
    boxShadow: 24,
    p: 4,
    paddingBottom: '25px'
};

export default function ModalConfirmDelete(props) {
    const [cancelBooking, setCancelBooking] = useState(true);
    const [open, setOpen] = useState(false);
    const [reason, setReason] = useState('');
    const [otherReason, setOtherReason] = useState('');
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const { enqueueSnackbar } = useSnackbar();

    useEffect(() => {
        const currentDate = new Date();
        const checkInParts = props.data.checkIn.split('/');
        const checkInDate = new Date(checkInParts[2], checkInParts[1] - 1, checkInParts[0]);
        checkInDate.setHours(12, 0, 0, 0);
        const timeToCancel = cancellationPolicyToTime(props.data.cancellationPolicy);
        const timeToCancelDate = new Date(checkInDate.getTime() - timeToCancel * 24 * 60 * 60 * 1000);
        if (currentDate.getTime() > timeToCancelDate.getTime()) {
            setCancelBooking(false);
        }
    }, []);
    const handleCancelBooking = async () => {
        await bookingAPI
            .cancelBooking({
                bookingCode: props.idRemove,
                cancelReason: reason === 'Lý do khác' ? otherReason : reason
            })
            .then((data) => {
                if (data.statusCode === 200) {
                    enqueueSnackbar(t('message.cancelSuccess'), { variant: 'success' });
                } else {
                    enqueueSnackbar(data.data.message, { variant: 'warning' });
                }
                props.handleReload();
                setOpen(false);
            })
            .catch((error) => {
                enqueueSnackbar(error.response?.data.message, { variant: 'error' });
            });
    };
    const handleReasonChange = (e) => {
        setReason(e.target.value);
    };
    return (
        <div>
            {cancelBooking ? (
                <button className="CANCEL" onClick={handleOpen}>
                    {t('common.cancelBooking')}
                </button>
            ) : (
                <><p>Không được phép hủy</p></>
            )}

            <Modal
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                open={open}
                onClose={handleClose}
                closeAfterTransition
            >
                <Fade in={open}>
                    <Box className="paper form__cancel-booking" sx={style}>
                        {/* <Typography id="transition-modal-title" variant="h4" component="h2">
                            {t('title.cancelPopup')}
                        </Typography> */}
                        <header>Chọn lý do muốn hủy</header>
                        <div className="reason">
                            <div className="reason-item">
                                <input
                                    type="radio"
                                    id="reason1"
                                    name="reason"
                                    value="Chỗ ở không phù hợp"
                                    checked={reason === 'Chỗ ở không phù hợp'}
                                    onChange={handleReasonChange}
                                />
                                <label htmlFor="reason1">Chỗ ở không phù hợp</label>
                            </div>
                            <div className="reason-item">
                                <input
                                    type="radio"
                                    id="reason2"
                                    name="reason"
                                    value="Đã tìm được chỗ ở khác"
                                    checked={reason === 'Đã tìm được chỗ ở khác'}
                                    onChange={handleReasonChange}
                                />
                                <label htmlFor="reason2">Đã tìm được chỗ ở khác</label>
                            </div>
                            <div className="reason-item">
                                <input
                                    type="radio"
                                    id="reason3"
                                    name="reason"
                                    value="Không thể di chuyển"
                                    checked={reason === 'Không thể di chuyển'}
                                    onChange={handleReasonChange}
                                />
                                <label htmlFor="reason3">Không thể di chuyển</label>
                            </div>
                            <div className="reason-item">
                                <input
                                    type="radio"
                                    id="reason4"
                                    name="reason"
                                    value="Thay đổi kế hoạch"
                                    checked={reason === 'Thay đổi kế hoạch'}
                                    onChange={handleReasonChange}
                                />
                                <label htmlFor="reason4">Thay đổi kế hoạch</label>
                            </div>
                            <div className="reason-item">
                                <input
                                    type="radio"
                                    id="reason5"
                                    name="reason"
                                    value="Lý do khác"
                                    checked={reason === 'Lý do khác'}
                                    onChange={handleReasonChange}
                                />
                                <label htmlFor="reason4">Lý do khác</label>
                            </div>
                            {reason === 'Lý do khác' && (
                                <div className="other-reason">
                                    <label htmlFor="other-reason">Nhập lý do khác:</label>
                                    <textarea
                                        type="text"
                                        id="other-reason"
                                        className="input__other-reason"
                                        value={otherReason}
                                        rows={4} // Số dòng hiển thị ban đầu
                                        cols={50} // Số ký tự hiển thị trên mỗi dòng
                                        onChange={(e) => setOtherReason(e.target.value)}
                                    />
                                </div>
                            )}
                        </div>

                        <div style={{ display: 'flex', justifyContent: 'right', marginTop: '10px' }}>
                            <button onClick={handleClose} className="no-btn">
                                {t('common.return')}
                            </button>
                            <button onClick={handleCancelBooking} className="yes-btn">
                                {t('common.cancelBooking')}
                            </button>
                        </div>
                    </Box>
                </Fade>
            </Modal>
        </div>
    );
}
