import { useEffect, useState } from 'react';
import { createContext } from 'react';
import publicProvinceAPI from '~/services/apis/publicAPI/publicProvinceAPI';

export const LocateContext = createContext();

const LocateProvider = ({ children }) => {
    const [selectedProvince, setSelectedProvince] = useState('');
    const [selectedDistrict, setSelectedDistrict] = useState('');
    const [selectedWard, setSelectedWard] = useState('');

    const [provinces, setProvinces] = useState([]);
    const [districts, setDistricts] = useState([]);
    const [wards, setWards] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await publicProvinceAPI.getAllProvinceDetails();
                setProvinces(response.data);
            } catch (error) {}
        };
        fetchData();
    }, []);

    const handleProvinceChange = (e) => {
        setSelectedProvince(e.target.value);
        setSelectedDistrict('');
        setSelectedWard('');
        if (e.target.value) {
            const provinceName = e.target.value;
            const province = provinces.find((p) => p.provinceName === provinceName);
            if (province) {
                setDistricts(province.districtSet);
            }
        } else {
            setDistricts([]);
        }
    };

    const handleDistrictChange = (e) => {
        setSelectedDistrict(e.target.value);
        setSelectedWard('');
        if (e.target.value) {
            const districtName = e.target.value;
            const district = districts.find((d) => d.districtName === districtName);
            if (district) {
                setWards(district.wardSet);
            }
        } else {
            setWards([]);
        }
    };
    return (
        <LocateContext.Provider
            value={{
                provinces,
                selectedProvince,
                handleProvinceChange,
                districts,
                selectedDistrict,
                handleDistrictChange,
                wards,
                selectedWard,
                setSelectedWard
            }}
        >
            {children}
        </LocateContext.Provider>
    );
};

export default LocateProvider;
