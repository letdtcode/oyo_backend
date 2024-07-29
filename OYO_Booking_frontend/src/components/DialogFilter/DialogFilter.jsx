import { Button } from '@mui/material';
import { t } from 'i18next';
import { useEffect, useState } from 'react';
import DialogActions from '@mui/material/DialogActions';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import FilterAltOutlinedIcon from '@mui/icons-material/FilterAltOutlined';
import RangePriceFilter from './RangePriceFilter/RangePriceFilter';
import SelectAddress from '../SelectAddress/SelectAddress';
import ListFacilityFilter from './ListFacilityFilter/ListFacilityFilter';
import CountRoomFilter from './CountRoomFilter/CountRoomFilter';
import './DialogFilter.scss';
import { useDispatch, useSelector } from 'react-redux';
import filterAcomSlice from '~/redux/filterAccom';

const DialogFilter = (props) => {
    const dispatch = useDispatch();
    const filterAccom = useSelector((state) => state.filterAccom);
    const [open, setOpen] = useState(false);
    const [address, setAddress] = useState({});
    const [valuePriceRange, setValuePriceRange] = useState([]);
    const [facility, setFacility] = useState([]);
    const [numBathRoom, setNumBathRoom] = useState(0);
    const [numBedRoom, setNumBedRoom] = useState(0);
    const handleClose = () => {
        setOpen(false);
    };
    const handleClickOpen = () => {
        setOpen(true);
    };
    useEffect(() => {
        setAddress({
            provinceCode: filterAccom.provinceCode,
            districtCode: filterAccom.districtCode,
            wardCode: filterAccom.wardCode
        });
        setValuePriceRange([filterAccom.priceFrom, filterAccom.priceTo]);
        setFacility(filterAccom.facilityCode);
        setNumBedRoom(filterAccom.numBedRoom);
        setNumBathRoom(filterAccom.numBathRoom);
    }, [filterAccom, open]);

    const handleFilter = () => {
        dispatch(filterAcomSlice.actions.address(address));
        if (valuePriceRange[0] !== 1 || valuePriceRange[1] !== 5000000) {
            dispatch(filterAcomSlice.actions.valuePriceRange(valuePriceRange));
        }
        dispatch(filterAcomSlice.actions.facility(facility));
        dispatch(filterAcomSlice.actions.numBathRoom(numBathRoom));
        dispatch(filterAcomSlice.actions.numBedRoom(numBedRoom));
        props.setState(() => ({
            items: Array.from({ length: 0 }),
            hasMore: true
        }));
        handleClose();
    };

    return (
        <div className="dialog-filter">
            <Button variant="outlined" onClick={handleClickOpen} className="btn-show">
                <FilterAltOutlinedIcon />
                {t('common.filter')}
            </Button>
            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
                fullWidth={true}
                maxWidth="md"
            >
                <div className="container__filter paper">
                    <DialogTitle
                        id="alert-dialog-title"
                        sx={{
                            fontSize: '18px',
                            fontWeight: 'bold',
                            width: '600px',
                            marginBottom: '20px'
                        }}
                    >
                        {t('common.filter')}
                    </DialogTitle>
                    <DialogContent sx={{ fontSize: '19px', fontWeight: 'bold' }}>
                        {t('common.selectAddressYouWant')}
                        <SelectAddress data={address} setData={setAddress} />
                        <br /> <hr />
                    </DialogContent>
                    <DialogContent sx={{ fontSize: '19px', fontWeight: 'bold' }}>
                        {t('label.priceRange')}
                        <div style={{ marginTop: '30px' }}>
                            <RangePriceFilter values={valuePriceRange} setValues={setValuePriceRange} />
                        </div>
                        <br /> <hr />
                    </DialogContent>
                    <DialogContent sx={{ fontSize: '19px', fontWeight: 'bold' }}>
                        {t('label.convenient')}
                        <ListFacilityFilter data={facility} setData={setFacility} />
                    </DialogContent>

                    <DialogContent sx={{ fontSize: '19px', fontWeight: 'bold' }}>
                        {t('label.room')}
                        <div style={{ marginTop: '30px', marginBottom: '50px' }}>
                            <CountRoomFilter name={t('label.bedroom')} data={numBedRoom} setData={setNumBedRoom} />
                            <CountRoomFilter name={t('label.bathroom')} data={numBathRoom} setData={setNumBathRoom} />
                        </div>
                    </DialogContent>
                </div>

                <DialogActions>
                    <Button
                        onClick={handleClose}
                        color="error"
                        sx={{
                            fontSize: '16px',
                            textTransform: 'uppercase'
                        }}
                    >
                        {t('common.close')}
                    </Button>
                    <Button
                        onClick={handleFilter}
                        autoFocus
                        sx={{
                            fontSize: '16px',
                            textTransform: 'uppercase'
                        }}
                    >
                        {t('common.save')}
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
};
export default DialogFilter;
