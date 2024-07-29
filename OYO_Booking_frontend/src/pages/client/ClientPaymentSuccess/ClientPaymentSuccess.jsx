import logoOYO from '~/assets/logo.svg';
import './ClientPaymentSuccess.scss';

const ClientPaymentSuccess = () => {
    return (
        <div className="client-payment-success">
            <div className="payment-success-container">
                <img src={logoOYO} alt="company logo" />
                <FontAwesomeIcon icon="fa-regular fa-check" size="2xl" style={{ color: '#63E6BE' }} />
                <p>Thanh toán thành công</p>
                <button>Đóng cửa sổ này</button>
            </div>
        </div>
    );
};

export default ClientPaymentSuccess;
