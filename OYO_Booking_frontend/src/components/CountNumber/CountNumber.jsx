import { useState, useEffect } from 'react';
import './CountNumber.scss';

const CountNumber = ({number, data, keyType, setData}) => {
    const [counter, setCounter] = useState(0);

    useEffect(() => {
        setCounter(number);
    }, [number]);

    const handleIncrease = () => {
        setCounter((prevState) => prevState + 1);
        const existingItem = data.find((item) => item.key === keyType);
        if (!existingItem) {
            setData([...data, { key: keyType, number: counter + 1 }]);
        } else {
            const updatedData = data.map((item) =>
                item.key === keyType ? { ...item, number: counter + 1 } : item
            );
            setData(updatedData);
        }
    };

    const handleReduce = () => {
        setCounter((prevState) => prevState - 1);
        const existingItem = data.find((item) => item.key === keyType);
        if (existingItem) {
            const updatedData = data.map((item) =>
                item.key === keyType ? { ...item, number: counter - 1 } : item
            );
            setData(updatedData);
        }
    };

    return (
        <div className="count-number">
            <button
                type='button'
                onClick={handleReduce}
                className={`count-number__button ${counter === 0 ? 'count-number__button--disabled' : 'count-number__button--reduce'}`}
                disabled={counter === 0}
            >
                -
            </button>
            <h1 className="count-number__counter">{counter}</h1>
            <button
                type='button'
                onClick={handleIncrease}
                className="count-number__button count-number__button--increase"
            >
                +
            </button>
        </div>
    );
};

export default CountNumber;
