import React from 'react';
import Slider from 'react-slick';
// Import css files
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './BedRoomSlider.scss';
import BedIcon from '@mui/icons-material/Bed';

export default function BedRoomSlider(props) {
    let settings = {
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 3,
        slidesToScroll: 3,
        autoplay: true,
        autoplaySpeed: 5000,
        initialSlide: 0,
        responsive: [
            {
                breakpoint: 1024,
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
                    slidesToShow: 2,
                    slidesToScroll: 2,
                    initialSlide: 2
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
        <div className="slider__bedroom">
            <Slider {...settings}>
                {props?.bedRooms.map((bedRoom, index) => (
                    <div key={index}>
                        <div className="slider__item">
                            <div className="icon-bed">
                                <BedIcon />
                                <BedIcon />
                            </div>
                            <div className="title-bed">
                                <h2>{bedRoom.typeBedName}</h2>
                                {/* <p>{room?.nameOfBed}</p> */}
                            </div>
                        </div>
                    </div>
                ))}
            </Slider>
        </div>
    );
}
