import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { store } from '~/redux/store';
import notificationSlice from '~/redux/notificationSlice';

const VITE_WS_BASE_URL = import.meta.env.VITE_WS_BASE_URL;
let stompClient = null;

const connectSocketServer = ({ userMail }) => {
    const socket = new SockJS(`${VITE_WS_BASE_URL}/ws`);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, () => {
        stompClient.subscribe(`/user/${userMail}/queue/messages`, (message) => {
            store.dispatch(notificationSlice.actions.subscribeNumberOfNotification(message.body));
        });
    });
};

const disconnectSocketServer = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
};

export { connectSocketServer, disconnectSocketServer, stompClient };
