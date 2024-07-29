import './TypeAccomPopular.scss';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import Slider from 'react-slick';
import publicAccomPlaceAPI from '~/services/apis/publicAPI/publicAccomPlaceAPI';
import { useEffect, useState } from 'react';
import filterAcomSlice from '~/redux/filterAccom';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { t } from 'i18next';

const data = [];

const settings = {
    dots: true,
    infinite: true,
    speed: 1000,
    slidesToShow: 4,
    slidesToScroll: 2,
    initialSlide: 0,
    responsive: [
        {
            breakpoint: 1024,
            settings: {
                slidesToShow: 2,
                slidesToScroll: 2,
                infinite: true,
                dots: true
            }
        },
        {
            breakpoint: 980,
            settings: {
                slidesToShow: 3,
                slidesToScroll: 3,
                infinite: true,
                dots: true
            }
        },
        {
            breakpoint: 600,
            settings: {
                slidesToShow: 1,
                slidesToScroll: 1
            }
        },
        {
            breakpoint: 480,
            settings: {
                slidesToShow: 1,
                slidesToScroll: 1
            }
        }
    ]
};

const TypeAccomPopupar = () => {
    const nagavite = useNavigate();
    const dispatch = useDispatch();
    const [listAccomCateData, setListAccomCateData] = useState(null);
    useEffect(() => {
        async function fetchData() {
            const res = await publicAccomPlaceAPI.getAllAccomCategoryInfo();
            setListAccomCateData(res.data);
        }
        fetchData();
    }, []);

    if (!listAccomCateData) return null;

    const handleFilterCate = (cate) => {
        dispatch(filterAcomSlice.actions.cateAcoom(cate));
        nagavite('/list-accom');
    };
    return (
        <div className="type-accom-popular">
            <div className="type-accom-popular__head">
                <h1>{t('title.homestayCategory')}</h1>
            </div>
            <Slider {...settings}>
                {listAccomCateData.map((item, index) => (
                    <div key={index} style={{ width: '100%' }} onClick={(e) => handleFilterCate(item.accomCateName)}>
                        <div className="slide" style={{ backgroundImage: `url(${item.imageUrl})`, height: '279px' }}>
                            <h1>{item.accomCateName}</h1>
                            <span>{item.description}</span>
                        </div>
                    </div>
                ))}
            </Slider>
        </div>
    );
};

export default TypeAccomPopupar;
