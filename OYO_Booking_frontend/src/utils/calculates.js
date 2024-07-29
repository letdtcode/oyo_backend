import { differenceInDays, parse } from 'date-fns';
export const pricePay = (data) => {
    let result = data   

    // if ( data.paymentPolicy === 'PAYMENT_HALF') {
    //     result/=2;
    // }
   
    // if ( data.paymentMethod === 'PAYPAL') {
    //     totalPrice *= 0.9; // Giảm giá 10% nếu sử dụng PayPal
    // }

    return data;
}

 export const dayGap = (data) =>{
    const checkInDate = parse(data.start, 'dd/MM/yyyy', new Date());
    const checkOutDate = parse(data.end, 'dd/MM/yyyy', new Date());
    
    const daysDifference = differenceInDays(checkOutDate, checkInDate) +1;
    return daysDifference
 }