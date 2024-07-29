import { useState, useEffect } from 'react';
import TypeBedAdmin from '~/pages/admin/LayoutTypeBedAdmin/TypeBedAdmin';
import cmsTypeBedAPI from '~/services/apis/adminAPI/cmsTypeBedAPI';
import LoadingAdmin from '~/components/Admin/LoadingAdmin/LoadingAdmin';

const LayoutTypeBedAdmin = () => {
    const [listTypeBed, setListTypeBed] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        setIsLoading(true);
        cmsTypeBedAPI.getAllTypeBedWithPaging().then((dataResponse) => {
            setListTypeBed(dataResponse.data.content);
            setIsLoading(false);
        });
    }, []);

    const handleChangeData = (data) => {
        setListTypeBed(data);
    };

    return <>{isLoading ? <LoadingAdmin /> : <TypeBedAdmin data={listTypeBed} setList={handleChangeData} />}</>;
};

export default LayoutTypeBedAdmin;
