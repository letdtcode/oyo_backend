import axios from '~/services/axios';

const uploadMedia = {
    multipleFile: async (data) => {
        let formData = new FormData();
        data.forEach((image) => {
            formData.append('files', image);
        });
        const res = await axios.post('/media/cloud/upload-multil',formData);
        return res.data;
    },
    singleFile: async (data) => {
        let formData = new FormData();
        formData.append('file', data);
        const res = await axios.post('/media/cloud/upload',formData);
        return res.data;
    }
};
export default uploadMedia;
