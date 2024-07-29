import { useState, useEffect, useContext } from 'react';
import { SearchContext } from '~/contexts/SearchContext';
import UserAdmin from '~/pages/admin/LayoutUserAdmin/UserAdmin';
import cmsUserAPI from '~/services/apis/adminAPI/cmsUserAPI';
import LoadingAdmin from '~/components/Admin/LoadingAdmin/LoadingAdmin';

const LayoutUserAdmin = () => {
    const searchContext = useContext(SearchContext);
    const [listUser, setListUser] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        setIsLoading(true);
        cmsUserAPI
            .getAllUserWithPaging()
            .then((dataResponse) => {
                setListUser(dataResponse.data.content);
                setIsLoading(false);
            })
            .catch((error) => {});
    }, []);

    return <>{isLoading ? <LoadingAdmin /> : <UserAdmin data={listUser} setListUser={setListUser} />}</>;
};

export default LayoutUserAdmin;
