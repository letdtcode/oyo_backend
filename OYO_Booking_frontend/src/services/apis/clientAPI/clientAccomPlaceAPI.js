import axios from '~/services/axios';

const clientAccomPlaceAPI = {
    getAccomPlaceRecommend: async () => {
        const res = await axios.get(`client/accom-place/recommend?pageNumber=0&pageSize=4`);
        return res.data;
    }
};
export default clientAccomPlaceAPI;
