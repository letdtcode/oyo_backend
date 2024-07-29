import axiosClient from '~/services/axiosAdmin';
const cmsFacilityAPI = {
    getAllFacilityWithPaging: async () => {
        const response = await axiosClient.get(`/cms/facilities/pages?pageNumber=0&pageSize=200`);
        return response.data;
    },
    addFacility: async (data) => {
        const response = await axiosClient.post('/cms/facilities/create', data);
        return response.data;
    },
    updateFacility: async (data, id) => {
        const response = await axiosClient.put(`/cms/facilities/${id}/update`, data);
        return response.data;
    },
    changeStatusFacility: async (status, id) => {
        const response = await axiosClient.put(`/cms/facilities/${id}/change-status?status=${status}`);
        return response.data;
    },
    deleteFacility: async (id) => {
        const response = await axiosClient.delete(`/cms/facilities/${id}/delete`);
        return response.data;
    }
};

export default cmsFacilityAPI;
