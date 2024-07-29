import { configureStore, combineReducers } from '@reduxjs/toolkit';
import { persistStore, persistReducer, FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER } from 'redux-persist';
import userSlice from './userSlice';
import bookingSlice from './bookingSlice';
import notificationSlice from './notificationSlice';
import globalSlice from './globalSlice';
import filterAcomSlice from './filterAccom';
import managerAccomSlice from './managerAccomSlice';
import localStorage from 'redux-persist/es/storage';

const persistConfig = {
    key: 'root',
    whitelist: ['user', 'booking'],
    blacklist: ['notification', 'global'],
    storage: localStorage
};

const rootReducer = combineReducers({
    user: userSlice.reducer,
    booking: bookingSlice.reducer,
    notification: notificationSlice.reducer,
    global: globalSlice.reducer,
    filterAccom: filterAcomSlice.reducer,
    managerAccom: managerAccomSlice.reducer
});
const resettableReducer = (state, action) => {
    if (action.type === 'user/logout') {
        state = undefined;
    }
    return rootReducer(state, action);
};

const persistedReducer = persistReducer(persistConfig, rootReducer, resettableReducer);

export const store = configureStore({
    reducer: persistedReducer,
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware({
            serializableCheck: {
                ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER]
            }
        })
});

export let persistor = persistStore(store);
