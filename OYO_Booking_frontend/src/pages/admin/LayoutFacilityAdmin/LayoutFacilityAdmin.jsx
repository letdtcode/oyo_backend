import { useState, useEffect } from 'react';
import FacilityAdmin from '~/pages/admin/LayoutFacilityAdmin/FacilityAdmin';
import cmsFacilityAPI from '~/services/apis/adminAPI/cmsFacilityAPI';
import cmsFacilityCategoryAPI from '~/services/apis/adminAPI/cmsFacilityCategoryAPI';
import LoadingAdmin from '~/components/Admin/LoadingAdmin/LoadingAdmin';

const LayoutFacilityAdmin = () => {
    const [listFacility, setListFacility] = useState([]);
    const [listFacilityCategory, setListFacilityCategory] = useState([{}]);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        setIsLoading(true);
        cmsFacilityAPI.getAllFacilityWithPaging().then((dataResponse) => {
            setListFacility(dataResponse.data.content);
            cmsFacilityCategoryAPI.getAllFacilityCategoryWithPaging().then((dataResponse) => {
                setListFacilityCategory(dataResponse.data.content);
            });
            setIsLoading(false);
        });
    }, []);

    const handleChangeData = (data) => {
        setListFacility(data);
    };

    return (
        <>
            {isLoading ? (
                <LoadingAdmin />
            ) : (
                <FacilityAdmin
                    data={listFacility}
                    setList={handleChangeData}
                    dataFacilityCategory={listFacilityCategory}
                />
            )}
        </>
    );
};

export default LayoutFacilityAdmin;
