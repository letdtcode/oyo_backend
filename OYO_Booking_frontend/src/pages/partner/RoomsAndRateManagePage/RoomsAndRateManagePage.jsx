import './RoomsAndRateManagePage.scss';
import { useDispatch, useSelector } from 'react-redux';
import RoomsAndRate from './RoomsAndRate/RoomsAndRate';
import Skeleton from '@mui/material/Skeleton'; // Import Skeleton
import { useEffect } from 'react';
import { useFetchAccomData } from '~/redux/managerAccomSlice';
import FramePage from '~/components/FramePage/FramePage';
import nodata from '~/assets/img/no-data.jpg';

export default function RoomsAndRateManagePage() {
    useFetchAccomData();
    const { accomPriceCustom, loadingPriceCustom, error } = useSelector((state) => state.managerAccom);

    const renderSkeletonTable = () => {
        return (
            <div className="skeleton-table">
                <div className="skeleton-table__body">
                    {Array(5)
                        .fill()
                        .map((_, rowIndex) => (
                            <div className="skeleton-table__row" key={rowIndex}>
                                <Skeleton variant="rectangular" width="100%" height={50} />
                                {Array(8)
                                    .fill()
                                    .map((_, colIndex) => (
                                        <Skeleton key={colIndex} variant="rectangular" width="100%" height={50} />
                                    ))}
                            </div>
                        ))}
                </div>
            </div>
        );
    };

    const renderNoData = () => {
        return (
            <div className="no-data">
                <img src={nodata} alt="No data" />
                <p>Không có dữ liệu</p>
            </div>
        );
    };
    const bannerData = {
        title: 'Quản lý phòng và giá',
        subtitle: 'Điều chỉnh và quản lý thông tin về phòng và giá của bạn.',
        imgSrc: 'https://ucarecdn.com/55424b49-277a-4452-9d7e-cdc7ae4ae7d1/-/crop/354:181/-/quality/lightest/-/format/webp/' // Replace with actual image path
    };
    return (
        <FramePage ownerPage={true} bannerData={bannerData}>
            <div className="rooms-and-rate-manager">
                <div className="rooms-and-rate-manager__header">
                    <h1 className="page-header" style={{ fontSize: 24, fontWeight: 500 }}>
                        Quản lý phòng và giá
                    </h1>
                    {loadingPriceCustom === 'loading' ? (
                        renderSkeletonTable()
                    ) : accomPriceCustom && accomPriceCustom.length > 0 ? (
                        <RoomsAndRate accomPriceCustom={accomPriceCustom} />
                    ) : (
                        renderNoData()
                    )}
                </div>
            </div>
        </FramePage>
    );
}
