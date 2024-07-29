import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import { useForm } from 'react-hook-form';
import userSlice from '~/redux/userSlice';
import authAPI from '~/services/apis/authAPI/authAPI';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { useSnackbar } from 'notistack';
import { useSelector } from 'react-redux';
import { useEffect } from 'react';
import { t } from 'i18next';
import './LoginAdmin.scss';

function Copyright(props) {
    return (
        <Typography variant="body2" color="text.secondary" align="center" {...props}>
            {'Copyright © '}
            <Link color="inherit" href="https://mui.com/">
                OYO Booking
            </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

export default function LoginAdmin() {
    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm();
    const dispatch = useDispatch();
    const { enqueueSnackbar } = useSnackbar();
    const navigate = useNavigate();
    const isAdmin = useSelector((state) => state.user.isAdmin);
    useEffect(() => {
        if (isAdmin) {
            navigate('/admin');
        }
    }, [isAdmin]);

    const onSubmit = async (data) => {
        await authAPI
            .loginRequest(data)
            .then((res) => {
                if (res.statusCode === 200 && res.data.roles.find((role) => role === 'ROLE_ADMIN')) {
                    dispatch(userSlice.actions.signinAdmin(res.data));
                    enqueueSnackbar(t('message.signin'), { variant: 'success' });
                    navigate('/admin');
                }
            })
            .catch(() => {
                enqueueSnackbar(t('message.signinError'), { variant: 'error' });
            });
    };

    return (
        <Grid container component="main" sx={{ height: '100vh' }} className="login-admin">
            <CssBaseline />
            <Grid
                item
                xs={false}
                sm={4}
                md={7}
                sx={{
                    backgroundImage: 'url(https://source.unsplash.com/random?wallpapers)',
                    backgroundRepeat: 'no-repeat',
                    backgroundColor: (t) => (t.palette.mode === 'light' ? t.palette.grey[50] : t.palette.grey[900]),
                    backgroundSize: 'cover',
                    backgroundPosition: 'center'
                }}
            />
            <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
                <Box
                    sx={{
                        my: 8,
                        mx: 4,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center'
                    }}
                >
                    <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                        <LockOutlinedIcon />
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Sign In Admin
                    </Typography>
                    <form className="form__login-admin" onSubmit={handleSubmit(onSubmit)}>
                        <TextField
                            {...register('email', { required: true })}
                            margin="normal"
                            required
                            fullWidth
                            label="Email Address"
                            name="email"
                            autoComplete="email"
                            autoFocus
                        />
                        <TextField
                            {...register('password', { required: true })}
                            margin="normal"
                            required
                            fullWidth
                            name="password"
                            label="Password"
                            type="password"
                            autoComplete="current-password"
                        />
                        <FormControlLabel control={<Checkbox value="remember" color="primary" />} label="Remember me" />
                        <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
                            Sign In
                        </Button>
                        <Copyright sx={{ mt: 5 }} />
                    </form>
                </Box>
            </Grid>
        </Grid>
    );
}
