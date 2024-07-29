import { useState, useEffect } from 'react';
import AccomCategoryAdmin from '~/pages/admin/LayoutAccomCategoryAdmin/AccomCategoryAdmin';
import cmsAccomCategoryAPI from '~/services/apis/adminAPI/cmsAccomCategoryAPI';
import LoadingAdmin from '~/components/Admin/LoadingAdmin/LoadingAdmin';

const LayoutAccomCategoryAdmin = () => {
    const [listAccomCategory, setListAccomCategory] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        setIsLoading(true);
        cmsAccomCategoryAPI.getAllAcommCategoryWithPaging().then((dataResponse) => {
            setListAccomCategory(dataResponse.data.content);
            setIsLoading(false);
        });
    }, []);

    const handleChangeData = (data) => {
        setListAccomCategory(data);
    };

    return (
        <>{isLoading ? <LoadingAdmin /> : <AccomCategoryAdmin data={listAccomCategory} setList={handleChangeData} />}</>
    );
};

export default LayoutAccomCategoryAdmin;
