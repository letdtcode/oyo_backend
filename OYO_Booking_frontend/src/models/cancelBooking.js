export const cancelBookingModel = {
    cancelLation: [
        { label: 'Trước 1 ngày', value: 'CANCEL_24H' },
        { label: 'Trước 5 ngày', value: 'CANCEL_5D' },
        { label: 'Trước 7 ngày', value: 'CANCEL_7D' },
        { label: 'Trước 15 ngày', value: 'CANCEL_15D' },
        { label: 'Trước 30 ngày', value: 'CANCEL_30D' },
        { label: 'Không hoàn tiền', value: 'NO_CANCEL' }
    ],

    cancellationFeeRate: [0, 5, 10, 15, 20, 25, 30, 40, 50]
};

export const policyPublicModel = {
    cancellationPolicy: null,
    cancellationFeeRate: null,
    allowEvent: [true, false],
    allowPet: [true, false],
    allowSmoking: [true, false]
};
