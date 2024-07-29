import axios from '~/services/axios';

const partnerStatisticAPI = {
    getStatisticOfHost: async (year) => {
        const res = await axios.get(`/partner/statistic?year=${year}`);
        return res.data;
    },
    getStatisticHomeByMonthAndYearOfHost: async (year, month) => {
        const res = await axios.get(`/partner/statistic/month?year=${year}&month=${month}&pageNumber=0&pageSize=100`);
        return res.data;
    }
};

export default partnerStatisticAPI;
