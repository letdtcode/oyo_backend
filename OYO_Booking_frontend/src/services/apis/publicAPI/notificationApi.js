// import axios from '~/services/axios';

// const notificationApi = {
//     getNotificationForUser(size){
//         const url = `api/v1/app/notification/page?number=0&size=${size}`;
//         return axios.get(url);
//     },
//     resetNumberNotification(data){
//         const url = `api/v1/user/reset/notification`;
//         return axios.post(url, data);
//     },
//     showOffViewNotification(data){
//         // const url = `api/v1/app/notification/tick-view`;
//         // return axios.put(url, data);
//         const mockNotifications = [
//             {
//               id: 1,
//               type: 'BOOKING_NOTIFICATION',
//               description: 'New booking received',
//               view: false,
//               homeId: 123,
//             },
//             // Add more mock notifications as needed
//           ];
//           return mockNotifications
//     },
//     deleteNotificationViewed(all) {
//         const url = `api/v1/app/notification/list/delete?all=${all}`;
//         return axios.delete(url);
//     },
// };

// export default notificationApi;
