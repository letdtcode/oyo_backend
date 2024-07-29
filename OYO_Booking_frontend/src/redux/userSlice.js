import { createSlice } from '@reduxjs/toolkit';

const userSlice = createSlice({
    name: 'user',
    initialState: {
        current: null,
        roles: null,
        settings: false,
        isAdmin: false
    },
    reducers: {
        signup(state, action) {},
        signin(state, action) {
            state.roles = action.payload.roles;
            localStorage.setItem('accessToken', action.payload.accessToken);
            localStorage.setItem('refreshToken', action.payload.refreshToken);
            state.current = action.payload.infoUserResponse;
        },
        signinAdmin(state, action) {
            state.roles = action.payload.roles;
            localStorage.setItem('accessTokenAdmin', action.payload.accessToken);
            localStorage.setItem('refreshTokenAdmin', action.payload.refreshToken);
            state.isAdmin = true;
        },
        setProfile(state, action) {
            localStorage.setItem('user', JSON.stringify(action.payload.data));
        },
        logout(state) {
            localStorage.removeItem('user');
            localStorage.removeItem('accessToken');
            localStorage.removeItem('refreshToken');
            state.current = null;
            state.roles = null;
            window.location.reload();
        },
        logoutAdmin(state) {
            localStorage.removeItem('accessTokenAdmin');
            localStorage.removeItem('refreshTokenAdmin');
            state.isAdmin = false;
            window.location.reload();
        },
        editInfo(state, action) {
            state.current = action.payload;
        },
        updateHost(state) {
            state.settings = true;
        },
        refreshToken(state, action) {
            localStorage.setItem('accessToken', action.payload.accessToken);
        }
    }
});

export default userSlice;
