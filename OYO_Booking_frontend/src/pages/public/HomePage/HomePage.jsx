import HomeSlider from '~/components/HomeSlider/HomeSlider';
import Popular from '~/components/Popular/Popular';
import RoomPopular from '~/components/RoomPopular/RoomPopular';
import FramePage from '~/components/FramePage/FramePage';
import TypeAccomPopupar from '~/components/TypeAccomPopular/TypeAccomPopupar';
import { useEffect, useState } from 'react';
import clientAccomPlaceAPI from '~/services/apis/clientAPI/clientAccomPlaceAPI';
import { useSelector } from 'react-redux';
import HomestayRecommend from '~/components/HomestayRecommend/HomestayRecommend';

export default function HomePage() {
    const [homestayRecommends, setHomestayRecommends] = useState([]);
    const currentUser = useSelector((state) => state.user.current);
    useEffect(() => {
        if (currentUser)
            clientAccomPlaceAPI.getAccomPlaceRecommend().then((res) => setHomestayRecommends(res?.data?.content));
    }, [currentUser]);
    return (
        <FramePage>
            <HomeSlider />
            <TypeAccomPopupar />
            {homestayRecommends.length > 0 ? <HomestayRecommend homestayRecommends={homestayRecommends} /> : null}
            <RoomPopular />
            <Popular />
        </FramePage>
    );
}
