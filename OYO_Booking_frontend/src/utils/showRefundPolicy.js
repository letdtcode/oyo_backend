
export const showRefundPolicy=({ cancellationPolicy, cancellationFeeRate })=>{
    let result = '';
    if (cancellationPolicy === 'NO_CANCEL') {
        result = 'Không hoàn trả';
    }
    else if(cancellationPolicy === 'CANCEL_24H'){
        result = 'Được hủy trước 24h';
    }
    else if(cancellationPolicy === 'CANCEL_5D'){
        result = 'Được hủy trước 5 ngày';
    }
    else if(cancellationPolicy === 'CANCEL_7D'){
        result = 'Được hủy trước 7 ngày';
    }
    else if(cancellationPolicy === 'CANCEL_15D'){
        result = 'Được hủy trước 15 ngày';
    }
    else if(cancellationPolicy === 'CANCEL_30D'){
        result = 'Được hủy trước 30 ngày';
    }

    if (cancellationFeeRate > 0) {
        result += ` và phải trả phí hủy ${cancellationFeeRate}%`;
    }
    else {
        result += ' và không phải trả phí hủy';
    }   

    return result;
}