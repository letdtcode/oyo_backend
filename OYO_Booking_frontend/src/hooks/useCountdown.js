import { useState, useEffect } from 'react';

const useCountDown = (targetDate) => {
    const [countDown, setCountDown] = useState(targetDate - new Date().getTime());

    useEffect(() => {
        const interval = setInterval(() => {
            if (targetDate - new Date().getTime() > 0) {
                setCountDown(targetDate - new Date().getTime());
            } else {
                setCountDown(0);
            }
        }, 1000);
        return () => clearInterval(interval);
    }, [targetDate]);
    return getReturnValues(countDown);
};

const getReturnValues = (countDown) => {
    const days = Math.floor(countDown / (1000 * 60 * 60 * 24));
    const hours = Math.floor((countDown % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutes = Math.floor((countDown % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((countDown % (1000 * 60)) / 1000);

    return [days, hours, minutes, seconds];
};

export { useCountDown };
