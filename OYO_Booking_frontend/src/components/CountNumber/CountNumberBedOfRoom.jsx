import { useState } from 'react';

import './CountNumber.scss';

const CountNumberBedOfRoom = (props) => {
    const [counter, setCounter] = useState(props?.countInit ? props?.countInit : 0);

    const handleIncrease = () => {
        setCounter((preState) => preState + 1); // Set state Callback
        if (props.handleSetDataBedCount) {
            props.handleSetDataBedCount({amount: counter + 1, categoryId: props?.categoryId});
        }
    };

    const handleReducer = () => {
        setCounter((preState) => preState - 1); // Set state Callback
        if (props.handleSetDataBedCount) {
            props.handleSetDataBedCount({amount: counter - 1, categoryId: props?.categoryId});
        }
    };
    return (
        <div className="count-number">
            {counter === 0 ? (
                <button className="btn-notallow">-</button>
            ) : (
                <button onClick={handleReducer} className="btn-reducer">
                    -
                </button>
            )}
            <h1>{counter}</h1>
            <button onClick={handleIncrease} className="btn-increase">
                +
            </button>
        </div>
    );
};

export default CountNumberBedOfRoom;
