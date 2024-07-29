import axios from '~/services/axios';

const publicFacilityAPI = {
    getAllDataFacility: async () => {
        const res = await axios.get('/public/facilities/get-all');
        return res.data;
    },
    updateActiveConvenientItem: async () => {
        const res = await axios.get('/public/facilities/get-all');
        return res.data;
    }
};
export default publicFacilityAPI;
