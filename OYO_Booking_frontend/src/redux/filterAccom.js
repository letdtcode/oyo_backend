import { createSlice } from '@reduxjs/toolkit';

const filterAcomSlice = createSlice({
    name: 'filterAccom',
    initialState: {
        provinceCode: null,
        accomCateName: null,
        districtCode: null,
        wardCode: null,
        priceFrom: null,
        priceTo: null,
        facilityCode: [],
        numBedRoom: null,
        numBathRoom: null,
    },
    reducers: {
        reset(state, action) {
            state.provinceCode = null;
            state.accomCateName = null;
            state.districtCode = null;
            state.wardCode = null;
            state.priceFrom = null;
            state.priceTo = null;
            state.facilityCode = [];
            state.numBedRoom = null;
            state.numBathRoom = null;
        },
        province(state, action) {
            state.provinceCode = action.payload;
        },
        cateAcoom(state, action) {
            state.accomCateName = action.payload;
        },
        address(state, action) {
            state.provinceCode = action.payload.provinceCode;
            state.districtCode = action.payload.districtCode;
            state.wardCode = action.payload.wardCode;
        },
        valuePriceRange(state, action) {
            state.priceFrom = action.payload[0]
            state.priceTo =action.payload[1]
        },
        facility(state, action) {
            state.facilityCode = action.payload;
        },
        numBedRoom(state, action) {
            state.numBedRoom = action.payload;
        },
        numBathRoom(state, action) {
            state.numBathRoom = action.payload;
        },
 
    }
});
export default filterAcomSlice;
