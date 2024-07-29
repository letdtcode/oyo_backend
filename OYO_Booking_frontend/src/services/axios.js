import axios from 'axios';
import { getToken, getRefreshToken, updateToken } from './token';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
const instance = axios.create({
    baseURL: API_BASE_URL,
    timeout: 20000,
    validateStatus: function (status) {
        return status >= 200 && status < 400;
    }
});
instance.interceptors.request.use(
    (config) => {
        const token = getToken();

        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);
instance.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalConfig = error?.config;

        if (error.response && error.response.status === 401) {
            const refreshToken = getRefreshToken();
            if (refreshToken) {
                try {
                    let newAccessToken;
                    await axios
                        .post(`${API_BASE_URL}/auth/refresh-token`, { refreshToken: refreshToken })
                        .then((res) => {
                            updateToken(res.data.data.accessToken);
                            newAccessToken = res.data.data.accessToken;
                        })
                        .catch((err) => {
                            localStorage.removeItem('accessToken');
                            localStorage.removeItem('refreshToken');
                            localStorage.removeItem('persist:root');
                            window.location.reload();
                        });
                    originalConfig.headers['Authorization'] = `Bearer ${newAccessToken}`;
                    return axios(originalConfig);
                } catch {
                    (err) => {
                        return Promise.reject(err);
                    };
                }
            }
        }
        return Promise.reject(error);
    }
);
export default instance;
