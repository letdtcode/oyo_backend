import axiosClient from '~/services/axiosAdmin';
const cmsAccomPlaceAPI = {
    getAllAcommPlaceWithPaging: async () => {
        const response = await axiosClient.get(`/cms/accoms/pages?pageNumber=0&pageSize=10&status=APPROVED`);
        return response.data;
    },
    changeStatusAccomPlace: async (status, id) => {
        const response = await axiosClient.put(`/cms/accoms/${id}/change-status?status=${status}`);
        return response.data;
    },
    deleteAccomPlace: async (id) => {
        const response = await axiosClient.delete(`/cms/accoms/${id}/delete`);
        return response.data;
    },
    getAllAcommPlaceWatting: async () => {
        const response = await axiosClient.get(`/cms/accoms/pages?pageNumber=0&pageSize=10&status=WAITING_FOR_APPROVAL`);
        return response.data;
    },
    changeStatusAccomPlace: async (status, id) => {
        const response = await axiosClient.put(`/cms/accoms/${id}/change-status?status=${status}`);
        return response.data;
    },
    deleteAccomPlace: async (id) => {
        const response = await axiosClient.delete(`/cms/accoms/${id}/delete`);
        return response.data;
    },
    approveAccomPlace: async (id) => {
        const response = await axiosClient.post (`/cms/accoms/${id}/approve-accom`);
        return response.data;
    },

    getDetailAccomPlace: async (id) => {
        const response = await axiosClient.get(`/cms/accoms/${id}/detail`);
        return response.data;
    },
};

export default cmsAccomPlaceAPI;
