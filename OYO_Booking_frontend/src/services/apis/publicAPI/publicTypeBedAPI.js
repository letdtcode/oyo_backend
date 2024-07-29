import axios from '~/services/axios';

const publicTypeBedAPI = {
    getAllTypeBed: async () => {
        const res = await axios.get('/public/type-beds?pageNumber=0&pageSize=100');
        return res.data;
    }
};
export default publicTypeBedAPI;
