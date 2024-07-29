import { useEffect, useState } from 'react';
import { t } from 'i18next';
import { Link, useNavigate } from 'react-router-dom';
import SkeletonProvince from '../Skeleton/SkeletonProvince';
import publicAccomPlaceAPI from '~/services/apis/publicAPI/publicAccomPlaceAPI';
import { transLateProvince } from '~/services/thirdPartyAPI/translateAPI';
import { useDispatch } from 'react-redux';
import filterAcomSlice from '~/redux/filterAccom';
import './Popular.scss';

const Popular = () => {
    const dispatch = useDispatch();
    const [listProvince, setListProvince] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        setLoading(true);
        publicAccomPlaceAPI
            .getTopHomeOfProvince()
            .then(async (res) => {
                const data = await Promise.all(
                    res.data.content.flatMap((item) => {
                        return transLateProvince(item);
                    })
                );
                setListProvince(data);
                setLoading(false);
            })
            .catch((err) => {});
    }, []);

    const handleLinkToProvince = (province) => {
        dispatch(filterAcomSlice.actions.reset());
        dispatch(filterAcomSlice.actions.province(province.provinceCode));
        navigate(`list-accom`);
    };

    return (
        <div className="web-content">
            <div className="package-menu">
                <div className="package-menu__head">
                    <p>{t('title.exploreVN')}</p>
                </div>
                <div className="row">
                    {loading ? (
                        <SkeletonProvince />
                    ) : (
                        listProvince?.map((province, index) => {
                            return (
                                <div className="col l-3 m-6 c-12" key={index}>
                                    <div className="package">
                                        <div className="package-overlay">
                                            <img src={province?.thumbnail} alt="" className="package-thumbnail" />
                                            <div className="package-info">
                                                <h3 className="package-heading">{province?.provinceName}</h3>
                                                <span className="package-desc">
                                                    {`${province?.numBooking} ${t('numberCount.countBooking')}`}
                                                </span>
                                            </div>
                                        </div>

                                        <Link to="#" className="mobile-package__link"></Link>
                                        <div className="package-cover hide-on-mobile-tablet">
                                            <div className="package-btn">
                                                <p
                                                    className="package-btn-link"
                                                    onClick={() => handleLinkToProvince(province)}
                                                >
                                                    {t('link.viewDetail')}
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            );
                        })
                    )}
                </div>
            </div>
        </div>
    );
};

export default Popular;
