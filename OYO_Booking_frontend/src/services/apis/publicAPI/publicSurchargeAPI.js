import axios from '~/services/axios';

const publicSurchargeAPI = {
    getAllSurcharge: async () => {
        const res = await axios.get('/public/surcharges');
        return res.data;
    }
};
export default publicSurchargeAPI;
