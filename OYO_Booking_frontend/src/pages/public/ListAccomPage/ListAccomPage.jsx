import FilterBar from '~/components/FilterBar/FilterBar';
import FramePage from '~/components/FramePage/FramePage';
import InfiniteScroll from 'react-infinite-scroll-component';
import { useEffect, useState } from 'react';
import publicAccomPlaceAPI from '~/services/apis/publicAPI/publicAccomPlaceAPI';
import SkeletonRoomItem from '~/components/Skeleton/SkeletonRoomItem';
import RoomItem from '~/components/RoomItem/RoomItem';
import loader from '~/assets/video/loader.gif';
import { transLateListTitle } from '~/services/thirdPartyAPI/translateAPI';
import { useSelector } from 'react-redux';
import './ListAccomPage.scss';
import { useLocation } from 'react-router-dom';

const ListAccomPage = () => {
    const [listDataRoom, setListDataRoom] = useState([]);
    const [queryParams, setQueryParams] = useState(false);
    const [loading, setLoading] = useState(false);
    const filterAccom = useSelector((state) => state.filterAccom);
    const [state, setState] = useState({
        pagi: 0,
        hasMore: true
    });

    useEffect(() => {
        const fildeFiler = [
            'provinceCode',
            'districtCode',
            'wardCode',
            'priceFrom',
            'priceTo',
            'numBathRoom',
            'numBedRoom',
            'accomCateName'
        ];
        let query = '';
        fildeFiler.forEach((item) => {
            if (filterAccom[item]) {
                query += `${item}=${filterAccom[item]}&`;
            }
        });
        if (filterAccom?.facilityCode.length > 0) {
            query += `${filterAccom.facilityCode.map((item) => `facilityCode=${item}`).join('&')}`;
        }
        setState({
            pagi: 0,
            hasMore: true
        });
        setQueryParams(query);
    }, [filterAccom]);

    useEffect(() => {
        if (queryParams !== false) {
            publicAccomPlaceAPI
                .getAllRoomsWithFilter({ queryParams: queryParams, pageNum: state.pagi, pageSize: 8 })
                .then(async (res) => {
                    console.log(res.data);
                    const data = await Promise.all(
                        res.data.content.flatMap((item) => {
                            return transLateListTitle(item);
                        })
                    );
                    if (state.pagi === 0) {
                        setListDataRoom(data);
                    } else {
                        setListDataRoom((prevState) => prevState.concat(data));
                    }
                    if ((res.data.pageNumber + 1) * res.data.pageSize >= res.data.totalElements) {
                        setState((prevState) => ({
                            ...prevState,
                            hasMore: false
                        }));
                    } else {
                        setState((prevState) => ({
                            ...prevState,
                            hasMore: true
                        }));
                    }
                    setLoading(false);
                })
                .catch((error) => {
                    setLoading(false);
                });
        }
    }, [queryParams, state.pagi, filterAccom.loading]);

    const filterData = (listDataNew) => {
        setListDataRoom(listDataNew);
    };

    const fetchMoreData = () => {
        if (listDataRoom.length === 0) return;
        setState((prevState) => ({
            ...prevState,
            pagi: prevState.pagi + 1
        }));
    };

    return (
        <FramePage>
            <FilterBar
                filterData={filterData}
                queryParams={queryParams}
                setQueryParams={setQueryParams}
                pagi={state.pagi}
                dataQueryDefauld={queryParams}
                setState={setState}
            />

            <InfiniteScroll
                dataLength={listDataRoom.length}
                next={fetchMoreData}
                hasMore={state.hasMore}
                loader={
                    <div className="loader">
                        <img src={loader} alt="loading..." className="image__loader" />
                    </div>
                }
                scrollableTarget="scrollableDiv"
                style={{ paddingTop: '10px', zIndex: '-1', margin: '0 100px' }}
            >
                <div className="row" style={{ margin: 0 }}>
                    {loading ? (
                        <SkeletonRoomItem />
                    ) : (
                        listDataRoom.map((room, index) => <RoomItem key={index} infoRoom={room} />)
                    )}
                </div>
            </InfiniteScroll>
        </FramePage>
    );
};
export default ListAccomPage;
