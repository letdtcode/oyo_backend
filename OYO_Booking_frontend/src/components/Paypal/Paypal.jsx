// import { PayPalScriptProvider, PayPalButtons } from '@paypal/react-paypal-js';
// import { useEffect, useState } from 'react';
// import { convertVndToUSD } from '~/utils/convertPrice';

// const PAYPAL_CLIENT_ID = import.meta.env.VITE_PAYPAL_CLIENT_ID;

// function Paypal({ pricePayment, booking, canBooking, errors }) {
//     const priceToUSD = convertVndToUSD(pricePayment);

//     // console.log(priceToUSD);
//     return (
//         <div className="paypal">
//             <PayPalScriptProvider
//                 options={{
//                     'client-id': PAYPAL_CLIENT_ID
//                 }}
//             >
//                 <PayPalButtons
//                     disabled={Object.keys(errors).length !== 0 || !canBooking}
//                     forceReRender={[priceToUSD]}
//                     createOrder={(data, actions) => {
//                         return actions.order.create({
//                             purchase_units: [
//                                 {
//                                     amount: {
//                                         value: priceToUSD
//                                     }
//                                 }
//                             ]
//                         });
//                     }}
//                     onApprove={async (data, actions) => {
//                         const details = await actions.order.capture();
//                         if (details.status === 'COMPLETED') {
//                             await booking();
//                         }
//                     }}
//                 />
//             </PayPalScriptProvider>
//         </div>
//     );
// }

// export default Paypal;
