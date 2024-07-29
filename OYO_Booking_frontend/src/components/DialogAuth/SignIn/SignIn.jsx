import { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import googleIcon from '~/assets/imageMaster/google-logo.png';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import Link from '@mui/material/Link';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import Visibility from '@mui/icons-material/Visibility';
import LoadingButton from '@mui/lab/LoadingButton';
import InputAdornment from '@mui/material/InputAdornment';
import CustomInput from '~/assets/custom/CustomInput';
import authAPI from '~/services/apis/authAPI/authAPI';
import { SigninRequest } from '~/models/auth';
import { validate } from '~/utils/validate';
import { useSnackbar } from 'notistack';
import { useDispatch } from 'react-redux';
import userSlice from '~/redux/userSlice';
import { t } from 'i18next';
import { GOOGLE_AUTH_URL } from '~/mockdata';

export default function SignIn(props) {
    const dispatch = useDispatch();
    const [signin, setSignin] = useState(SigninRequest);
    const [errorEmail, setErrorEmail] = useState();
    const [errorPassword, setErrorPassword] = useState();

    const [showPasswordInput, setShowPasswordInput] = useState(false);
    const [showRegisterButton, setShowRegisterButton] = useState(false);
    const [showLoginButton, setShowLoginButton] = useState(false);
    const [showForgotPassword, setShowForgotPassword] = useState(false);
    const [showStatusButton, setShowStatusButton] = useState(false);
    const [showLoadingButton, setShowLoadingButton] = useState(false);
    const [showValidEmail, setShowValidEmail] = useState(false);
    const [showPassword, setShowPassword] = useState(false);
    const { enqueueSnackbar } = useSnackbar();

    const handleLogin = async (event) => {
        event.preventDefault();
        if (!signin.password) {
            setErrorPassword(t('validate.passwordRequire'));
        } else {
            await authAPI
                .loginRequest(signin)
                .then((res) => {
                    if (res.statusCode === 200) {
                        dispatch(userSlice.actions.signin(res.data));
                        enqueueSnackbar(t('message.signin'), { variant: 'success' });
                        props.handleClose();
                    } else if (res.statusCode === 202) {
                        enqueueSnackbar(t('message.accountPending'), { variant: 'warning' });
                    } else if (res.statusCode === 203) {
                        enqueueSnackbar(t('message.accountBlock'), { variant: 'error' });
                    }
                })
                .catch(() => {
                    setErrorPassword(t('message.passwordWrong'));
                    enqueueSnackbar(t('message.signinError'), { variant: 'error' });
                });
        }
    };

    const status = [
        'PasswordInput',
        'RegisterButton',
        'LoginButton',
        'StatusButton',
        'LoadingButton',
        'ValidEmail',
        'ForgotPassword'
    ];
    const toggleShow = (ShowNames) => {
        status.forEach((statusName) => {
            if (ShowNames.includes(statusName)) {
                eval(`setShow${statusName}(true)`);
            } else {
                eval(`setShow${statusName}(false)`);
            }
        });
    };
    useEffect(() => {
        let timer;
        if (signin.email) {
            timer = setTimeout(async () => {
                const checkValidEmail = validate(signin);
                if (checkValidEmail.email) {
                    setErrorEmail(checkValidEmail.email);
                    toggleShow(['StatusButton', 'ValidEmail']);
                    return;
                }
                await authAPI
                    .checkAccount(signin)
                    .then(() => {
                        toggleShow(['PasswordInput', 'LoginButton', 'ForgotPassword']);
                    })
                    .catch(() => toggleShow(['RegisterButton']));
            }, 2000);
        } else if (signin.email === '') {
            toggleShow(['StatusButton']);
        }

        return () => {
            clearTimeout(timer);
        };
    }, [signin.email]);

    const handleEmailChange = (event) => {
        setSignin({ ...signin, email: event.target.value });
        toggleShow(['LoadingButton']);
    };

    const handlePasswordChange = async (event) => {
        setSignin({ ...signin, password: event.target.value });
    };

    const handleRegister = () => {
        props.setEmail(signin.email);
        props.setPosition('SignUp');
    };
    const handleForgotPassword = () => {
        props.setEmail(signin.email);
        props.setPosition('ForgotPassword');
    };

    return (
        <Container component="main" maxWidth="xs" className="form-signin">
            <Box component="form" onSubmit={handleLogin} noValidate sx={{ mt: 1 }}>
                <div className="form-element">
                    <CustomInput
                        title={t('label.email')}
                        id="email"
                        name="email"
                        value={signin.email}
                        onChange={handleEmailChange}
                        placeholder={t('contentMess.accountExample')}
                    />
                    {showValidEmail && <h5>{errorEmail}</h5>}
                </div>

                {showPasswordInput && (
                    <div className="form-element">
                        <CustomInput
                            title={t('label.password')}
                            name="password"
                            id="password"
                            value={signin.password}
                            onChange={handlePasswordChange}
                            type={showPassword ? 'text' : 'password'}
                            InputProps={{
                                endAdornment: (
                                    <InputAdornment position="end">
                                        <IconButton onClick={(e) => setShowPassword(!showPassword)} edge="end">
                                            {showPassword ? <Visibility /> : <VisibilityOff />}
                                        </IconButton>
                                    </InputAdornment>
                                )
                            }}
                        />
                        {errorPassword && <h5>{errorPassword}</h5>}
                    </div>
                )}
                {showStatusButton && (
                    <Button className="form-button continue" variant="contained" fullWidth disabled>
                        {t('common.continue')}
                    </Button>
                )}
                {showLoadingButton && (
                    <LoadingButton
                        className="form-button"
                        loading
                        variant="contained"
                        fullWidth
                        disabled
                    ></LoadingButton>
                )}
                {showLoginButton && (
                    <Button className="form-button login" type="submit" fullWidth variant="contained">
                        {t('title.signin')}
                    </Button>
                )}
                {showForgotPassword && (
                    <Button className="to-forgotpassword" fullWidth variant="text" onClick={handleForgotPassword}>
                        {t('link.forgotpassword')}
                    </Button>
                )}
                {showRegisterButton && (
                    <Button className="form-button to-register" fullWidth variant="contained" onClick={handleRegister}>
                        {t('title.signup')}
                    </Button>
                )}
                <h4>
                    <span className="centered-line" />
                    {t('title.orSignin')}
                    <span className="centered-line" />
                </h4>
                <div className="social-container">
                    <Button fullWidth variant="outlined" className="form-button google" href={GOOGLE_AUTH_URL}>
                        <img src={googleIcon} alt="Your Image" width="24" height="24" />
                        {t('title.withGoogle')}
                    </Button>
                </div>
                <div className="policy">
                    <h6>
                        {t('contentPolicy.policyAuth')}
                        <Link to="" className="link-policy">
                            {t('link.rules')}
                        </Link>
                        {t('contentPolicy.and')}
                        <Link to="" className="link-policy">
                            {t('link.privacyPolicy')}
                        </Link>
                        {t('contentPolicy.ofOYO')}
                    </h6>
                </div>
            </Box>
        </Container>
    );
}
