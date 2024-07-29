import axios from '~/services/axios';

const clientNotificationAPI = {
    getDataNotificationOfUser: async () => {
        const res = await axios.get(`/client/notification/page?pageNumber=0&pageSize=20`);
        return res.data;
    },
    getDataNotificationUnviewOfUser: async () => {
        const res = await axios.get(`/client/notification/page?pageNumber=0&pageSize=20&viewed=false`);
        return res.data;
    },
    resetAllNotification: async () => {
        const res = await axios.put(`/client/notification/reset-all`);
        return res.data;
    }
};
export default clientNotificationAPI;
