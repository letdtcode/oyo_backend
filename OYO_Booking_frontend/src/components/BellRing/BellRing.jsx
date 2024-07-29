import { useEffect, useRef, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import DoneAllIcon from '@mui/icons-material/DoneAll';

import notificationSlice from '../../redux/notificationSlice';
import notificationApi from '~/services/apis/publicAPI/notificationApi';
import './BellRing.scss';

const BellRing = (props) => {
    const [dataNoti, setDataNoti] = useState([]);
    const [showAll, setShowAll] = useState(6);
    const [checkDelete, setCheckDelete] = useState(false);
    const dropdown_toggle_el = useRef(null);
    const dropdown_content_el = useRef(null);
    const user = useSelector((state) => state.user);
    const noti = useSelector((state) => state.notification);

    const dispatch = useDispatch();

    useEffect(() => {
        if (user.current.id) {
            notificationApi.getNotificationForUser(100).then((dataRes) => {
                setDataNoti(dataRes.data.content);
            });
        }
        if (showAll === 100) {
            dropdown_content_el.current.classList.add('active');
        }
        setCheckDelete(false);
    }, [user, showAll, checkDelete]);

    const clickOutsideRef = (content_ref, toggle_ref) => {
        document.addEventListener('mousedown', (e) => {
            // user click toggle
            if (toggle_ref.current && toggle_ref.current.contains(e.target)) {
                content_ref.current.classList.toggle('active');
            } else {
                // user click outside toggle and content
                if (content_ref.current && !content_ref.current.contains(e.target)) {
                    content_ref.current.classList.remove('active');
                    setShowAll(6);
                }
            }
        });
    };

    clickOutsideRef(dropdown_content_el, dropdown_toggle_el);

    const handleResetNoti = () => {
        notificationApi.getNotificationForUser(100).then((dataRes) => {
            setDataNoti(dataRes.data.content);
        });
        if (noti.numberOfNotification !== 0) {
            notificationApi.resetNumberNotification('').then(() => {
                dispatch(notificationSlice.actions.subscribeNumberOfNotification(0));
            });
        }
    };

    const handleShowAll = () => {
        if (dataNoti.length !== 0) {
            if (showAll === 6) {
                setShowAll(100);
                dropdown_content_el.current.classList.add('active');
            } else {
                notificationApi.deleteNotificationViewed(true).then(() => {
                    setCheckDelete(true);
                });
            }
        }
    };

    const clearNoti = () => {
        notificationApi.deleteNotificationViewed(false).then(() => {
            setCheckDelete(true);
        });
    };

    return (
        <>
            {user.current.id ? (
                <div className="bell-ring" onClick={handleResetNoti}>
                    <button ref={dropdown_toggle_el} className="dropdown__toggle">
                        {props.icon ? <i className={props.icon}></i> : ''}
                        {props.badge ? <span className="dropdown__toggle-badge">{props.badge}</span> : ''}
                        {props.customToggle ? props.customToggle() : ''}
                    </button>
                    <div ref={dropdown_content_el} className={`dropdown__content`}>
                        <div className="header__noti">
                            <h1>Notifications</h1>
                        </div>
                        <div className={`${showAll !== 6 ? 'all-noti' : ''}`}>
                            {props.contentData && props.renderItems
                                ? dataNoti.slice(0, showAll).map((item, index) => props.renderItems(item, index))
                                : ''}
                        </div>
                        <hr style={{ border: '0.2px solid #e0e0e0', padding: '0px 18px' }} />
                        {props.renderFooter ? (
                            <div className="dropdown__footer">
                                <div className="btn-read-all" onClick={clearNoti}>
                                    <DoneAllIcon sx={{ color: '#2979ff', fontSize: '18px', marginRight: '4px' }} />
                                    <p>Clear all as read</p>
                                </div>
                                <p onClick={handleShowAll} aria-disabled={dataNoti.length === 0} className="btn-view">
                                    {showAll === 6 ? props.renderFooter() : <p className="btn-view">Clear all</p>}
                                </p>
                            </div>
                        ) : (
                            ''
                        )}
                    </div>
                </div>
            ) : (
                <></>
            )}
        </>
    );
};

export default BellRing;
