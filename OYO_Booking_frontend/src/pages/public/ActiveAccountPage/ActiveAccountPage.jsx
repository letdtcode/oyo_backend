import { useEffect, useState } from 'react';
import authAPI from '~/services/apis/authAPI/authAPI';
import Loading from '~/components/Loading/Loading';
import { useSearchParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import FramePage from '~/components/FramePage/FramePage';
import './ActiveAccountPage.scss';
const ActiveAccountPage = () => {
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState('');
    const [statusCode, setStatusCode] = useState(0);
    const [searchParams] = useSearchParams();
    const email = searchParams.get('email');
    const token = searchParams.get('token');
    const navigate = useNavigate();

    useEffect(() => {
        authAPI
            .verifyTokenMail({ email, token })
            .then((res) => {
                setStatusCode(res.statusCode);
                setMessage(res.message);
                setLoading(false);
            })
            .catch((err) => {
                setLoading(false);
                setMessage(err.errors);
            });
    }, []);
    const goToLogin = () => {
        navigate('/');
    };
    return loading ? (
        <Loading />
    ) : (
        <>
            {statusCode === 200 ? (
                <div className="active__acount__container" style={{ backgroundColor: '#064e3b' }}>
                    <div className="active__account" style={{ backgroundColor: '#065f46' }}>
                        <h2 className="text__active__account">Tài khoản xác thực thành công</h2>
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            width="85"
                            height="85"
                            fill="currentColor"
                            className="bi bi-check-circle-fill animated swing"
                            viewBox="0 0 16 16"
                        >
                            <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z" />
                        </svg>
                        <button className="btn_active_account" onClick={goToLogin}>
                            Đăng nhập ngay
                        </button>
                        <p style={{ marginTop: '20px', fontSize: 18 }}>@OYO Booking</p>
                    </div>
                </div>
            ) : statusCode === 304 ? (
                <div className="active__acount__container" style={{ backgroundColor: '#7F1D1D' }}>
                    <div className="active__account" style={{ backgroundColor: '#991B1B' }}>
                        <h2 className="text__active__account">{message}</h2>
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            width="85"
                            height="85"
                            fill="currentColor"
                            className="bi bi-x-circle-fill animated swing"
                            viewBox="0 0 16 16"
                        >
                            <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM5.354 4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 .708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 7.293 5.354 4.646z" />
                        </svg>
                        <h3>Please check mail again !</h3>

                        <p style={{ marginTop: '20px', fontSize: 18 }}>@OYO Booking</p>
                    </div>
                </div>
            ) : (
                <div className="active__acount__container" style={{ backgroundColor: '#7F1D1D' }}>
                    <div className="active__account" style={{ backgroundColor: '#991B1B' }}>
                        <h2 className="text__active__account">{message}</h2>
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            width="85"
                            height="85"
                            fill="currentColor"
                            className="bi bi-x-circle-fill animated swing"
                            viewBox="0 0 16 16"
                        >
                            <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM5.354 4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 .708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 7.293 5.354 4.646z" />
                        </svg>
                        <h3>Please try again !</h3>

                        <p style={{ marginTop: '20px', fontSize: 18 }}>@OYO Booking</p>
                    </div>
                </div>
            )}
        </>
    );
};
export default ActiveAccountPage;
