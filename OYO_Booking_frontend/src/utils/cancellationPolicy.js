export const cancellationPolicyToTime = (cancellationPolicy) => {
    switch (cancellationPolicy) {
        case 'CANCEL_24H':
            return 1;
        case 'CANCEL_5D':
            return 5;
        case 'CANCEL_7D':
            return 7;
        case 'CANCEL_15D':
            return 15;
        case 'CANCEL_30D':
            return 30;
        default:
            return 0;
    }
};
