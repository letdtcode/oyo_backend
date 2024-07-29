import { useState, useEffect } from 'react';
import FacilityCategoryAdmin from '~/pages/admin/LayoutFacilityCategoryAdmin/FacilityCategoryAdmin';
import cmsFacilityCategoryAPI from '~/services/apis/adminAPI/cmsFacilityCategoryAPI';
import LoadingAdmin from '~/components/Admin/LoadingAdmin/LoadingAdmin';

const LayoutFacilityCategoryAdmin = () => {
    const [listFacilityCategory, setListFacilityCategory] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        setIsLoading(true);
        cmsFacilityCategoryAPI.getAllFacilityCategoryWithPaging().then((dataResponse) => {
            setListFacilityCategory(dataResponse.data.content);
            setIsLoading(false);
        });
    }, []);

    const handleChangeData = (data) => {
        setListFacilityCategory(data);
    };

    return (
        <>
            {isLoading ? (
                <LoadingAdmin />
            ) : (
                <FacilityCategoryAdmin data={listFacilityCategory} setList={handleChangeData} />
            )}
        </>
    );
};

export default LayoutFacilityCategoryAdmin;
