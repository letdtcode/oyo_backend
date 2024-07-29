const format3Dots = (value, numberMax ) => {
    if (value !== '') {
        return value && value.slice(0, numberMax) + (value.length > numberMax ? '...' : '');
    } else {
        return '';
    }
};

export default format3Dots;