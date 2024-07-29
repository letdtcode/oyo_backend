import axios from 'axios';

const API_KEY = import.meta.env.VITE_API_KEY_GOOGLE;
const API_URL = import.meta.env.VITE_API_URL_TRANSLATE;

export const translateText = async (text) => {
    const target = localStorage.getItem('selectedLanguage');
    if (target === 'vi') {
        return text;
    } else if(text) {
        const response = await axios.post(`${API_URL}?key=${API_KEY}`, {
            q: text,
            target: target
        });
        return response.data.data.translations[0].translatedText;
    }
    else {
        return text;
    }
};

export const transLateListTitle = async (data) => {
    let result = data;
    const fieldsToTranslate = ['accomName', 'addressGeneral', 'accomCateName'];
    for (const field of fieldsToTranslate) {
        result[field] = await translateText(result[field]);
    }
    return result;
}

export const transLateProvince = async (data) => {
    let result = data;
    const fieldsToTranslate = ['provinceName'];
    for (const field of fieldsToTranslate) {
        result[field] = await translateText(result[field]);
    }
    return result;
}

export const transLateRoom = async (data) => {
    let result = data;

    const fieldsToTranslate = ['accomName', 'description', 'addressDetail', 'accomCateName', 'addressGeneral', 'guide'];
    for (const field of fieldsToTranslate) {
        result[field] = await translateText(result[field]);
    }
    
    result.facilityCategoryList = await Promise.all(result.facilityCategoryList.map(async (item) => {
        return {
            ...item,
            description: await translateText(item.description),
            faciCateName: await translateText(item.faciCateName),
            infoFacilityList: await Promise.all(item.infoFacilityList.map(async (faci) => {
                return {
                    ...faci,
                    facilityCateName: await translateText(faci.facilityCateName),
                    facilityName: await translateText(faci.facilityName),
                };
            })),
        };
    }));
    return result;
};

export const transLateHistoryBooking = async (data) => {
    let result = data;
    const fieldsToTranslate = ['generalAddress', 'accomName', 'refundPolicy'];
    for (const field of fieldsToTranslate) {
        result[field] = await translateText(result[field]);
    }
    return result;
};

export const translateToVNAPI = async (data) => {
    const response = await axios.post(`${API_URL}?key=${API_KEY}`, {
        q: data,
        target: 'vi'
    });
    return response.data.data.translations[0].translatedText;
};