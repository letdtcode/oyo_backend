import React, { useState, useRef } from 'react';
import { Link } from 'react-router-dom';
import FactCheckOutlinedIcon from '@mui/icons-material/FactCheckOutlined';
import WysiwygOutlinedIcon from '@mui/icons-material/WysiwygOutlined';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';
import SettingsOutlinedIcon from '@mui/icons-material/SettingsOutlined';

import Avatar from '@mui/material/Avatar';
import Divider from '@mui/material/Divider';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import UpdateAvatar from '~/components/UpdateAvatar/UpdateAvatar';
import userSlice from '~/redux/userSlice';
import { useDispatch, useSelector } from 'react-redux';
import globalSlice from '~/redux/globalSlice';

import './CardInfo.scss';
import { t } from 'i18next';
import { Button } from '@mui/material';

export default function CardInfo() {
    const user = useSelector((state) => state.user.current);

    const dispatch = useDispatch();
    const [anchorEl, setAnchorEl] = useState(null);
    const handleOpenMenu = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleCloseMenu = () => {
        setAnchorEl(null);
    };

    const handleViewAvatar = (e) => {
        e.preventDefault();
        handleCloseMenu();
        dispatch(globalSlice.actions.setViewImg([user.avatarUrl]));
    };

    const handleChangeAvatar = (e) => {
        e.preventDefault();
        inputRef.current.click();
        handleCloseMenu();
    };

    const handleLogout = (e) => {
        dispatch(userSlice.actions.logout());
       
    };
    const [imageFile, setImageFile] = useState(null);
    const [modalOpen, setModalOpen] = useState(false);
    const inputRef = useRef(null);

    const handleImgChange = (e) => {
        const allowedImageTypes = ['image/jpeg', 'image/png', 'image/bmp', 'image/webp', 'image/jpg'];
        if (!allowedImageTypes.includes(e.target.files[0].type)) {
            alert(t('validate.invalidImageType'));
            return;
        }
        setImageFile(e.target.files[0]);
        setModalOpen(true);
        inputRef.current.value = '';
    };
    return (
        <div className="col l-3" style={{ paddingTop: 60 }}>
            <UpdateAvatar
                modalOpen={modalOpen}
                imageFile={imageFile}
                setModalOpen={setModalOpen}
                setImageFile={setImageFile}
            />
            <input
                hidden
                type="file"
                accept="image/jpeg, image/png, image/bmp, image/webp, image/jpg"
                ref={inputRef}
                onChange={handleImgChange}
            />
            <div className="paper card-info">
                <div className="user-info">
                    <Avatar className="user-avatar" alt="Cindy Baker" src={user.avatarUrl} onClick={handleOpenMenu} />
                    <div className="user-details">
                        <h3 className="user-name">{user.userName}</h3>
                        <div className="user-email">{user.mail}</div>
                    </div>
                </div>
                <Divider />
                <div className="options-card">
                    <Link to="/myBooking" className="option">
                        <div className="option-icon">
                            <WysiwygOutlinedIcon />
                        </div>
                        {t('navbar.historyBookingClient')}
                    </Link>
                    <Link to="/account/profile" className="option edit-profile">
                        <div className="option-icon">
                            <SettingsOutlinedIcon />
                        </div>
                        {t('navbar.account')}
                    </Link>
                    <Link to="/" className="option logout" onClick={handleLogout}>
                        <div className="option-icon">
                            <LogoutOutlinedIcon />
                        </div>
                        {t('navbar.signout')}
                    </Link>
                </div>
            </div>
            <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={handleCloseMenu}>
                <MenuItem onClick={handleViewAvatar}>{t('common.viewImage')}</MenuItem>
                <MenuItem onClick={handleChangeAvatar}>{t('common.changeAvatar')}</MenuItem>
            </Menu>
        </div>
    );
}
