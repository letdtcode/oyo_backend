import React, { useEffect, useState, useContext } from 'react';
import InputAdornment from '@mui/material/InputAdornment';
import { Grid } from '@mui/material';
import FormControl from '@mui/material/FormControl';
import Button from '@mui/material/Button';
import CustomInput from '~/assets/custom/CustomInput';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import Visibility from '@mui/icons-material/Visibility';
import IconButton from '@mui/material/IconButton';
import { ChangePassword } from '~/models/auth';
import authAPI from '~/services/apis/authAPI/authAPI';
import { useDispatch, useSelector } from 'react-redux';
import { validateChangePassword } from '~/utils/validate';

import { useSnackbar } from 'notistack';

import { t } from 'i18next';

export default function passwordSecurity() {
    const { enqueueSnackbar } = useSnackbar();
    const user = useSelector((state) => state.user.current);
    const [showPassword, setShowPassword] = useState({
        newPassword: false,
        checkPassword: false
    });
    const [errors, setErrors] = useState({
        oldPassword: null,
        newPassword: null,
        enterNewPassword: null
    });
    const [changePassword, setChangePassword] = useState(ChangePassword);

    const handleShowPassword = (fieldName) => {
        setShowPassword({ ...showPassword, [fieldName]: !showPassword[fieldName] });
    };

    const handleInput = (event) => {
        setChangePassword({ ...changePassword, [event.target.name]: event.target.value });
    };

    useEffect(() => {
        setChangePassword({ ...changePassword, email: user.mail });
    }, [user?.mail]);
    const handleChangePassword = async (event) => {
        event.preventDefault();
        const check = validateChangePassword(changePassword)
        if (Object.keys(check).length !==0 ) {
            setErrors(check);
        } else {
            await authAPI
                .changePasswordRequest(changePassword)
                .then((res) => {
                    enqueueSnackbar(t('message.changePassword'), { variant: 'success' });
                })
                .catch((err) => {
                    enqueueSnackbar('Đổi mật khẩu thất bại', { variant: 'error' });
                });
        }
    };
    return (
        <div className="changePassword">
            <h2>{t('navbar.changePassword')}</h2>
            <hr className="divider" />
            <FormControl fullWidth component="form" onSubmit={handleChangePassword} className="form">
                <Grid container direction={{ xs: 'column', md: 'row' }} columnSpacing={7} rowSpacing={3}>
                    <Grid item xs={12}>
                        <CustomInput
                            type="password"
                            name="oldPassword"
                            size="medium"
                            title={t('label.currentPassword')}
                            value={changePassword.oldPassword}
                            onChange={handleInput}
                        ></CustomInput>
                        {errors.oldPassword && <h5>{errors.oldPassword}</h5>}
                    </Grid>
                    <Grid item xs={12}>
                        <CustomInput
                            name="newPassword"
                            title={t('label.newPassword')}
                            value={changePassword.newPassword}
                            onChange={handleInput}
                            type={showPassword.newPassword ? 'text' : 'password'}
                            InputProps={{
                                endAdornment: (
                                    <InputAdornment position="end">
                                        <IconButton onClick={() => handleShowPassword('newPassword')} edge="end">
                                            {showPassword.newPassword ? <Visibility /> : <VisibilityOff />}
                                        </IconButton>
                                    </InputAdornment>
                                )
                            }}
                        ></CustomInput>
                        {errors.newPassword && <h5>{errors.newPassword}</h5>}
                    </Grid>
                    <Grid item xs={12}>
                        <CustomInput
                            name="enterNewPassword"
                            title={t('label.enterANewPassword')}
                            value={changePassword.enterNewPassword}
                            onChange={handleInput}
                            type={showPassword.checkPassword ? 'text' : 'password'}
                            InputProps={{
                                endAdornment: (
                                    <InputAdornment position="end">
                                        <IconButton onClick={() => handleShowPassword('checkPassword')} edge="end">
                                            {showPassword.checkPassword ? <Visibility /> : <VisibilityOff />}
                                        </IconButton>
                                    </InputAdornment>
                                )
                            }}
                        ></CustomInput>
                        {errors.enterNewPassword && <h5>{errors.enterNewPassword}</h5>}
                    </Grid>
                    <Grid container justifyContent={{ xs: 'center', md: 'flex-end' }} item xs={12}>
                        <Button className="button change-password" variant="contained" type="submit">
                            {t('navbar.changePassword')}
                        </Button>
                    </Grid>
                </Grid>
            </FormControl>
        </div>
    );
}
