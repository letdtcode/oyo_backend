import axios from '~/services/axios';

const publicAccomPlaceAPI = {
    getAllAccomCategoryInfo: async () => {
        const response = await axios.get('/public/accoms/cate-info');
        return response.data;
    },
    getRoomDetail: async (id) => {
        const res = await axios.get(`/public/accoms/${id}/detail`);
        return res.data;
    },
    getRoomCategory: async () => {
        const res = await axios.get(`/public/accoms/cate-info`);
        return res.data;
    },
    getTophome: async (data) => {
        const res = await axios.get(`/public/accoms/top?pageNumber=${data.number}&pageSize=${data.size}`);
        return res.data;
    },
    getListHomeOfHost: async (data) => {
        const res = await axios.post(`/public/homes/${data}/detail`);
        return res;
    },
    checkBooking: async (data) => {
        const res = await axios.post(`/public/accoms/check-booking`, data);
        return res.data;
    },
    getReviewHome: async (data) => {
        const res = await axios.get(`/public/accoms/${data}/reviews`);
        return res.data;
    },
    getTopHomeOfProvince: async () => {
        const res = await axios.get(`/public/provinces/top?pageNumber=0&pageSize=11`);
        return res.data;
    },
    getAllRoomsWithFilter: async (data) => {    
        const res = await axios.get(`/public/accoms/filters?${data.queryParams}&pageNum=${data.pageNum}&pageSize=${data.pageSize}`);
        return res.data;
    },
    getSearchHome : async (data) => {
        const res = await axios.get(`/public/accoms/search?keyword=${data}&pageNum=0&pageSize=20`);
        return res.data;
    }
};
export default publicAccomPlaceAPI;
