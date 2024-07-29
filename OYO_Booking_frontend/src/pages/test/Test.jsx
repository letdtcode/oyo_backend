import { useState } from 'react';
import { Client } from '@stomp/stompjs';
import Menu from '@mui/material/Menu';
import notificationNone from '~/assets/imageMaster/notificationNone.png';
import { t } from 'i18next';
import './test.css';
import { useSelector } from 'react-redux';

const Test = () => {
    const [anchorEl, setAnchorEl] = useState(null);
    const [numNoti, setNumNoti] = useState(0);
    const currentUser = useSelector((state) => state.user.current);

    const open = Boolean(anchorEl);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
        client.publish({
            destination: '/app/booking-noti',
            body: JSON.stringify({ senderId: currentUser.userId, accomId: 1 })
        });
    };
    const handleClose = () => {
        setAnchorEl(null);
    };
    const client = new Client({
        brokerURL: 'ws://localhost:8080/ws',
        onConnect: () => {
            client.subscribe('/topic/public', (payload) => {
                setNumNoti(numNoti + 1);
                alert(payload.body);
            });
        }
    });

    client.activate();

    return (
        <div>
            <button type="button" onClick={handleClick} className="icon-button">
                <span className="material-icons">notifications</span>
                <span className="icon-button__badge">{numNoti}</span>
            </button>

            <Menu className="mailbox" id="mailbox" anchorEl={anchorEl} open={open} onClose={handleClose}>
                <header className="header-myAccount"> {t('title.yourMail')}</header>
                <hr className="divider" />
                <div className="your-mail">
                    {!1 ? (
                        <>
                            <img src={notificationNone} className="notificationNone"></img>
                            <ul>{t('common.youNoHaveMail')}</ul>
                        </>
                    ) : (
                        <>
                            <div>heeh</div>
                        </>
                    )}
                </div>
            </Menu>
        </div>
    );
};

export default Test;
