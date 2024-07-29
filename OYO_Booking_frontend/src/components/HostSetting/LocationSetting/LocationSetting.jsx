import { useState, useEffect } from 'react';
import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import SelectAddress from '~/components/SelectAddress/SelectAddress';
import './LocationSetting.scss';
import { useSnackbar } from 'notistack';
import { useForm } from 'react-hook-form';
import { useParams } from 'react-router-dom';
import GoogleMapReact from 'google-map-react';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import { useDispatch } from 'react-redux';
import LocationOnIcon from '@mui/icons-material/LocationOn';

const LocationCurrent = () => <LocationOnIcon style={{ color: 'red', fontSize: 'xx-large' }} />;

export default function LocationSetting(props) {
    const [expanded, setExpanded] = useState(false);
    const {
        handleSubmit,
        register,
        setValue,
        getValues,
        formState: { isSubmitting }
    } = useForm();

    const [address, setAddress] = useState({});
    const { enqueueSnackbar } = useSnackbar();
    const params = useParams();
    const addressDetail = address?.wardCode
        ? address?.numHouseAndStreetName + ', ' + address?.wardName + ', ' + address?.provinceName
        : 'Nhập địa chỉ chi tiết homestay của bạn';

    useEffect(() => {
        partnerManageAccomAPI.getAddress(params.idHome).then((dataResponse) => {
            setAddress({
                provinceCode: dataResponse?.data?.provinceAddress.provinceCode,
                provinceName: dataResponse?.data?.provinceAddress.provinceName,
                wardCode: dataResponse?.data?.wardAddress.wardCode,
                wardName: dataResponse?.data?.wardAddress.wardName,
                districtCode: dataResponse?.data?.districtAddress.districtCode,
                districtName: dataResponse?.data?.districtAddress.districtName,
                numHouseAndStreetName: dataResponse?.data?.numHouseAndStreetName,
                longitude: dataResponse?.data?.longitude,
                latitude: dataResponse?.data?.latitude,
                guide: dataResponse?.data?.guide
            });
            setValue('guide', dataResponse?.data?.guide);
        });
    }, [params.idHome]);

    const handleChange = (panel) => (event, isExpanded) => {
        setExpanded(isExpanded ? panel : false);
    };

    const handleMapClick = (event) => {
        setAddress({ ...address, latitude: event.lat, longitude: event.lng });
    };

    const handleClose = () => {
        setExpanded(false);
    };

    const onSubmit = (event) => {
        event.preventDefault();
        const newData = {
            data: {
                provinceCode: address.provinceCode,
                districtCode: address.districtCode,
                wardCode: address.wardCode,
                numHouseAndStreetName: address.numHouseAndStreetName,
                longitude: address.longitude,
                latitude: address.latitude,
                guide: address.guide
            },
            id: params.idHome
        };

        console.log(newData);
        partnerManageAccomAPI
            .updateAddress(newData)
            .then((res) => {
                enqueueSnackbar('Cập nhật thành công', {
                    variant: 'success'
                });
            })
            .catch((err) => {
                enqueueSnackbar(err, {
                    variant: 'error'
                });
            });
    };

    return (
        <div className="container__locationSetting">
            <h3>Vị trí</h3>
            <form onSubmit={onSubmit}>
                <Accordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls="panel1bh-content"
                        id="panel1bh-header"
                    >
                        <p style={{ width: '33%', flexShrink: 0 }}>Địa chỉ chi tiết</p>
                        <p style={{ color: 'text.secondary' }}>{addressDetail}</p>
                    </AccordionSummary>
                    <AccordionDetails>
                        <SelectAddress setData={setAddress} data={address}></SelectAddress>
                        <input
                            className="input-address"
                            value={address?.numHouseAndStreetName}
                            onChange={(e) => setAddress({ ...address, numHouseAndStreetName: e.target.value })}
                        />
                        <div className="" style={{ width: 800, height: 500 }}>
                            <GoogleMapReact
                                bootstrapURLKeys={{ key: import.meta.env.VITE_API_KEY_GOOGLE }}
                                defaultCenter={{ lat: 10.762622, lng: 106.660172 }}
                                defaultZoom={14}
                                center={
                                    address?.latitude && address?.longitude
                                        ? { lat: address?.latitude, lng: address?.longitude }
                                        : { lat: 10.762622, lng: 106.660172 }
                                }
                                onClick={handleMapClick}
                                className="google-map"
                            >
                                {address?.latitude && address?.longitude && (
                                    <LocationCurrent
                                        className="icon__location-current"
                                        lat={address?.latitude}
                                        lng={address?.longitude}
                                    />
                                )}
                            </GoogleMapReact>
                        </div>
                        <div className="content-input">
                            <h4>Hướng dẫn nhà/phòng cho thuê</h4>
                            <p>Thêm hướng dẫn cho nơi ở của bạn để khách có thể dể dàng tiếp cận hơn.</p>
                            <textarea className="text-input" {...register('guide')} />
                        </div>

                        <div className="btn">
                            <p onClick={handleClose} className="btn-close">
                                Hủy
                            </p>
                            <button type="submit" className="btn-save">
                                Lưu
                            </button>
                        </div>
                    </AccordionDetails>
                </Accordion>
            </form>
        </div>
    );
}
