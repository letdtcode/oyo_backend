import ImageSetting from '~/pages/partner/ManagerRoom/GallerySetting/ImageSetting/ImageSetting';
import LocationSetting from '~/components/HostSetting/LocationSetting/LocationSetting';
import NavbarOwner from '~/components/NavbarOwner/NavbarOwner';
import ScrollspyComponent from '~/components/Scrollspy/Scrollspy';
import GeneralInfoSetting from '~/pages/partner/ManagerRoom/GeneralInfoSetting/GeneralInfoSetting';
import './ManagerRoom.scss';
import CountRoomSetting from '~/pages/partner/ManagerRoom/CountRoomSetting/CountRoomSetting';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import publicAccomPlaceAPI from '~/services/apis/publicAPI/publicAccomPlaceAPI';
import PriceDiscountSurchagre from '~/pages/test/PriceDiscountSurchagre/PriceDiscountSurchagre';
import { useSelector, useDispatch } from 'react-redux';
import VideoIntroSetting from '~/pages/partner/ManagerRoom/GallerySetting/VideoIntroSetting/VideoIntroSetting';
import PolicySetting from './PolicySetting/PolicySetting';
import PaymentInfoSetting from './PaymentInfoSetting/PaymentInfoSetting';
import FacilitySetting from '~/components/HostSetting/FacilitySetting/FacilitySetting';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import GallerySetting from './GallerySetting/GallerySetting';

const infoLink = {
    name: 'Chi tiết nhà cho thuê',
    urlLink: '/host/setting'
};

const backUrl = '/host/setting';
const item = ['', 'section1', 'section2', 'section3', 'section4', 'section5', 'section6', 'section7'];

const ManagerRoom = () => {
    const params = useParams();

    const children = [
        {
            id: '#section1',
            to: 'section1',
            info: 'Thông tin chung',
            comp: <GeneralInfoSetting accomId={params.idHome} />
        },
        {
            id: '#section2',
            to: 'section2',
            info: 'Địa chỉ chỗ nghỉ',
            comp: <LocationSetting />
        },
        {
            id: '#  ',
            to: 'section3',
            info: 'Tiện ích',
            comp: <FacilitySetting />
        },
        {
            id: '#section4',
            to: 'section4',
            info: 'Hình ảnh & Video',
            comp: <GallerySetting accomId={params.idHome} />
        },

        {
            id: '#section5',
            to: 'section5',
            info: 'Thiết lập phòng',
            comp: <CountRoomSetting accomId={params.idHome} />
        },

        {
            id: '#section6',
            to: 'section6',
            info: 'Chính sách',
            comp: <PolicySetting accomId={params.idHome} />
        },
        {
            id: '#section7',
            to: 'section7',
            info: 'Thông tin thanh toán',
            comp: <PaymentInfoSetting accomId={params.idHome} />
        }
    ];

    return (
        <div className="manager-room">
            <NavbarOwner />
            <ScrollspyComponent children={children} item={item} infoLink={infoLink} backUrl={backUrl} />
        </div>
    );
};

export default ManagerRoom;
