import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';

const managerAccomSlice = createSlice({
    name: 'managerAccom',
    initialState: {
        accomApproved: [],
        accomPriceCustom: [],
        loadingApproved: 'idle',
        loadingPriceCustom: 'idle',
        error: null
    },
    reducers: {
        reloadResources(state) {
            state.loading = 'idle';
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(fetchAccomApproved.pending, (state) => {
                state.loadingApproved = 'loading';
            })
            .addCase(fetchAccomApproved.fulfilled, (state, action) => {
                state.accomApproved = action.payload;
                state.loadingApproved = 'succeeded';
            })
            .addCase(fetchAccomApproved.rejected, (state, action) => {
                state.error = action.error.message;
                state.loadingApproved = 'failed';
            })

            .addCase(fetchAccomPriceCustom.pending, (state) => {
                state.loadingPriceCustom = 'loading';
            })
            .addCase(fetchAccomPriceCustom.fulfilled, (state, action) => {
                state.loadingPriceCustom = 'succeeded';
                state.accomPriceCustom = action.payload;
            })
            .addCase(fetchAccomPriceCustom.rejected, (state, action) => {
                state.loadingPriceCustom = 'failed';
                state.error = action.error.message;
            });
    }
});

export default managerAccomSlice;

export const fetchAccomApproved = createAsyncThunk('managerAccom/fetchAccomApproved', async () => {
    const response = await partnerManageAccomAPI.getListAccomApproved();
    return response.data.content;
});
export const fetchAccomPriceCustom = createAsyncThunk('managerAccom/fetchAccomPriceCustom', async () => {
    const response = await partnerManageAccomAPI.getListAccomWithPriceCustom();
    return response.data.content;
});

export const useFetchAccomData = () => {
    const dispatch = useDispatch();
    const loadingPriceCustom = useSelector((state) => state.managerAccom.loadingPriceCustom);
    const loadingApproved = useSelector((state) => state.managerAccom.loadingApproved);
    useEffect(() => {
        if (loadingPriceCustom === 'idle') {
            dispatch(fetchAccomPriceCustom());
        }
        if (loadingApproved === 'idle') {
            dispatch(fetchAccomApproved());
        }
    }, [dispatch]);
};
