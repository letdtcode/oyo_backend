import axiosClient from '~/services/axiosAdmin';
const cmsAccomCategoryAPI = {
    getAllAcommCategoryWithPaging: async () => {
        const response = await axiosClient.get(`/cms/accom-categories/pages?pageNumber=0&pageSize=20`);
        return response.data;
    },
    addAccomCategory: async (data) => {
        const response = await axiosClient.post('/cms/accom-categories/create', data);
        return response.data;
    },
    updateAccomCategory: async (data, id) => {
        const response = await axiosClient.put(`/cms/accom-categories/${id}/update`, data);
        return response.data;
    },
    changeStatusAccomCategory: async (status, id) => {
        const response = await axiosClient.put(`/cms/accom-categories/${id}/change-status?status=${status}`);
        return response.data;
    },
    deleteAccomCategory: async (id) => {
        const response = await axiosClient.delete(`/cms/accom-categories/${id}/delete`);
        return response.data;
    }
};

export default cmsAccomCategoryAPI;
