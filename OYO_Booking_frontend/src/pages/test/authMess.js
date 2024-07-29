import { createSlice } from '@reduxjs/toolkit';

const authMessSlice = createSlice({
    name: 'authMess',
    initialState: {
        error401: false
    },
    reducers: {
        addError401(state) {
            state.error401 = true;
        },
        closeError401(state) {
            state.error401 = !state.error401;
        },
    },
});

export default authMessSlice;
