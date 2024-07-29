import axiosClient from '~/services/axiosAdmin';
const cmsSurchargeCategoryAPI = {
    getAllSurchargeCategoryWithPaging: async () => {
        const response = await axiosClient.get(`/cms/surcharge-categories/pages?pageNumber=0&pageSize=200`);
        return response.data;
    },
    addSurchargeCategory: async (data) => {
        const response = await axiosClient.post('/cms/surcharge-categories/create', data);
        return response.data;
    },
    updateSurchargeCategory: async (data, id) => {
        const response = await axiosClient.put(`/cms/surcharge-categories/${id}/update`, data);
        return response.data;
    },
    changeStatusSurchargeCategory: async (status, id) => {
        const response = await axiosClient.put(`/cms/surcharge-categories/${id}/change-status?status=${status}`);
        return response.data;
    },
    deleteSurchargeCategory: async (id) => {
        const response = await axiosClient.delete(`/cms/surcharge-categories/${id}/delete`);
        return response.data;
    }
};

export default cmsSurchargeCategoryAPI;
