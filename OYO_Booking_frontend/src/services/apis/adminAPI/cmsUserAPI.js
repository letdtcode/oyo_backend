import axiosClient from '~/services/axiosAdmin';
const cmsUserAPI = {
    getAllUserWithPaging: async () => {
        const response = await axiosClient.get(`/cms/users/pages?pageNumber=0&pageSize=20`);
        return response.data;
    },
    changeStatusUser: async (status, mail) => {
        const response = await axiosClient.put(`/cms/users/${mail}/change-status?status=${status}`);
        return response.data;
    },
    deleteUser: async (mail) => {
        const response = await axiosClient.delete(`/cms/users/${mail}/delete`);
        return response.data;
    }
};

export default cmsUserAPI;
