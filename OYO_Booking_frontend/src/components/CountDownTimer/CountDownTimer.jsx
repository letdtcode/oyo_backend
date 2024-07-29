import { useEffect } from 'react';
import './CountDownTimer.scss';
import { useCountDown } from '~/hooks/useCountdown';
const CountDownTimer = ({ targetDate, handleShowModalTimeUp }) => {
    const [days, hours, minutes, seconds] = useCountDown(targetDate);

    useEffect(() => {
        if (days + hours + minutes + seconds <= 0) {
            handleShowModalTimeUp(true);
        }
    }, [days, hours, minutes, seconds]);

    return (
        <div className="count-down-timer" style={{}}>
            <i className="fa-regular fa-alarm-clock" style={{ marginRight: '10px' }} />
            Thời gian hoàn tất thanh toán{' '}
            <span>
                {minutes < 10 ? '0' + minutes : minutes}:{seconds < 10 ? '0' + seconds : seconds}
            </span>
        </div>
    );
};

export default CountDownTimer;
