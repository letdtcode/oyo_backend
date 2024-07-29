import { useState, useEffect, useContext } from 'react';
import { SearchContext } from '~/contexts/SearchContext';
import AccomAdmin from '~/pages/admin/LayoutAccomAdmin/AccomAdmin';
import cmsAccomPlaceAPI from '~/services/apis/adminAPI/cmsAccomPlaceAPI';
import LoadingAdmin from '~/components/Admin/LoadingAdmin/LoadingAdmin';

const LayoutAccomAdmin = () => {
    // const searchContext = useContext(SearchContext);
    const [isLoading, setIsLoading] = useState(false);
    const [listAccom, setListAccom] = useState([]);

    useEffect(() => {
        setIsLoading(true);
        cmsAccomPlaceAPI.getAllAcommPlaceWithPaging().then((dataResponse) => {
            setListAccom(dataResponse.data.content);
            setIsLoading(false);
        });
    }, []);

    return <>{isLoading ? <LoadingAdmin /> : <AccomAdmin data={listAccom} setListAccom={setListAccom} />}</>;
};

export default LayoutAccomAdmin;
