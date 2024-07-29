import './Policy.scss';
import { t } from 'i18next';
import { useState, useEffect } from 'react';
import Checkbox from '@mui/material/Checkbox';
import FormControlLabel from '@mui/material/FormControlLabel';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import { cancelBookingModel, policyPublicModel } from '~/models/cancelBooking';
import CustomInput from '~/assets/custom/CustomInput';
import MenuItem from '@mui/material/MenuItem';

export default function Policy({ id, save, doneSave }) {
    const [loading, setLoading] = useState(false);
    const [policyPublic, setPolicyPublic] = useState(policyPublicModel);

    useEffect(() => {
        if (id) {
            partnerManageAccomAPI.getPolicy(id).then((res) => {
                setPolicyPublic({
                    allowSmoking: res.data.generalPolicy.allowSmoking || false,
                    allowEvent: res.data.generalPolicy.allowEvent || false,
                    allowPet: res.data.generalPolicy.allowPet || false,
                    cancellationPolicy: res.data.cancellationPolicy.code || cancelBookingModel.cancelLation[0].value,
                    cancellationFeeRate:
                        res.data.cancellationPolicy.cancellationFeeRate || cancelBookingModel.cancellationFeeRate[0]
                });
                setLoading(false);
            });
        }
    }, []);
    const handleOnchange = (event) => {
        setPolicyPublic({ ...policyPublic, [event.target.name]: event.target.value });
    };
    useEffect(() => {
        if (save) {
            partnerManageAccomAPI
                .updatePolicy({ id, data: policyPublic })
                .then((res) => {
                    doneSave(true);
                })
                .catch(() => {
                    doneSave(false);
                });
        }
    }, [save]);
    return (
        <div className="policy">
            <div className="policy__content">
                <label className="title-">{t('label.refundPolicy')}</label>
                <div className="policy__content__item row">
                    <CustomInput
                        id="cancellationPolicy"
                        className="input col l-6 m-12 c-12"
                        name="cancellationPolicy"
                        size="small"
                        title={t('label.timeCancel')}
                        select={true}
                        value={policyPublic.cancellationPolicy || cancelBookingModel.cancelLation[0].value}
                        content={cancelBookingModel.cancelLation.map((item) => {
                            return (
                                <MenuItem key={item.value} value={item.value}>
                                    {item.label}
                                </MenuItem>
                            );
                        })}
                        onChange={handleOnchange}
                    />
                    <CustomInput
                        id="cancellationFeeRate"
                        className="input col l-6 m-12 c-12"
                        name="cancellationFeeRate"
                        size="small"
                        select={true}
                        title={t('label.feeCancel')}
                        value={policyPublic.cancellationFeeRate || cancelBookingModel.cancellationFeeRate[0]}
                        content={cancelBookingModel.cancellationFeeRate.map((item) => {
                            return (
                                <MenuItem key={item} value={item}>
                                    {item}%
                                </MenuItem>
                            );
                        })}
                        onChange={handleOnchange}
                    />
                </div>

                <label className="title-">{t('label.policyPublic')}</label>
                <div className="policy__content__item">
                    <FormControlLabel
                        control={<Checkbox sx={{ '& .MuiSvgIcon-root': { fontSize: 28 } }} />}
                        label={'Được phép hút thuốc'}
                        sx={{ '.MuiTypography-root': { fontSize: 17 } }}
                        checked={policyPublic?.allowSmoking}
                        onChange={(e) => setPolicyPublic({ ...policyPublic, allowSmoking: e.target.checked })}
                    />
                </div>
                <div className="policy__content__item">
                    <FormControlLabel
                        control={<Checkbox sx={{ '& .MuiSvgIcon-root': { fontSize: 28 } }} />}
                        label={'Được phép tổ chức tiệc'}
                        sx={{ '.MuiTypography-root': { fontSize: 17 } }}
                        checked={policyPublic?.allowEvent}
                        onChange={(e) => setPolicyPublic({ ...policyPublic, allowEvent: e.target.checked })}
                    />
                </div>
                <div className="policy__content__item">
                    <FormControlLabel
                        control={<Checkbox sx={{ '& .MuiSvgIcon-root': { fontSize: 28 } }} />}
                        label={'Được phép mang thú cưng'}
                        sx={{ '.MuiTypography-root': { fontSize: 17 } }}
                        checked={policyPublic?.allowPet}
                        onChange={(e) => setPolicyPublic({ ...policyPublic, allowPet: e.target.checked })}
                    />
                </div>
            </div>
        </div>
    );
}
