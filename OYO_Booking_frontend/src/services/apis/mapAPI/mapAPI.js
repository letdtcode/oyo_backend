import axios from 'axios';
const API_KEY_GOOGLE = import.meta.env.VITE_API_KEY_GOOGLE;
const API_KEY_LOCATIONIQ = import.meta.env.VITE_API_KEY_LOCATIONIQ;
const mapAPI = {
    getRoomCategory: async () => {
        const res = await axios.get(
            `https://maps.google.com/maps/api/geocode/json?latlng=10.851631687494017%2C106.76831957957806&key=${API_KEY_GOOGLE}`
        );
        return res.data;
    },
    geoCodeAddress: async (address) => {
        const res = await axios.get(`https://us1.locationiq.com/v1/search?key=${API_KEY_LOCATIONIQ}&q=${address}&format=json&accept-language=vi`);
        return res.data;
    }
};

export default mapAPI;
