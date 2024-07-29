import './MailNotification.scss';
import { useState } from 'react';
import { IconButton } from '@mui/material';
import Badge from '@mui/material/Badge';
import MailOutlineIcon from '@mui/icons-material/MailOutline';
import Menu from '@mui/material/Menu';
import notificationNone from '~/assets/imageMaster/notificationNone.png';
import { t } from 'i18next';
import ItemNotification from './ItemNotification/ItemNotification';
import { useSelector, useDispatch } from 'react-redux';
import notificationSlice from '~/redux/notificationSlice';
import clientNotificationAPI from '~/services/apis/clientAPI/clientNotificationAPI';

export default function MailNotification() {
    const [anchorEl, setAnchorEl] = useState(null);
    const numNotification = useSelector((state) => state.notification.numberOfNotification);
    const [dataNotification, setDataNotification] = useState([]);
    const open = Boolean(anchorEl);
    const dispatch = useDispatch();

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
        clientNotificationAPI.getDataNotificationOfUser().then((res) => {
            setDataNotification(res.data.content);
        });
        clientNotificationAPI.resetAllNotification().then((res) => {
            if (res.statusCode === 200) {
                dispatch(notificationSlice.actions.subscribeNumberOfNotification(0));
            }
        });
    };
    const handleClose = () => {
        setAnchorEl(null);
    };
    return (
        <>
            <IconButton onClick={handleClick}>
                <Badge badgeContent={numNotification} color="primary">
                    <MailOutlineIcon />
                </Badge>
            </IconButton>

            <Menu
                className="mailbox"
                id="mailbox"
                anchorEl={anchorEl}
                open={open}
                onClose={handleClose}
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'center',
                  }}
                  transformOrigin={{
                    vertical: 'top',
                    horizontal: 'center',
                  }}
            >
                <header className="header-myAccount"> {t('title.yourMail')}</header>
                <hr className="divider" />
                <div className="your-mail">
                    {dataNotification.length > 0 ? (
                        dataNotification.map((noti) => (
                            <ItemNotification
                                title={noti.title}
                                content={noti.content}
                                dateTime={noti.dateTime}
                                key={noti.id}
                            />
                        ))
                    ) : (
                        <>
                            <img src={notificationNone} className="notificationNone"></img>
                            <ul>{t('common.youNoHaveMail')}</ul>
                        </>
                    )}
                </div>
            </Menu>
        </>
    );
}
