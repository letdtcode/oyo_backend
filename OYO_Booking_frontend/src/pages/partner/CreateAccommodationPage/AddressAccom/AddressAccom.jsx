import './AddressAccom.scss';
import { t } from 'i18next';
import React, { useEffect, useState } from 'react';
import SelectAddress from '~/components/SelectAddress/SelectAddress';
import GoogleMapReact from 'google-map-react';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import mapAPI from '~/services/apis/mapAPI/mapAPI';
import { useDispatch, useSelector } from 'react-redux';
import { addressFormData, fullAddressFormData } from '~/models/address';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';



export default function AddressAccom({ id, save, doneSave }) {
    const [loading, setLoading] = useState(false);
    const [data, setData] = useState(fullAddressFormData);
    const [address, setAddress] = useState(addressFormData);
    useEffect(() => {
        if (id) {
            partnerManageAccomAPI.getAddress(id).then((res) => {
                setAddress({
                    provinceCode: res.data.provinceAddress.provinceCode,
                    districtCode: res.data.districtAddress.districtCode,
                    wardCode: res.data.wardAddress.wardCode,
                    provinceName: res.data.provinceAddress.provinceName,
                    districtName: res.data.districtAddress.districtName,
                    wardName: res.data.wardAddress.wardName
                });
                setData({
                    numHouseAndStreetName: res.data.numHouseAndStreetName,
                    provinceCode: res.data.provinceAddress.provinceCode,
                    districtCode: res.data.districtAddress.districtCode,
                    wardCode: res.data.wardAddress.wardCode,
                    longitude: res.data.longitude,
                    latitude: res.data.latitude,
                    guide: res.data.guide
                });
                setLoading(false);
            });
        }
    }, [id]);

    useEffect(() => {
        if (address.provinceCode && address.districtCode && address.wardCode && data.numHouseAndStreetName) {
            const addressFull = `${data.numHouseAndStreetName} ,${address.wardName}, ${address.districtName}, ${address.provinceName}`;
            console.log(addressFull);
            mapAPI.geoCodeAddress(addressFull).then((res) => {
                setData({
                    ...data,
                    provinceCode: address.provinceCode,
                    districtCode: address.districtCode,
                    wardCode: address.wardCode,
                    latitude: parseFloat(res[0].lat),
                    longitude: parseFloat(res[0].lon)
                });
            });
        }
    }, [address, data.numHouseAndStreetName]);
    const handleMapClick = (event) => {
        setData({ ...data, latitude: event.lat, longitude: event.lng });
    };
    useEffect(() => {
        if (save) {
            partnerManageAccomAPI
                .updateAddress({ id, data })
                .then((res) => {
                    doneSave(true);
                })
                .catch(() => {
                    doneSave(false);
                });
        }
    }, [save]);
    const LocationCurrent = () => <LocationOnIcon style={{ color: 'red', fontSize: 'xx-large' }} />;
    if (loading) return <div>loading</div>;
    return (
        <div className="address-info__content">
            <div className="row">
                <div className="box-address col l-6">
                    <label>{t('label.address')}</label>
                    <SelectAddress setData={setAddress} data={address} />
                    <p className="span-address-step1">{t('contentMess.address')}</p>
                    <input
                        name="input-address-step1"
                        type="text"
                        className="input-address-step1"
                        defaultValue={data.numHouseAndStreetName}
                        onBlur={(e) => {
                            setData({ ...data, numHouseAndStreetName: e.currentTarget.value });
                        }}
                        required
                    />
                    <p style={{ marginTop: 30, fontSize: 13, fontStyle: 'italic' }} className="span-address-step1">
                        {t('contentMess.guide')}
                    </p>
                    <textarea
                        name="input-guide-step1"
                        type="text"
                        className="input-guide-step1"
                        defaultValue={data.guide}
                        value={data.guide}
                        onChange={(e) => {
                            setData({ ...data, guide: e.currentTarget.value });
                        }}
                    />
                </div>
                <div className="container__google-map col l-6">
                    <GoogleMapReact
                        bootstrapURLKeys={{ key: import.meta.env.VITE_API_KEY_GOOGLE }}
                        defaultCenter={{ lat: 10.762622, lng: 106.660172 }}
                        defaultZoom={14}
                        center={
                            data.latitude && data.longitude
                                ? { lat: data.latitude, lng: data.longitude }
                                : { lat: 10.762622, lng: 106.660172 }
                        }
                        onClick={handleMapClick}
                        className="google-map"
                    >
                        <LocationCurrent
                                className="icon__location-current"
                                lat={data?.latitude|| 10.762622}
                                lng={data?.longitude|| 106.660172}
                                
                            />
                    
                    </GoogleMapReact>
                </div>
            </div>
        </div>
    );
}
