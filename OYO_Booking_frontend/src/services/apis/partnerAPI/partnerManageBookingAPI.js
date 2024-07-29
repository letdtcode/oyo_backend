import axios from '~/services/axios';

const partnerManageBookingAPI = {
    getWaiting: async () => {
        const res = await axios.get('/partner/booking/pages?status=WAITING&pageNumber=0&pageSize=10');
        return res.data;
    },
    getCheckIn: async () => {
        const res = await axios.get('/partner/booking/pages?status=CHECK_IN&pageNumber=0&pageSize=10');
        return res.data;
    },
    getCheckOut: async () => {
        const res = await axios.get('/partner/booking/pages?status=CHECK_OUT&pageNumber=0&pageSize=10');
        return res.data;
    },
    setCheckIn: async (data) => {
        const res = await axios.put(`/partner/booking/check-in?bookingCode=${data}`);
        return res.data;
    },
    setCheckOut: async (data) => {
        const res = await axios.put(`/partner/booking/check-out?bookingCode=${data}`);
        return res.data;
    },
    getHistoryBooking: async () => {
        const res = await axios.get(`/partner/booking/pages?&pageNumber=0&pageSize=10`);
        return res.data;
    }
};

export default partnerManageBookingAPI;
