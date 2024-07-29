import { Component } from 'react';
import Slider from 'react-slick';
import bannerOne from '~/assets/imageBanner/banner3.jpg';
import bannerTwo from '~/assets/imageBanner/banner6.jpg';
import bannerThree from '~/assets/imageBanner/banner8.jpg';
import bannerFour from '~/assets/imageBanner/banner7.jpg';
import bannerFive from '~/assets/imageBanner/banner9.jpg';
import SearchHome from '../SearchHome/SearchHome';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import SearchData from '../../mockdata/SearchData.json';
import { t } from 'i18next';
import './HomeSlider.scss';

export default class SimpleSlider extends Component {
    render() {
        var settings = {
            dots: true,
            infinite: true,
            speed: 500,
            slidesToShow: 1,
            slidesToScroll: 1,
            autoplay: true,
            autoplaySpeed: 5000,
            initialSlide: 0,
            responsive: [
                {
                    breakpoint: 1024,
                    settings: {
                        slidesToShow: 1,
                        slidesToScroll: 1,
            
                    }
                },
                {
                    breakpoint: 600,
                    settings: {
                        slidesToShow: 1,
                        slidesToScroll: 1,
        
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
        return (
            <div className="slider__home">
                <Slider {...settings}>
                    <div>
                        <img src={bannerOne} alt="home" className="slider__home-item" />
                    </div>
                    <div>
                        <img src={bannerTwo} alt="home" className="slider__home-item" />
                    </div>
                    <div>
                        <img src={bannerThree} alt="home" className="slider__home-item" />
                    </div>
                    <div>
                        <img src={bannerFour} alt="home" className="slider__home-item" />
                    </div>
                    <div>
                        <img src={bannerFive} alt="home" className="slider__home-item" />
                    </div>
                </Slider>
                <div className="content-search">
                    <h1 className="title-home">{t('title.home')}</h1>
                    <p>{t('contentMain.homeinsearch')}</p>
                    <SearchHome placeholder={t('placeholder.searchHome')} data={SearchData} />
                </div>
            </div>
        );
    }
}
