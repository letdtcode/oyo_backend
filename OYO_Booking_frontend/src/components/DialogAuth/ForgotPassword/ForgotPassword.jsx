import { useState, useEffect, useRef } from 'react';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import CustomInput from '~/assets/custom/CustomInput';
import CheckCircleRoundedIcon from '@mui/icons-material/CheckCircleRounded';
import authAPI from '~/services/apis/authAPI/authAPI';
import { useSnackbar } from 'notistack';
import { useDispatch } from 'react-redux';
import globalSlice from '~/redux/globalSlice';
import { t } from 'i18next';
import './ForgotPassword.scss';

function ForgotPassword(props) {
    const [captchaText, setCaptchaText] = useState('');
    const [userInput, setUserInput] = useState('');
    const [errors, setErrors] = useState(null);
    const canvasRef = useRef(null);
    const { enqueueSnackbar } = useSnackbar();
    const dispatch = useDispatch();

    useEffect(() => {
        const canvas = canvasRef.current;
        const ctx = canvas.getContext('2d');
        initializeCaptcha(ctx);
    }, []);

    const generateRandomChar = (min, max) => String.fromCharCode(Math.floor(Math.random() * (max - min + 1) + min));

    const generateCaptchaText = () => {
        let captcha = '';
        for (let i = 0; i < 2; i++) {
            captcha += generateRandomChar(65, 90);
            captcha += generateRandomChar(97, 122);
        }
        return captcha
            .split('')
            .sort(() => Math.random() - 0.5)
            .join('');
    };
    const drawCaptchaOnCanvas = (ctx, captcha) => {
        ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
        const textColors = ['rgb(0,0,0)', 'rgb(130,130,130)'];
        const letterSpace = 150 / captcha.length;
        const canvasWidth = ctx.canvas.width;
        const canvasHeight = ctx.canvas.height;
        for (let i = 0; i < captcha.length; i++) {
            const xInitialSpace = 25;

            ctx.font = '40px Roboto Mono';
            ctx.fillStyle = textColors[Math.floor(Math.random() * 2)];
            ctx.textAlign = 'center';
            const xPosition = xInitialSpace + i * letterSpace + letterSpace / 2;
            ctx.textBaseline = 'middle';
            const yPosition = canvasHeight / 2;

            ctx.fillText(captcha[i], xPosition, yPosition);
        }
    };

    const initializeCaptcha = (ctx) => {
        setErrors(null);
        setUserInput('');
        const newCaptcha = generateCaptchaText();
        setCaptchaText(newCaptcha);
        drawCaptchaOnCanvas(ctx, newCaptcha);
    };

    const handleUserInputChange = (e) => {
        setUserInput(e.target.value);
    };

    const handleCaptchaSubmit = async () => {
        if (userInput === captchaText) {
            dispatch(globalSlice.actions.setLoading(true));
            await authAPI
                .resetPassword(props.email)
                .then((res) => {
                    enqueueSnackbar(t('message.changePassword'), { variant: 'success' });
                    dispatch(globalSlice.actions.setLoading(false));
                    props.handleClose();
                })
                .catch((err) => {});
        } else {
            const canvas = canvasRef.current;
            const ctx = canvas.getContext('2d');
            initializeCaptcha(ctx);
            setErrors('Capcha sai, vui lòng nhập lại');
        }
    };
    const handleSubmit = async (e) => {};
    return (
        <Container component="main" maxWidth="xs">
            <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
                <div className="form-element">
                    <CustomInput
                        title={t('label.email')}
                        value={props.email}
                        disabled={true}
                        name="email"
                        id="email"
                        size="small"
                        InputProps={{
                            startAdornment: <CheckCircleRoundedIcon style={{ color: 'var(--primary-main)' }} />
                        }}
                    />
                </div>

                <div className="form-element">
                    <div className="wrapper">
                        <canvas ref={canvasRef}></canvas>
                        <Button
                            className="btn-reload"
                            onClick={() => initializeCaptcha(canvasRef.current.getContext('2d'))}
                        >
                            {t('common.reload')}
                        </Button>
                    </div>
                    {errors && <h5>{errors}</h5>}
                    <CustomInput
                        type="text"
                        className="user-input"
                        title={t('common.enterCapcha')}
                        value={userInput}
                        onChange={handleUserInputChange}
                    />

                    <Button className="form-button btn-forgotpassword" fullWidth onClick={handleCaptchaSubmit}>
                        {t('common.retrievalPassword')}
                    </Button>
                </div>
            </Box>
        </Container>
    );
}

export default ForgotPassword;
