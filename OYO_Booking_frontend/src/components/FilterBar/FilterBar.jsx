import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import { useEffect, useState } from 'react';
import Slider from 'react-slick';
import publicAccomPlaceAPI from '~/services/apis/publicAPI/publicAccomPlaceAPI';
import Button from '@mui/material/Button';
import { t } from 'i18next';
import './FilterBar.scss';
import DialogFilter from '../DialogFilter/DialogFilter';
import { useDispatch } from 'react-redux';
import filterAcomSlice from '~/redux/filterAccom';
import { useNavigate } from 'react-router-dom';

const itemHomeStayCateAll = {
    accomCateName: 'Tất cả',
    description: 'Tất cả loại hình cho thuê',
    icon: 'https://img.icons8.com/dotty/25/1A1A1A/home.png',
    imageUrl:
        'https://res.cloudinary.com/dyv5zrsgj/image/upload/v1703612695/oyo_booking/accom_category/q3hohmoclks5q2ypfu6s',
    status: 'ENABLE'
};

const FilterBar = (props) => {
    const settings = {
        dots: true,
        infinite: true,
        speed: 1500,
        slidesToShow: 10,
        slidesToScroll: 10,
        initialSlide: 0
    };
    const nagavite = useNavigate();
    const dispatch = useDispatch();
    const [listAccomCateData, setListAccomCateData] = useState(null);
    const [indexActive, setIndexActive] = useState(-1);
    useEffect(() => {
        async function fetchData() {
            const res = await publicAccomPlaceAPI.getAllAccomCategoryInfo();
            setListAccomCateData([itemHomeStayCateAll, ...res.data]);
        }
        fetchData();
    }, []);
    const handleFilterCate = (index, current) => {
        setIndexActive(index);
        if (current === null) {
            dispatch(filterAcomSlice.actions.reset());
            dispatch(filterAcomSlice.actions.cateAcoom());
        } else {
            if (index === 0) {
                dispatch(filterAcomSlice.actions.reset());
                dispatch(filterAcomSlice.actions.cateAcoom());
            } else {
                dispatch(filterAcomSlice.actions.reset());
                dispatch(filterAcomSlice.actions.cateAcoom(current.accomCateName));
            }
        }
    };
    const handleReset = async (e) => {
        e.preventDefault();
        props.setState(() => ({
            items: Array.from({ length: 0 }),
            hasMore: true
        }));
        // nagavite('/list-accom');
        setIndexActive(-1);
        dispatch(filterAcomSlice.actions.reset());
    };

    return (
        <div className="filter-bar">
            <Slider {...settings}>
                {listAccomCateData?.map((current, index) => (
                    <div key={index}>
                        <div
                            className={`slider__item-filter  ${index === indexActive && 'active'}`}
                            onClick={() => handleFilterCate(index, current)}
                        >
                            <div className="icon-filter">
                                <img src={current?.icon} alt="icon-filter" />
                            </div>
                            <div className="title-filter">
                                <p>{current?.accomCateName}</p>
                            </div>
                        </div>
                    </div>
                ))}
            </Slider>
            <DialogFilter
                setState={props.setState}
                filterData={props.filterData}
                pagi={props.pagi}
                dataQueryDefauld={props.dataQueryDefauld}
            />
            <Button className="btn-all" variant="outlined" onClick={handleReset}>
                {t('common.reload')}{' '}
            </Button>
        </div>
    );
};
export default FilterBar;
