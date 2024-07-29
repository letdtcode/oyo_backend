import './DashboardPartnerPage.scss';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useSelector } from 'react-redux';
import Button from '@mui/material/Button';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import SliderListAccomWaiting from './SliderListAccomWaiting/SliderListAccomWaiting';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import TableAccomApproved from './TableAccomApproved/TableAccomApproved';
import { useFetchAccomData } from '~/redux/managerAccomSlice';
import FramePage from '~/components/FramePage/FramePage';
import gif from '~/assets/imageMaster/homeStep3.gif';
export default function DashboardPartner() {
    useFetchAccomData();
    const { accomApproved, loadingApproved } = useSelector((state) => state.managerAccom);
    const [accomWaiting, setAccomWaiting] = useState([]);

    useEffect(() => {
        const fetchAccomWaiting = async () => {
            try {
                const response = await partnerManageAccomAPI.getListAccomWaiting();

                setAccomWaiting(response.data.content);
            } catch (error) {
            }
        };
        fetchAccomWaiting();
    }, []);
    const bannerData = {
        title: 'Trang tổng quan',
        subtitle: 'Chào mừng bạn đến với trang tổng quan của OYO.',
        imgSrc: gif
    };
    
    
    return (
        <FramePage ownerPage={true} bannerData={bannerData}>
            <div className="dashboard-partner">
                <header className="dashboard-partner__header">
                    <span className="dashboard-partner__header__title" style={{ fontSize: 24, fontWeight: 500 }}>
                        {accomWaiting.length > 0 && 'Chỗ nghỉ cần hoàn thiện/chờ duyệt'}
                    </span>
                    <Link to="createHotel">
                        <Button variant="contained">
                            <AddCircleOutlineIcon />
                            Tạo chỗ nghỉ mới
                        </Button>
                    </Link>
                </header>
                <div className="dashboard-partner__slider">
                    <SliderListAccomWaiting accomWaiting={accomWaiting} setAccomWaiting={setAccomWaiting}/>
                </div>
            </div>

            <div className="dashboard-partner__page">
                <>
                    <header className="dashboard-partner__header">
                        <div className="dashboard-partner__header__title">
                            {accomApproved.length > 0 && 'Chỗ nghỉ đã được duyệt/đang hoạt động'}
                        </div>
                    </header>
                    <div className="dashboard-partner__table">
                        <TableAccomApproved accomApproved={accomApproved} loading={loadingApproved} />
                    </div>
                </>
            </div>
        </FramePage>
    );
}
