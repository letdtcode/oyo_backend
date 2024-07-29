import './GeneralInfo.scss';
import { t } from 'i18next';
import React, { useEffect, useState } from 'react';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import publicSurchargeAPI from '~/services/apis/publicAPI/publicSurchargeAPI';
import { generalDefault } from '~/models/accom';
import { convertPrice, convertPriceToNumber } from '~/utils/convertPrice';

export default function GeneralInfo({ id, save, doneSave }) {
    const [loading, setLoading] = useState(true);
    const [data, setData] = useState({});
    const [allSurcharge, setAllSurchagre] = useState([]);

    useEffect(() => {
        publicSurchargeAPI.getAllSurcharge().then((res) => {
            setAllSurchagre(res.data);
        });
        if (id) {
            partnerManageAccomAPI.getGeneralInfo(id).then((res) => {
                setData(res.data);
                setLoading(false);
            });
        }
    }, [id]);

    useEffect(() => {
        if (save) {
            const newData = { ...data };

            Object.keys(newData).forEach((key) => {
                if (newData[key] === null) {
                    newData[key] = generalDefault[key];
                }
            });
            partnerManageAccomAPI
                .updateGeneralInfo({ id, data: newData })
                .then((res) => {
                    doneSave(true);
                })
                .catch(() => {
                    doneSave(false);
                });
        }
    }, [save]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name === 'pricePerNight') {
            setData({
                ...data,
                [name]: convertPriceToNumber(value)
            });
            return;
        }
        if (name === 'discountPercent') {
            if (parseInt(value) > 100) {
                setData({
                    ...data,
                    [name]: 1.0
                });
                return;
            }
            if (parseInt(value) < 0) {
                setData({
                    ...data,
                    [name]: 0
                });
                return;
            }
        }
        setData({
            ...data,
            [name]: value
        });
    };
    if (loading) return <></>;
    return (
        <div className="general-info">
            <div className="info__content row">
                <div className="col l-6 m-6 c-12">
                    <label className="info__title">{t('label.nameHome')}</label>
                    <input
                        type="text"
                        name="accomName"
                        placeholder={t('placeholder.nameHome')}
                        className="info__input"
                        defaultValue={data?.accomName}
                        onChange={handleChange}
                    />
                    <label className="info__title">{t('label.acreageHome')}</label>
                    <input
                        name="acreage"
                        type="number"
                        placeholder={t('placeholder.acreageM2')}
                        className="info__input"
                        defaultValue={data?.acreage}
                        onChange={handleChange}
                    />
                </div>

                <div className="col l-6 m-6 c-12">
                    <label className="info__title">{t('label.descHome')}</label>
                    <textarea
                        name="description"
                        className="info__textarea"
                        defaultValue={data?.description}
                        onChange={handleChange}
                    />
                </div>

                <div className="col l-6 m-6 c-12">
                    <label className="info__title">{t('label.checkInFrom')}</label>
                    <input
                        name="checkInFrom"
                        type="time"
                        className="info__input"
                        defaultValue={data?.checkInFrom || '14:00'}
                        onChange={handleChange}
                    />

                    <label className="info__title">{t('label.priceHome')}</label>
                    <input
                        name="pricePerNight"
                        type="text"
                        placeholder={t('placeholder.priceVND')}
                        className="info__input"
                        value={convertPrice(data?.pricePerNight || 0)}
                        onChange={handleChange}
                    />
                </div>
                <div className="col l-6 m-6 c-12">
                    <label className="info__title">{t('label.checkOutTo')}</label>
                    <input
                        name="checkOutTo"
                        type="time"
                        className="info__input"
                        defaultValue={data?.checkOutTo || '12:00'}
                        onChange={handleChange}
                    />
                    <label className="info__title">{t('label.discountPercent')} (%)</label>
                    <input
                        name="discountPercent"
                        type="number"
                        placeholder={t('placeholder.discountPercent')}
                        className="info__input"
                        onChange={handleChange}
                        value={data?.discountPercent}
                        label={t('label.discountPercent')}
                    />
                </div>
                <div className="col l-12 m-12 c-12">
                    <label className="info__title">{t('label.surchargeList')}</label>
                    <ul className="surcharge-list">
                        {allSurcharge.map((surcharge, index) => (
                            <div key={index}>
                                <label className="info__title">{surcharge.surchargeCateName}</label>
                                <input
                                    type="text"
                                    className="info__input"
                                    name={surcharge.surchargeCateName}
                                    defaultValue={
                                        data.surchargeList[
                                            data.surchargeList.findIndex(
                                                (item) => item.surchargeCode === surcharge.surchargeCode
                                            )
                                        ]?.cost
                                    }
                                    onChange={(e) =>
                                        setData({
                                            ...data,
                                            surchargeList: [
                                                ...data.surchargeList.filter(
                                                    (item) => item.surchargeCode !== surcharge.surchargeCode
                                                ),
                                                {
                                                    surchargeCode: surcharge.surchargeCode,
                                                    cost: parseInt(e.target.value)
                                                }
                                            ]
                                        })
                                    }
                                />
                            </div>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    );
}
