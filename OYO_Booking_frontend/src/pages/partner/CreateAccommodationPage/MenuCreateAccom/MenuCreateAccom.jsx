import './MenuCreateAccom.scss';
import { Link } from 'react-router-dom';
import { styled } from '@mui/material/styles';
import LinearProgress, { linearProgressClasses } from '@mui/material/LinearProgress';
import { Button } from '@mui/material';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import { useSnackbar } from 'notistack';
import { useState } from 'react';
import tickIcon from '~/assets/img/tick-icon.jpg';
import { center } from '@cloudinary/url-gen/qualifiers/textAlignment';
import { WidthFull } from '@mui/icons-material';
import { bold } from '@cloudinary/url-gen/qualifiers/fontWeight';

export default function MenuCreateAccom({ process, selectedTab, idAccom }) {
    const { enqueueSnackbar } = useSnackbar();
    const [isRequestApproval, setIsRequestApproval] = useState(false);
    const handlePost = () => {
        partnerManageAccomAPI
            .requestApprovalAccomPlace(idAccom)
            .then((res) => {
                setIsRequestApproval(true);
                enqueueSnackbar('Lưu thành công', { variant: 'success' });
            })
            .catch(() => {
                enqueueSnackbar('Lưu thất bại', { variant: 'error' });
            });
    };

    return (
        <div className="col l-2 m-3 c-4">
            <div className="menu-create-accom__container">
                <div className="menu-create-accom__content">
                    <div className="options-card">
                        <Link
                            to={`/host/createHotel/generalInfo/${idAccom}`}
                            className={`paper option ${selectedTab === 'generalInfo' ? 'selected-option' : ''}`}
                        >
                            Thông tin chung
                        </Link>
                        <Link
                            to={`/host/createHotel/address/${idAccom}`}
                            className={`paper option ${selectedTab === 'address' ? 'selected-option' : ''}`}
                        >
                            Địa chỉ chỗ nghỉ
                        </Link>
                        <Link
                            to={`/host/createHotel/amenities/${idAccom}`}
                            className={`paper option ${selectedTab === 'amenities' ? 'selected-option' : ''}`}
                        >
                            Tiện ích
                        </Link>
                        <Link
                            to={`/host/createHotel/gallery/${idAccom}`}
                            className={`paper option ${selectedTab === 'gallery' ? 'selected-option' : ''}`}
                        >
                            Hình ảnh & Video
                        </Link>
                        <Link
                            to={`/host/createHotel/roomSetting/${idAccom}`}
                            className={`paper option ${selectedTab === 'roomSetting' ? 'selected-option' : ''}`}
                        >
                            Thiết lập phòng
                        </Link>
                        <Link
                            to={`/host/createHotel/policy/${idAccom}`}
                            className={`paper option ${selectedTab === 'policy' ? 'selected-option' : ''}`}
                        >
                            Chính sách
                        </Link>
                        <Link
                            to={`/host/createHotel/payment/${idAccom}`}
                            className={`paper option ${selectedTab === 'payment' ? 'selected-option' : ''}`}
                        >
                            Thông tin thanh toán
                        </Link>
                        <div className="progress__underway paper">
                            {isRequestApproval === false && (
                                <>
                                    <div>Tiến trình đã thực hiện được</div>

                                    <div className="progress">
                                        <BorderLinearProgress
                                            variant="determinate"
                                            value={process}
                                        ></BorderLinearProgress>
                                        <div className="percent">{process}%</div>
                                    </div>
                                </>
                            )}
                            {process < 100 ? null : isRequestApproval === false ? (
                                <Button variant="contained" className=" option" fullWidth onClick={handlePost}>
                                    Đăng chỗ nghỉ
                                </Button>
                            ) : (
                                <div
                                    style={{
                                        display: 'flex',
                                        justifyContent: 'center',
                                        alignItems: 'center',
                                        fontWeight: 500,
                                        fontSize: 14
                                    }}
                                >
                                    <img src={tickIcon} style={{ width: 40, height: 40 }} alt="tick icon"></img>
                                    <span>Đã gửi yêu cầu kiểm duyệt</span>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

const BorderLinearProgress = styled(LinearProgress)(({ theme }) => ({
    height: 25,
    borderRadius: 5,
    [`&.${linearProgressClasses.colorPrimary}`]: {
        backgroundColor: theme.palette.grey[theme.palette.mode === 'light' ? 200 : 800]
    },
    [`& .${linearProgressClasses.bar}`]: {
        borderRadius: 5,
        backgroundColor: theme.palette.mode === 'light' ? '#1a90ff' : '#308fe8'
    }
}));
