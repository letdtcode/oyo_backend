import { createSlice } from '@reduxjs/toolkit';

const notificationSlice = createSlice({
    name: 'notification',
    initialState: {
        numberOfNotification: 0
    },
    reducers: {
        subscribeNumberOfNotification(state, action) {
            state.numberOfNotification = action.payload;
        }
    }
});

export default notificationSlice;
