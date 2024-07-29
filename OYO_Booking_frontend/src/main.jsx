import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import { SnackbarProvider } from 'notistack';
import { I18nextProvider } from 'react-i18next';
import { Provider } from 'react-redux';
import { PersistGate } from 'redux-persist/integration/react'; // Thêm dòng này
import { store, persistor } from './redux/store'; // Thêm dòng này
import i18n from './i18n';
import CssBaseline from '@mui/material/CssBaseline';
import { BrowserRouter } from 'react-router-dom';
import { SearchProvider } from '~/contexts/SearchContext';
import './assets/css/grid.scss';
import './assets/css/frame.scss';
import './assets/css/index.scss';
import './assets/css/responsive.scss';
// import '../cloudinary-video-player/dist/cld-video-player.min.css';

ReactDOM.createRoot(document.getElementById('root')).render(
    // <React.StrictMode>
    <SearchProvider>
        <BrowserRouter>
            <Provider store={store}>
                <PersistGate loading={null} persistor={persistor}>
                    <I18nextProvider i18n={i18n}>
                        <CssBaseline />
                        <SnackbarProvider
                            maxSnack={3}
                            anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
                            style={{ fontSize: '14px' }}
                        >
                            <App />
                        </SnackbarProvider>
                    </I18nextProvider>
                </PersistGate>
            </Provider>
        </BrowserRouter>
    </SearchProvider>
    // </React.StrictMode>
);
