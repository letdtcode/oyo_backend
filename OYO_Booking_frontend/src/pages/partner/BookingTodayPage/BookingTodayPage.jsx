import './BookingTodayPage.scss';
import TabComponent from './MainOwnerSetting/TabComponent';
import FramePage from '~/components/FramePage/FramePage';
import gif1 from '~/assets/imageMaster/tohomesetup.gif'; // or any other image you want to use

const BookingTodayPage = () => {
    const bannerData = {
        title: 'Quản lý khách đặt phòng',
        subtitle: 'Theo dõi tình trạng check-in và check-out của khách hàng một cách nhanh chóng và dễ dàng.',
        imgSrc: gif1 
    };

    return (
        <FramePage ownerPage={true} bannerData={bannerData}>
            <div className="owner__setting">
                <div className="tab-content">
                    <TabComponent />
                </div>
            </div>
        </FramePage>
    );
};

export default BookingTodayPage;
