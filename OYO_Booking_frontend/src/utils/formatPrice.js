const formatPrice = (value) => {
    let formattedValue;
    if (localStorage.getItem('selectedLanguage') === 'en') {
        formattedValue = parseFloat(value / 24335).toFixed(1);
        return `${new Intl.NumberFormat('en-US').format(formattedValue)} USD`;
    } else {
        formattedValue = parseInt(value);
        return `${new Intl.NumberFormat('vi-VN').format(formattedValue)}₫`;
    }
};

export default formatPrice;
