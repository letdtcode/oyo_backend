import Modal from '@mui/material/Modal/Modal';
import TimeOutSvg from '~/assets/svg/icon_time_out.svg';
import './ModalTimeUp.scss';

const ModalTimeUp = ({ open, handleContinueBooking }) => {
    return (
        <>
            <Modal aria-labelledby="unstyled-modal-title" aria-describedby="unstyled-modal-description" open={open}>
                <div className="modal-container">
                    <div className="modal-time-up">
                        <div className="modal-time-up__header">
                            <img src={TimeOutSvg}></img>
                        </div>
                        <div className="modal-time-up__content">
                            <h2>Thời gian hoàn tất thanh toán đã hết!</h2>
                            <p>Bạn có muốn tiếp tục đặt phòng này? Giá có thể sẽ tăng nếu bạn rời đi bây giờ.</p>
                        </div>
                        <div className="modal-time-up__footer">
                            <button className="modal-button choose-another">Chọn phòng khác</button>
                            <button className="modal-button continue" onClick={handleContinueBooking}>
                                Tiếp tục đặt phòng{' '}
                            </button>
                        </div>
                    </div>
                </div>
            </Modal>
        </>
    );
};

export default ModalTimeUp;
