import React, { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import userSlice from '~/redux/userSlice';
import { useSnackbar } from 'notistack';

const OAuth2RedirectHandler = () => {
    const { enqueueSnackbar } = useSnackbar();
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    useEffect(() => {
        const accessToken = searchParams.get('accessToken');
        const refreshToken = searchParams.get('refreshToken');
        const infoUserResponse = JSON.parse(searchParams.get('infoUserResponse'));
        const error = searchParams.get('error');
        const infoUser = {
            accessToken,
            refreshToken,
            infoUserResponse
        };

        if (accessToken) {
            dispatch(userSlice.actions.signin(infoUser));
        } else {
            enqueueSnackbar(error, { variant: 'error' });
        }
        navigate('/');
    }, []);

    return <div>Redirect Handler</div>;
};

export default OAuth2RedirectHandler;