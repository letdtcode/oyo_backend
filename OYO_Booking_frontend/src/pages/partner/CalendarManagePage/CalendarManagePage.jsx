import './CalendarManagePage.scss';
import React from 'react';
import CalendarDateBooked from './CalendarDateBooked/CalendarDateBooked';
import { useSelector } from 'react-redux';
import Skeleton from '@mui/material/Skeleton'; // Import Skeleton
import { useFetchAccomData } from '~/redux/managerAccomSlice';
import FramePage from '~/components/FramePage/FramePage';
import nodata from '~/assets/img/no-data.jpg';

export default function CalendarManagePage() {
    useFetchAccomData();
    const { accomPriceCustom, loadingPriceCustom } = useSelector((state) => state.managerAccom);

    const renderSkeletonLoader = () => (
        <div className="skeleton-loader">
            {Array(5)
                .fill()
                .map((_, index) => (
                    <Skeleton key={index} variant="rectangular" width="100%" height={50} />
                ))}
        </div>
    );

    const renderNoData = () => (
        <div className="no-data">
            <img src={nodata} alt="No data" />
            <p>Không có dữ liệu</p>
        </div>
    ); const bannerData = {
        title: 'Quản lý lịch',
        subtitle: 'Xem và quản lý các lịch đặt phòng của khách hàng',
        imgSrc: 'https://media.licdn.com/dms/image/D5612AQEixzZoVo1fQg/article-cover_image-shrink_600_2000/0/1692126166662?e=2147483647&v=beta&t=nB3Glrfy-XZ2UR_ISolmxhBChWt2fTYgdcgBdmEh7WM' // Đặt hình ảnh banner tại đây nếu có
    };

    return (
        <FramePage ownerPage={true}  bannerData={bannerData}>
            <div className="calendar-manager">
                <div className="calendar-manager__header">
                    <h1 className="page-header">Quản lý lịch</h1>
                    {loadingPriceCustom === 'loading' ? (
                        renderSkeletonLoader()
                    ) : accomPriceCustom && accomPriceCustom.length > 0 ? (
                        <CalendarDateBooked accomPriceCustom={accomPriceCustom} />
                    ) : (
                        renderNoData()
                    )}
                </div>
            </div>
        </FramePage>
    );
}
