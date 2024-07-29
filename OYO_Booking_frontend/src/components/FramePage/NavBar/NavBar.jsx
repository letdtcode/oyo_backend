import { useState } from 'react';
import { useSelector } from 'react-redux';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import { useLocation, NavLink } from 'react-router-dom';
import logoOYO from '~/assets/logo.svg';
import ModeToggle from '~/components/ModeToggle/ModeToggle';
import DropdownUser from '~/components/DropdownUser/DropdownUser';
import LanguageSelected from '~/components/LanguageSelected/LanguageSelected';
import Button from '@mui/material/Button';
import AccountCircle from '@mui/icons-material/AccountCircle';
import DomainIcon from '@mui/icons-material/Domain';
import MailNotification from '~/components/MailNotification/MailNotification';
import DialogAuth from '~/components/DialogAuth/DialogAuth';
import MenuIcon from '@mui/icons-material/Menu';
import IconButton from '@mui/material/IconButton';
import Drawer from '@mui/material/Drawer';
import { t } from 'i18next';
import './NavBar.scss';
import { useDispatch } from 'react-redux';
import filterAcomSlice from '~/redux/filterAccom';

export default function NavBar() {
    const [open, setOpen] = useState(false);
    const user = useSelector((state) => state.user.current);
    const location = useLocation().pathname;
    const dispatch = useDispatch();

    const [openDrawer, setOpenDrawer] = useState(false);
    const toggleDrawer = (anchor, open) => (event) => {
        if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
            return;
        }
        setOpenDrawer(open);
    };
    const handleToListAccom = () => {
        dispatch(filterAcomSlice.actions.reset());
    };
    return (
        <AppBar className="appbar">
            <Toolbar className="toolbar">
                <NavLink to="/" className="logo">
                    <img src={logoOYO} alt="company logo" className="logo-bg" />
                </NavLink>
                <div className={`element ${location === '/' ? '__actived' : ''}`}>
                    <NavLink to="/">{t('navbar.home')}</NavLink>
                </div>
                <div className="appbar-right-menu" />

                <div className="element">
                    <ModeToggle />
                </div>
                <div className="element">
                    <LanguageSelected />
                </div>

                <div className={`element${location === '/list-accom' ? '__actived' : ''}`} onClick={handleToListAccom}>
                    <NavLink to="/list-accom">{t('navbar.listroom')}</NavLink>
                </div>

                {user === null ? (
                    <Button className="element" onClick={(e) => setOpen(true)} startIcon={<AccountCircle />}>
                        {t('title.signin')}/{t('title.signup')}
                    </Button>
                ) : (
                    <>
                        <div className={`element${location === '/wishlists' ? '__actived' : ''}`}>
                            <NavLink to="/wishlists">{t('navbar.listLove')}</NavLink>
                        </div>
                        {/* <div className="element">
                            <MailNotification />
                        </div> */}
                        <div className="element">
                            <DropdownUser />
                        </div>
                    </>
                )}
                <div className="element__drawer">
                    <IconButton
                        color="inherit"
                        aria-label="open drawer"
                        edge="start"
                        onClick={(e) => setOpenDrawer(true)}
                    >
                        <MenuIcon />
                    </IconButton>
                </div>
                <Drawer anchor="left" open={openDrawer} onClose={toggleDrawer(false)}>
                    <div className="drawer-content paper">
                        <NavLink to="/" className="logo">
                            <img src={logoOYO} alt="company logo" className="logo-bg" />
                        </NavLink>
                        <div className="drawer-item">
                            <NavLink to="/">{t('navbar.home')}</NavLink>
                        </div>
                        <div className="drawer-item">
                            <ModeToggle />
                            Chế độ sáng
                        </div>
                        <div className="drawer-item">
                            <LanguageSelected />
                        </div>
                        <div className="drawer-item">
                            <DomainIcon />
                            <NavLink to="/list-accom" onClick={handleToListAccom}>
                                {t('navbar.listroom')}{' '}
                            </NavLink>
                        </div>
                        {user === null ? (
                            <div className="drawer-item">
                                <Button onClick={(e) => setOpen(true)} startIcon={<AccountCircle />}>
                                    {t('title.signin')}/{t('title.signup')}
                                </Button>
                            </div>
                        ) : (
                            <>
                                <div className="drawer-item">
                                    <NavLink to="/wishlists">{t('navbar.listLove')}</NavLink>
                                </div>
                                <div className="drawer-item">
                                    <MailNotification /> Thư của bạn
                                </div>
                                <div className="drawer-item">
                                    <DropdownUser />
                                </div>
                            </>
                        )}
                    </div>
                </Drawer>
                {open && <DialogAuth open={open} setOpen={setOpen} />}
            </Toolbar>
        </AppBar>
    );
}
