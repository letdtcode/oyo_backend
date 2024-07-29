export const getToken = () => {
    return (localStorage.getItem('accessToken'))
}
export const updateToken = (accessToken) => {
    localStorage.setItem('accessToken', accessToken)
}
export const getRefreshToken = () => {
    return localStorage.getItem('refreshToken')
}
export const updateRefreshToken = (refreshToken) => {
    localStorage.setItem('refreshToken', JSON.stringify(refreshToken))
}

export const getTokenAdmin = () => {
    return (localStorage.getItem('accessTokenAdmin'))
}
export const updateTokenAdmin = (accessToken) => {
    localStorage.setItem('accessTokenAdmin', accessToken)
}
export const getRefreshTokenAdmin = () => {
    return localStorage.getItem('refreshTokenAdmin')
}
export const updateRefreshTokenAdmin = (refreshToken) => {
    localStorage.setItem('refreshTokenAdmin', JSON.stringify(refreshToken))
}
