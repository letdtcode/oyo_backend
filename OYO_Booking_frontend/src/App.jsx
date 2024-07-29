import React, { useEffect } from 'react';
import { I18nextProvider, useTranslation } from 'react-i18next';
import i18n from './i18n';
import Auth from './routes/Auth';
import { useDispatch, useSelector } from 'react-redux';
import LoadingDialog from '~/components/LoadingDialog/LoadingDialog';
import { connectSocketServer } from '~/services/socket/notificationSocket';
import clientNotificationAPI from './services/apis/clientAPI/clientNotificationAPI';
import notificationSlice from '~/redux/notificationSlice';
import './App.css';

function App() {
    const dispatch = useDispatch();
    const user = useSelector((state) => state.user.current);
    const loading = useSelector((state) => state.global.loading);

    useEffect(() => {
        const selectedLanguage = localStorage.getItem('selectedLanguage');
        if (selectedLanguage) {
            i18n.changeLanguage(selectedLanguage);
        } else {
            localStorage.setItem('selectedLanguage', 'vi');
            i18n.changeLanguage('vi');
        }
    }, []);
    const mode = localStorage.getItem('mode');
    if (!mode || (mode && mode === 'light')) {
        localStorage.setItem('mode', 'light');
        document.documentElement.setAttribute('mode', 'light');
    } else {
        localStorage.setItem('mode', 'dark');
        document.documentElement.setAttribute('mode', 'dark');
    }

    useEffect(() => {
        if (user !== null) {
            connectSocketServer({ userMail: user.mail });
            clientNotificationAPI.getDataNotificationUnviewOfUser().then((res) => {
                if (res.statusCode === 200) {
                    dispatch(notificationSlice.actions.subscribeNumberOfNotification(res.data.totalElements));
                }
            });
        }
    }, [user]);
    return (
        <div>
            <Auth />
            {loading && <LoadingDialog open={loading} />}
        </div>
    );
}
export default App;
