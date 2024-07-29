import { ErrorSharp } from '@mui/icons-material';
import axios from '~/services/axios';

const authAPI = {
    loginRequest: async (data) => {
        const res = await axios.post('auth/signin', data);
        return res.data;
    },
    checkAccount: async (data) => {
        const res = await axios.post(`/public/users/check-mail`, data);
        return res.data;
    },
    verifyTokenMail: async (data) => {
        const res = await axios.post(`/auth/verify?email=${data.email}&token=${data.token}`);
        return res.data;
    },
    registerRequest: async (data) => {
        const res = await axios.post('auth/signup', data);
        return res.data;
    },
    tokenRefreshRequest: async (data) => {
        const res = await axios.post('user/signin', data);
        return res.data;
    },
    changePasswordRequest: async (data) => {
        const res = await axios.put('general/change-password', data);
        return res.data;
    },
    updateInfoRequest: async (data) => {
        const res = await axios.put('general/update-info', data, {
            data: { mail: data.mail }
        });
        return res.data;
    },
    updateAvatarRequest: async (data) => {
        let formData = new FormData();
        formData.append('file', data);
        const res = await axios.put('general/update-avatar', formData);
        return res.data;
    },
    resetPassword: async (data) => {
        const res = await axios.post(`/auth/reset-password?mail=${data}`);
        return res.data;
    }
};

export default authAPI;
