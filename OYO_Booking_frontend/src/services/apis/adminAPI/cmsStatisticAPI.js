import axiosClient from '~/services/axiosAdmin';
const cmsStatisticAPI = {
    getStatisticOfAdmin: async (year) => {
        const response = await axiosClient.get(`/cms/statistic?year=${year}`);
        return response.data;
    },
    getStatisticForGuestOfAdmin: async (date) => {
        const response = await axiosClient.get(`/cms/statistic/guest?dateStart=${date[0]}&dateEnd=${date[1]}`);
        return response.data;
    },
    getStatisticForHostOfAdmin: async (date) => {
        const response = await axiosClient.get(`/cms/statistic/host?dateStart=${date[0]}&dateEnd=${date[1]}`);
        return response.data;
    },
    getStatisticForAccomPlaceOfAdmin: async (date) => {
        const response = await axiosClient.get(`/cms/statistic/accom-place?dateStart=${date[0]}&dateEnd=${date[1]}`);
        return response.data;
    },
    getStatisticForTransactionOfAdmin: async (date) => {
        const response = await axiosClient.get(`/cms/statistic/transaction?dateStart=${date[0]}&dateEnd=${date[1]}`);
        return response.data;
    },
    getStatisticChart: async (year, type) => {
        const response = await axiosClient.get(`/cms/statistic/chart?year=${year}&type=${type}`);
        return response.data;
    }
};

export default cmsStatisticAPI;
