// import { useEffect } from 'react';
// import axios from 'axios';
// import sha512 from 'js-sha512';
// import moment from 'moment-timezone';
// import bookingSlice from '~/redux/bookingSlice';
// import { useDispatch, useSelector } from 'react-redux';

// const VNPay = (props) => {
//     const { pricePayment, booking, canBooking, errors } = props;
//     const VNPay = useSelector((state) => state.booking.VNPay);
//     const urlParams = new URLSearchParams(window.location.search);
//     const vnp_TransactionStatus = urlParams.get('vnp_TransactionStatus');
//     const vnp_TxnRef = urlParams.get('vnp_TxnRef');
//     const vnp_SecureHash = urlParams.get('vnp_SecureHash');
//     const dispatch = useDispatch();

//     useEffect(() => {
//         if (vnp_TransactionStatus === '00' && vnp_TxnRef == VNPay.vnp_TxnRef) {
//             const params = new URLSearchParams();
//             urlParams.forEach((value, key) => {
//                 if (key !== 'vnp_SecureHash') {
//                     params.append(key, value);
//                 }
//             });
//             const queryString = params.toString();
//             const secretKey = 'CLMXUQRJDTHASLQRMTNIMVVCMCRVRHBT';
//             const vnp_SecureHash = sha512.hmac.create(secretKey).update(queryString).hex();
//             if (vnp_SecureHash === urlParams.get('vnp_SecureHash')) {
//                 booking();
//             }
//         }
//     }, [vnp_TransactionStatus, vnp_TxnRef, vnp_SecureHash]);

//     const createPaymentUrl = async () => {
//         try {
//             const ipAddr = await axios.get('https://api.ipify.org?format=json').then((response) => response.data.ip);

//             const createDate = moment.tz('Asia/Ho_Chi_Minh').format('YYYYMMDDHHmmss');
//             const expireDate = moment.tz('Asia/Ho_Chi_Minh').add(15, 'minutes').format('YYYYMMDDHHmmss');
//             const randomTxnRef = Math.floor(10000000 + Math.random() * 90000000);
//             let vnp_Params = {
//                 vnp_Amount: pricePayment * 100,
//                 vnp_BankCode: 'NCB',
//                 vnp_Command: 'pay',
//                 vnp_CreateDate: createDate,
//                 vnp_CurrCode: 'VND',
//                 vnp_ExpireDate: expireDate,
//                 vnp_IpAddr: ipAddr,
//                 vnp_Locale: 'vn',
//                 vnp_OrderInfo: 'Thanh toan OYO',
//                 vnp_OrderType: 'other',
//                 vnp_ReturnUrl: 'http://localhost:5173/booking',
//                 vnp_TmnCode: 'EXLRT5HV',
//                 vnp_TxnRef: randomTxnRef,
//                 vnp_Version: '2.1.0'
//             };
//             // Tạo chuỗi query từ vnp_Params sử dụng URLSearchParams
//             const params = new URLSearchParams();
//             for (const key in vnp_Params) {
//                 params.append(key, vnp_Params[key]);
//             }
//             const queryString = params.toString();

//             // Tạo mã băm từ chuỗi query và secretKey
//             const secretKey = 'CLMXUQRJDTHASLQRMTNIMVVCMCRVRHBT'; // Thay thế 'your_secret_key_here' bằng secretKey thực của bạn
//             const vnp_SecureHash = sha512.hmac.create(secretKey).update(queryString).hex();
//             const vnpUrl =
//                 'https://sandbox.vnpayment.vn/paymentv2/vpcpay.html?' +
//                 queryString +
//                 '&vnp_SecureHash=' +
//                 vnp_SecureHash;
//             dispatch(bookingSlice.actions.createVNPay(vnp_Params['vnp_TxnRef']));
//             window.location.href = vnpUrl;
//         } catch (error) {
//             console.error('Error creating payment URL:', error);
//         }
//     };

//     return (
//         <div>
//             <button
//                 className="btn__booking"
//                 onClick={createPaymentUrl}
//                 disabled={Object.keys(errors).length !== 0 || !canBooking}
//             >
//                 Chuyển đến trang thanh toán
//             </button>
//         </div>
//     );
// };

// export default VNPay;
