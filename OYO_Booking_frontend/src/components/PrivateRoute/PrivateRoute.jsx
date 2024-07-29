import { useEffect } from 'react';
import { Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { useSnackbar } from 'notistack';
import { t } from 'i18next';

const PrivateRoute = ({ element }) => {
    const user = useSelector((state) => state.user.current);
    const { enqueueSnackbar } = useSnackbar();

    useEffect(() => {
        if (!user) {
            enqueueSnackbar(t('message.requestSigin'), { variant: 'warning' });
        }
    }, [user, enqueueSnackbar]);


    if (!user) {
        return <Navigate to="/" />;
    }
    return element;
};

export default PrivateRoute;
