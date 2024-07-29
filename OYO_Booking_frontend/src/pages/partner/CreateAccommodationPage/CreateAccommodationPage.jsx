import './CreateAccommodationPage.scss';
import { useState, useEffect } from 'react';
import { Route, Routes, useParams } from 'react-router-dom';
import CreateAccomCate from './CreateAccomCate/CreateAccomCate';
import MenuCreateAccom from './MenuCreateAccom/MenuCreateAccom';
import GeneralInfo from './GeneralInfo/GeneralInfo';
import AddressAccom from './AddressAccom/AddressAccom';
import Gallery from './Gallery/Gallery';
import Amenities from './Amenities/Amenities';
import RoomSetting from './RoomSetting/RoomSetting';
import Policy from './Policy/Policy';
import PaymentInfo from './PaymentInfo/PaymentInfo';
import { Button } from '@mui/material';
import { useSnackbar } from 'notistack';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import ArrowBackOutlinedIcon from '@mui/icons-material/ArrowBackOutlined';
import { IconButton } from '@mui/material';
import FramePage from '~/components/FramePage/FramePage';

export default function CreateAccommodationPage() {
    const params = useParams();
    const [idAccom, setIdAccom] = useState(null);
    const [onClickSave, setOnClickSave] = useState(false);
    const [selectedTab, setSelectedTab] = useState(0);
    const [process, setProcess] = useState(0);
    const { enqueueSnackbar } = useSnackbar();
    useEffect(() => {
        let id = params['*'].split('/')[1];
        if (id !== idAccom) {
            setIdAccom(params['*'].split('/')[1]);
        }
        if (params['*'].split('/')[0] !== selectedTab) {
            setSelectedTab(params['*'].split('/')[0]);
        }
        id !== undefined
            ? partnerManageAccomAPI.getPercentCreate(id).then((res) => {
                  setProcess(res.data.percent);
              })
            : null;
    }, [params['*']]);

    const handleSave = (complete) => {
        if (complete) {
            enqueueSnackbar('Lưu thành công', { variant: 'success' });
            partnerManageAccomAPI.getPercentCreate(idAccom).then((res) => {
                setProcess(res.data.percent);
            });
        } else {
            enqueueSnackbar('Lưu thất bại', { variant: 'error' });
        }
        setOnClickSave(false);
    };

    const handleBack = () => {
        window.history.back();
    }
    return (
        <FramePage ownerPage={true}>
            <div className="create-acoom__page">
                <Routes>
                    <Route path="/" element={<CreateAccomCate />} />
                    <Route
                        path="/*"
                        element={
                            <>
                                <header className="create-acoom__title">
                                    <button className='create-acoom__title__btn-back' onClick={handleBack}>
                                        <ArrowBackOutlinedIcon />
                                    </button>
                                    <h2>Tạo chỗ nghỉ mới</h2>
                                </header>
                                <div className=" row">
                                    <MenuCreateAccom process={process} selectedTab={selectedTab} idAccom={idAccom} />
                                    <div className="col l-10 m-9 c-8 screen__container">
                                        {idAccom !== null && (
                                            <div className="screen__container paper">
                                                <Routes>
                                                    <Route
                                                        path="generalInfo/*"
                                                        element={
                                                            <GeneralInfo
                                                                id={idAccom}
                                                                save={onClickSave}
                                                                doneSave={handleSave}
                                                            />
                                                        }
                                                    />
                                                    <Route
                                                        path="address/*"
                                                        element={
                                                            <AddressAccom
                                                                id={idAccom}
                                                                save={onClickSave}
                                                                doneSave={handleSave}
                                                            />
                                                        }
                                                    />
                                                    <Route
                                                        path="amenities/*"
                                                        element={
                                                            <Amenities
                                                                id={idAccom}
                                                                save={onClickSave}
                                                                doneSave={handleSave}
                                                            />
                                                        }
                                                    />
                                                    <Route
                                                        path="gallery/*"
                                                        element={
                                                            <Gallery
                                                                id={idAccom}
                                                                save={onClickSave}
                                                                doneSave={handleSave}
                                                            />
                                                        }
                                                    />
                                                    <Route
                                                        path="roomSetting/*"
                                                        element={
                                                            <RoomSetting
                                                                id={idAccom}
                                                                save={onClickSave}
                                                                doneSave={handleSave}
                                                            />
                                                        }
                                                    />
                                                    <Route
                                                        path="policy/*"
                                                        element={
                                                            <Policy
                                                                id={idAccom}
                                                                save={onClickSave}
                                                                doneSave={handleSave}
                                                            />
                                                        }
                                                    />
                                                    <Route
                                                        path="payment/*"
                                                        element={
                                                            <PaymentInfo
                                                                id={idAccom}
                                                                save={onClickSave}
                                                                doneSave={handleSave}
                                                            />
                                                        }
                                                    />
                                                </Routes>
                                            </div>
                                        )}
                                        <Button
                                            variant="contained"
                                            color="primary"
                                            className="btn-save-acoom"
                                            onClick={(e) => {
                                                setOnClickSave(true);
                                            }}
                                        >
                                            Lưu
                                        </Button>
                                    </div>
                                </div>
                            </>
                        }
                    />
                </Routes>
            </div>
            </FramePage>
    );
}
