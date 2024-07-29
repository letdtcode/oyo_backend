import axios from 'axios';
import { createAsyncThunk } from '@reduxjs/toolkit';

const cloudName = 'dyv5zrsgj';
const resourceType = 'auto';
const apiUrl = `https://api.cloudinary.com/v1_1/${cloudName}/${resourceType}/upload`;

const cloudinaryAPI = {
    uploadVideoIntro: createAsyncThunk('owner/uploadVideoIntro', async (file) => {
        let formData = new FormData();
        formData.append('upload_preset', 'kcbcnpne');
        formData.append('folder', 'oyo_booking/video');
        formData.append('file', file);
        const res = await axios.post(apiUrl, formData);
        return res.data;
    })
};

export default cloudinaryAPI;
