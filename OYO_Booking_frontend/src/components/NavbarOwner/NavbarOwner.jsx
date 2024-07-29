import { t } from 'i18next';
import { useSelector } from 'react-redux';
import { NavLink, useNavigate } from 'react-router-dom';
import BookOnlineIcon from '@mui/icons-material/BookOnline';
import HomeIcon from '@mui/icons-material/Home';
import ManageHistoryIcon from '@mui/icons-material/ManageHistory';
import Logo from '~/assets/svg/logo.svg';
// import notificationApi from '~/services/apis/publicAPI/notificationApi';
import MailNotification from '~/components/MailNotification/MailNotification';
import DropdownUser from '../DropdownUser/DropdownUser';
import './NavbarOwner.scss';

const NavbarOwner = () => {
    const user = useSelector((state) => state.user);
    const noti = useSelector((state) => state.notification);

    const navigate = useNavigate();

    const renderIcon = (type, view) => {
        if (type === 'BOOKING_NOTIFICATION') {
            return <BookOnlineIcon sx={{ color: `${!view ? '#2962ff' : 'black'}`, fontSize: '18px' }} />;
        }
        if (type === 'HOME_NOTIFICATION') {
            return <HomeIcon sx={{ color: `${!view ? '#2962ff' : 'black'}`, fontSize: '18px' }} />;
        }
        if (type === 'OWNER_HOME_NOTIFICATION') {
            return <ManageHistoryIcon sx={{ color: `${!view ? '#2962ff' : 'black'}`, fontSize: '18px' }} />;
        }
    };
    return (
        <div className="navbar-owner">
            <NavLink to="/" className="logo">
                <img src={Logo} alt="company logo" className="logo-bg" />
            </NavLink>
            <div className="navbar-right menu">
                <NavLink to="/host" end={true}>
                    {t('navbar.managerPage')}
                </NavLink>
                <NavLink to="/host/today" end={true}>
                    {t('navbar.today')}
                </NavLink>

                <NavLink to="/host/roomsAndRateManager">{t('navbar.roomsAndRateManager')}</NavLink>
                <NavLink to="/host/calendar">{t('navbar.calender')}</NavLink>

                <NavLink to="/host/transactionhistory">{t('navbar.historyHost')}</NavLink>
                <NavLink to="/host/statistic">{t('navbar.statisticHost')}</NavLink>
                {/* <div className="element"> */}
                <div style={{ padding: '0 5px' }}>
                    <MailNotification />
                </div>
                {/* </div> */}
                <DropdownUser />
            </div>
        </div>
    );
};

export default NavbarOwner;
