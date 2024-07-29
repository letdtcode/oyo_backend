import { useState, useEffect } from 'react';
import SurchargeCategoryAdmin from '~/pages/admin/LayoutSurchargeCategoryAdmin/SurchargeCategoryAdmin';
import cmsSurchargeCategoryAPI from '~/services/apis/adminAPI/cmsSurchargeCategoryAPI';
import LoadingAdmin from '~/components/Admin/LoadingAdmin/LoadingAdmin';

const LayoutSurchargeCategoryAdmin = () => {
    const [listSurchargeCategory, setListSurchargeCategory] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        setIsLoading(true);
        cmsSurchargeCategoryAPI.getAllSurchargeCategoryWithPaging().then((dataResponse) => {
            setListSurchargeCategory(dataResponse.data.content);
            setIsLoading(false);
        });
    }, []);

    const handleChangeData = (data) => {
        setListSurchargeCategory(data);
    };

    return (
        <>
            {isLoading ? (
                <LoadingAdmin />
            ) : (
                <SurchargeCategoryAdmin data={listSurchargeCategory} setList={handleChangeData} />
            )}
        </>
    );
};

export default LayoutSurchargeCategoryAdmin;
