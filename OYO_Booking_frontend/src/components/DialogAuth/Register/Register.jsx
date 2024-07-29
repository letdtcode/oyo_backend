
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import { useState, useEffect } from 'react';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import Visibility from '@mui/icons-material/Visibility';
import { validate } from '~/utils/validate';
import InputAdornment from '@mui/material/InputAdornment';
import CustomInput from '~/assets/custom/CustomInput';
import CheckCircleRoundedIcon from '@mui/icons-material/CheckCircleRounded';
import authAPI from '~/services/apis/authAPI/authAPI';
import globalSlice from '~/redux/globalSlice';
import { useDispatch, useSelector } from 'react-redux';
import { RegisterRequest } from '~/models/auth';
import { useSnackbar } from 'notistack';
import { t } from 'i18next';

export default function Register(props) {
    const [register, setRegister] = useState(RegisterRequest);
    const [errors, setErrors] = useState({});
    const [showPassword, setShowPassword] = useState(false);
    const { enqueueSnackbar } = useSnackbar();
    const dispatch = useDispatch();
    const handleChange = (event) => {
        setRegister({ ...register, [event.target.name]: event.target.value });
    };
    const handleTogglePassword = () => {
        setShowPassword(!showPassword);
    };
    useEffect(() => {
        setRegister({ ...register, 'email': props.email });
    }, [props.email]);

    const handleRegister = async (event) => {
        event.preventDefault();
        const check = validate(register);
        if (Object.keys(check).length === 0) {
            dispatch(globalSlice.actions.setLoading(true))
            await authAPI
                .registerRequest(register)
                .then((res) => {
                    if (res.statusCode === 200) {
                        enqueueSnackbar('Đăng ký thành công, vui lòng xác thực tài khoản', {
                            variant: 'success'
                        });
                        dispatch(globalSlice.actions.setLoading(false))
                        props.handleClose();
                    } else if (res.statusCode === 400) {
                        enqueueSnackbar(t('message.accountExist'), { variant: 'error' });
                    }
                })
                .catch((error) => {
                    dispatch(globalSlice.actions.setLoading(false))
                });
        } else {
            setErrors(check);
        }
    };
    return (
        <Container component="main" maxWidth="xs">
            <Box component="form" onSubmit={handleRegister} noValidate sx={{ mt: 1 }}>
                <div className="form-element">
                    <CustomInput
                        title={t('label.email')}
                        value={register.email}
                        disabled={true}
                        name="email"
                        id="email"
                        size="small"
                        InputProps={{
                            startAdornment: <CheckCircleRoundedIcon style={{ color: '#00ff00' }} />
                        }}
                    />
                </div>

                <div className="form-element">
                    <CustomInput
                        title={t('label.firstName')}
                        label="Nhập họ và tên của bạn"
                        name="firstName"
                        id="firstName"
                        size="small"
                        onChange={handleChange}
                        value={register.firstName}
                    />
                    {errors.firstName && <h5>{errors.firstName}</h5>}
                </div>
                <div className="form-element">
                    <CustomInput
                        title={t('label.lastName')}
                        label="Nhập họ và tên của bạn"
                        name="lastName"
                        id="lastName"
                        size="small"
                        value={register.lastName}
                        onChange={handleChange}
                    />
                    {errors.lastName && <h5>{errors.lastName}</h5>}
                </div>
                <div className="form-element">
                    <CustomInput
                        title={t('label.password')}
                        name="password"
                        id="password"
                        size="small"
                        value={register.password}
                        onChange={handleChange}
                        type={showPassword ? 'text' : 'password'}
                        InputProps={{
                            endAdornment: (
                                <InputAdornment position="end">
                                    <IconButton onClick={handleTogglePassword} edge="end">
                                        {showPassword ? <Visibility /> : <VisibilityOff />}
                                    </IconButton>
                                </InputAdornment>
                            )
                        }}
                    />
                    {errors.password && <h5>{errors.password}</h5>}
                </div>

                <Button
                    className="form-button register"
                    type="submit"
                    fullWidth
                    variant="contained"
                >
                    {t('title.signup')}
                </Button>
            </Box>
         
        </Container>
    );
}
