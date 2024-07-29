import { t } from 'i18next'
export const guests = (data) => {
    const numAdult = data.numAdult || 1;
    const numChild = data.numChild || 0;
    const numBornChild = data.numBornChild || 0;

    let result = '';
    
    if (numAdult > 0) {
        result += `${numAdult} ${t('format.adult')}`;
    }

    if (numChild > 0) {
        result += `, ${numChild}  ${t('format.children')}`;
    }

    if (numBornChild > 0) {
        result += `, ${numBornChild}  ${t('format.bornChild')}`;
    }

    return result;
};
