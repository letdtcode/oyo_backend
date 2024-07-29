import { NavLink } from 'react-router-dom';
import './ConfirmOwner.scss';
import Slider from 'react-slick';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import Logo from '~/assets/svg/logo.svg';

import { useSelector } from 'react-redux';

import formatPrice from '~/utils/formatPrice';
import { t } from 'i18next';

const ConfirmOwner = (props) => {
    const setupRoomHost = useSelector((state) => state.settingowner.detailRoom);
    const settings = {
        fade: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1
    };
    return (
        <div className="confirm-page">
            <NavLink to="/" className="logo">
                <img src={Logo} alt="company logo" className="logo-bg" />
            </NavLink>
            <div className="content-confirm">
                <div className="row" style={{ margin: '0', width: '100%' }}>
                    <div
                        className="col l-8 m-7 c-12"
                        style={{
                            height: '80vh',
                            display: 'grid',
                            alignContent: 'center',
                            paddingLeft: '50px'
                        }}
                    >
                        <h1>{t('setupOwner.confirm.ready')}</h1>
                        <span>{t('setupOwner.confirm.content_1')}</span>
                        <span>{t('setupOwner.confirm.content_2')}</span>
                        <br /> <br /> <br />
                        <div className="status">
                            <p>{t('setupOwner.confirm.create')}</p>
                            <div className="status-icon">
                                <CheckCircleOutlineIcon className="icon-success" />{' '}
                                <p>{t('setupOwner.confirm.completed')}</p>
                            </div>
                        </div>
                        <br />
                        <div className="status">
                            <p>{t('setupOwner.confirm.active')}</p>
                            <span>
                                {t('setupOwner.confirm.active_content')}
                                <br />
                            </span>

                            <div className="status-icon" style={{ marginTop: '10px' }}>
                                <CheckCircleOutlineIcon className="icon-success" />{' '}
                                <p>{t('setupOwner.confirm.completed')}</p>
                            </div>
                        </div>
                    </div>
                    <div
                        className="col l-4 m-5 c-12"
                        style={{
                            height: '90vh',
                            alignContent: 'center',
                            paddingRight: '50px'
                        }}
                    >
                        <div className="img-confirm">
                            {/* {props?.imagesOfHome.map((image, index) => (
                                    <img
                                        key={index}
                                        src={URL.createObjectURL(new Blob([image], { type: 'image/jpeg' }))}
                                        alt={image.name}
                                        style={{ width: '200px', margin: '5px' }}
                                    />
                                ))} */}
                            <Slider {...settings}>
                                {props?.imagesOfHome.length !== 0 &&
                                    props?.imagesOfHome?.map((image, index) => (
                                        <div key={index} >
                                            <img
                                                src={URL.createObjectURL(new Blob([image], { type: 'image/jpeg' }))}
                                                alt="room_hot"
                                                className="image-home"
                                            />
                                        </div>
                                    ))}
                            </Slider>
                        </div>
                        <div className="container__card">
                            <div className="card">
                                <h3>
                                    {setupRoomHost.accomCateName}: {setupRoomHost.accomName}
                                </h3>
                                <h3>Diện tích: {setupRoomHost.acreage}</h3>
                                <h3>
                                    {setupRoomHost.numHouseAndStreetName}, {setupRoomHost.wardName},{' '}
                                    {setupRoomHost.districtName}, {setupRoomHost.provinceName}
                                </h3>
                                <h3>{setupRoomHost.numPeople} Khách</h3>
                                <h3>
                                    {setupRoomHost.numBathRoom} phòng tắm, {setupRoomHost.numBedRoom} phòng ngủ,{' '}
                                    {setupRoomHost.numKitchen} phòng bếp
                                </h3>
                                {/* <h3>{setupRoomHost.description}</h3> */}
                                <div className='price'>
                                    <h3></h3>
                                    <h3>{`${setupRoomHost.pricePerNight} VND / ngày`}</h3>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ConfirmOwner;
