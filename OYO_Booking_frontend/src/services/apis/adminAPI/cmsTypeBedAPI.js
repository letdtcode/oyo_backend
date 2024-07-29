import axiosClient from '~/services/axiosAdmin';
const cmsTypeBedAPI = {
    getAllTypeBedWithPaging: async () => {
        const response = await axiosClient.get(`/cms/type-beds/pages?pageNumber=0&pageSize=20`);
        return response.data;
    },
    addTypeBed: async (data) => {
        const response = await axiosClient.post('/cms/type-beds/create', data);
        return response.data;
    },
    updateTypeBed: async (data, id) => {
        const response = await axiosClient.put(`/cms/type-beds/${id}/update`, data);
        return response.data;
    },
    changeStatusTypeBed: async (status, id) => {
        const response = await axiosClient.put(`/cms/type-beds/${id}/change-status?status=${status}`);
        return response.data;
    },
    deleteTypeBed: async (id) => {
        const response = await axiosClient.delete(`/cms/type-beds/${id}/delete`);
        return response.data;
    }
};

export default cmsTypeBedAPI;
