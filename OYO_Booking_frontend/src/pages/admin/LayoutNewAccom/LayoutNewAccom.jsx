import './LayoutNewAccom.scss';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import cmsAccomPlaceAPI from '~/services/apis/adminAPI/cmsAccomPlaceAPI';
import TableEmpty from '~/assets/img/empty.png';

export default function LayoutNewAccom() {
    const navigate = useNavigate();
    const [listNewAccom, setListNewAccom] = useState([]);
    const [loading, setLoading] = useState(true);

    const handleItemClick = (id) => {
        navigate(`/admin/new-accom/${id}`);
    };

    useEffect(() => {
        cmsAccomPlaceAPI.getAllAcommPlaceWatting().then((res) => {
            setListNewAccom(res.data.content);
            setLoading(false);
        });
    }, []);

    return (
        <div className="new-accom__page">
            <div className="header__customer">
                <h2 className="page-header">Danh sách chỗ nghỉ mới</h2>
                <div className="new-accom__container paper">
                    <div className="new-accom__list">
                        {loading ? (
                            <></>
                        ) : (
                            <>
                                {listNewAccom.length > 0 ? (
                                    listNewAccom.map((item, index) => {
                                        return (
                                            <div
                                                className="new-accom__item"
                                                key={index}
                                                onClick={() => handleItemClick(item.accomId)}
                                            >
                                                <div className="new-accom__img">
                                                    <img src={item.logo} alt={item.accomName} />
                                                </div>
                                                <div className="new-accom__content">
                                                    <h3 className="new-accom__name">{item.accomName}</h3>
                                                    <p className="new-accom__address">{item.addressDetail}</p>
                                                    <p className="new-accom__price">{item.progress}%</p>
                                                </div>
                                            </div>
                                        );
                                    })
                                ) : (
                                    <div className="table-empty">
                                        <img src={TableEmpty} alt="icon-empty" style={{ marginTop: 3 }} />
                                        <span className="table-empty__title">Danh sách rỗng</span>
                                        <span className="table-empty__content" style={{ marginTop: 3 }}>
                                            Hiện chưa có homestay nào chờ duyệt
                                        </span>
                                    </div>
                                )}
                            </>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}
