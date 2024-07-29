import './Facility.scss';
import { t } from 'i18next';

import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

export default function Facility(props) {
    const { data } = props;
    const infoFacilityList = data.flatMap(item =>  item.infoFacilityList)

    var settings = {
        dots: false,
        infinite: false,
        speed: 500,
        slidesToShow: 6,
        slidesToScroll: 6,
  
    };
    return (
        <div className="facility">
            <Slider {...settings}>
                {
                    infoFacilityList.map((facility, index) => (
                        <div key={index} className="facility-item">
                            <img src={facility.imageUrl} alt="icon-facility" className="icon-facility" />
                            <p className='name-facility'>{facility.facilityName}</p>
                        </div>
                    ))
                }
            </Slider>
        </div>
    );
}
