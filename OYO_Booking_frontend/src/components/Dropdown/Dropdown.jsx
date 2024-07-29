import './Dropdown.scss';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { t } from 'i18next';
import { useState, useRef, useEffect } from 'react';
import { guests } from '~/utils/formatForm';

const Dropdown = (props) => {
    const [isActive, setIsActive] = useState(false);
    const refOne = useRef(null);

    useEffect(() => {
        document.addEventListener('click', hideOnClickOutside, true);
    }, []);

    const hideOnClickOutside = (e) => {
        if (refOne.current && !refOne.current.contains(e.target)) {
            setIsActive(false);
        }
    };
    const handleChange = (event) => {
        if (event.target.value >= 0 && event.target.value < 99) {
            if (props.handleChangeGuests) {
                props.handleChangeGuests({ ...props.guests, [event.target.name]: event.target.value });
            }
        }
    };

    return (
        <div className="count__guest">
            <div className="dropdown" ref={refOne}>
                <div className="dropdown-title">{t('numberCount.countClient')}</div>
                <div className="dropdown-btn" onClick={(e) => setIsActive(!isActive)}>
                    {guests(props.guests)}
                    <ExpandMoreIcon />
                </div>
                {isActive && (
                    <div className="dropdown-content">
                        <div className="dropdown-item">
                            {t('contentMain.countClient.adults')}
                            <input
                                type="number"
                                id="numAdult"
                                name="numAdult"
                                min="1"
                                defaultValue={props.guests.numAdult}
                                onChange={handleChange}
                            ></input>
                        </div>
                        <div className="dropdown-item">
                            {t('contentMain.countClient.children')}
                            <input
                                type="number"
                                id="numChild"
                                name="numChild"
                                min="0"
                                defaultValue={props.guests.numChild}
                                onChange={handleChange}
                            ></input>
                        </div>
                        <div className="dropdown-item">
                            {t('contentMain.countClient.baby')}
                            <input
                                type="number"
                                id="numBornChild"
                                name="numBornChild"
                                min="0"
                                defaultValue={props.guests.numBornChild}
                                onChange={handleChange}
                            ></input>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};

export default Dropdown;
