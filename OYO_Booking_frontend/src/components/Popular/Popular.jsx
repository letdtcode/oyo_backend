import { useEffect, useState } from 'react';
import { t } from 'i18next';
import { useNavigate } from 'react-router-dom';
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
                <div className="popular__container">
                    {loading ? (
                        // <SkeletonProvince />
                        <></>
                    ) : (
                        listProvince?.map((province, index) => {
                            return (
                                <div
                                    className={`popular__item item-${index}`}
                                    key={index}
                                    onClick={() => handleLinkToProvince(province)}
                                >
                                    <img src={province?.thumbnail} alt="" className={`package-thumbnail`} />
                                    <div className="package-info">
                                        <h3 className="package-heading">{province?.provinceName}</h3>
                                        <span className="package-desc">
                                            {`${province?.numBooking} ${t('numberCount.countBooking')}`}
                                        </span>
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
