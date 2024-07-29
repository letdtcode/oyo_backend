import { useState, useEffect } from 'react';
import ListFacilityByCategory from './ListFacilityByCategory/ListFacilittByCategory';
import publicFacilityAPI from '~/services/apis/publicAPI/publicFacilityAPI';

const ListFacilityFilter = (props) => {
    const [facilityCateList, setFacilityCateList] = useState(null);
    useEffect(() => {
        async function fetchData() {
            const res = await publicFacilityAPI.getAllDataFacility();
            setFacilityCateList(res.data);
        }
        fetchData();
    }, []);
    return (
        <div style={{ marginTop: '30px' }}>
            {facilityCateList?.map((current, index) => (
                <div key={index}>
                    <ListFacilityByCategory
                        data={props.data}
                        setData={props.setData}
                        facilityList={current.infoFacilityList}
                        facilityCateName={current.faciCateName}
                    />
                </div>
            ))}
        </div>
    );
};
export default ListFacilityFilter;
