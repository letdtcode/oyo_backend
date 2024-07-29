import axiosClient from '~/services/axiosAdmin';
const cmsProvinceAPI = {
    addProvince: async (data) => {
        const response = await axiosClient.post('/cms/provinces/create', data);
        return response.data;
    },
    updateProvince: async (data, id) => {
        const response = await axiosClient.put(`/cms/provinces/${id}/update`, data);
        return response.data;
    },
    deleteProvince: async (id) => {
        const response = await axiosClient.delete(`/cms/provinces/${id}/delete`);
        return response.data;
    }
};

export default cmsProvinceAPI;
