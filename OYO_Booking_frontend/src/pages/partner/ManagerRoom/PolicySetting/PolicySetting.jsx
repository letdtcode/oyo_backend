import { useState, useEffect } from 'react';
import './PolicySetting.scss';
import { cancelBookingModel, policyPublicModel } from '~/models/cancelBooking';
import Checkbox from '@mui/material/Checkbox';
import FormControlLabel from '@mui/material/FormControlLabel';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import CustomInput from '~/assets/custom/CustomInput';
import MenuItem from '@mui/material/MenuItem';
import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { useTranslation } from 'react-i18next';
import { useSnackbar } from 'notistack';

const PolicySetting = ({ accomId }) => {
    const { t } = useTranslation();
    const [policyPublic, setPolicyPublic] = useState(policyPublicModel);
    const [expanded, setExpanded] = useState(false);
    const { enqueueSnackbar } = useSnackbar();
    const handleChange = (panel) => (event, isExpanded) => {
        setExpanded(isExpanded ? panel : false);
    };

    const handleClose = () => {
        setExpanded(false);
    };

    useEffect(() => {
        if (accomId) {
            partnerManageAccomAPI.getPolicy(accomId).then((res) => {
                setPolicyPublic({
                    allowSmoking: res.data.generalPolicy.allowSmoking || false,
                    allowEvent: res.data.generalPolicy.allowEvent || false,
                    allowPet: res.data.generalPolicy.allowPet || false,
                    cancellationPolicy: res.data.cancellationPolicy.code || cancelBookingModel.cancelLation[0].value,
                    cancellationFeeRate:
                        res.data.cancellationPolicy.cancellationFeeRate || cancelBookingModel.cancellationFeeRate[0]
                });
            });
        }
    }, [accomId]);

    const handleOnChange = (event) => {
        setPolicyPublic({ ...policyPublic, [event.target.name]: event.target.value });
    };

    const handleSave = (e) => {
        e.preventDefault();

        partnerManageAccomAPI
            .updatePolicy({ id: accomId, data: policyPublic })
            .then((res) => {
                enqueueSnackbar('Cập nhật thành công', { variant: 'success' });
            })
            .catch(() => {
                enqueueSnackbar('Cập nhật thất bại', { variant: 'error' });
            });
    };

    return (
        <div className="policy-setting-container">
            <h3 className="policy-setting-container__title">Chính sách</h3>
            <form onSubmit={handleSave} className="policy-setting-container__content">
                <Accordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls="panel1bh-content"
                        id="panel1bh-header"
                        className="accordion-summary"
                    >
                        <label className="accordion-summary__title">{t('label.refundPolicy')}</label>
                    </AccordionSummary>
                    <AccordionDetails className="accordion-details">
                        <div className="accordion-details__item row">
                            <CustomInput
                                id="cancellationPolicy"
                                className="custom-input col l-6 m-12 c-12"
                                name="cancellationPolicy"
                                size="small"
                                title={t('label.timeCancel')}
                                select={true}
                                value={policyPublic.cancellationPolicy || cancelBookingModel.cancelLation[0].value}
                                content={cancelBookingModel.cancelLation.map((item) => (
                                    <MenuItem key={item.value} value={item.value}>
                                        {item.label}
                                    </MenuItem>
                                ))}
                                onChange={handleOnChange}
                            />
                            <CustomInput
                                id="cancellationFeeRate"
                                className="custom-input col l-6 m-12 c-12"
                                name="cancellationFeeRate"
                                size="small"
                                select={true}
                                title={t('label.feeCancel')}
                                value={policyPublic.cancellationFeeRate || cancelBookingModel.cancellationFeeRate[0]}
                                content={cancelBookingModel.cancellationFeeRate.map((item) => (
                                    <MenuItem key={item} value={item}>
                                        {item}%
                                    </MenuItem>
                                ))}
                                onChange={handleOnChange}
                            />
                        </div>
                        <div className="accordion-details__item">
                            <FormControlLabel
                                control={<Checkbox sx={{ '& .MuiSvgIcon-root': { fontSize: 28 } }} />}
                                label={'Được phép hút thuốc'}
                                sx={{ '.MuiTypography-root': { fontSize: 17 } }}
                                checked={policyPublic?.allowSmoking}
                                onChange={(e) => setPolicyPublic({ ...policyPublic, allowSmoking: e.target.checked })}
                                className="accordion-details__checkbox"
                            />
                        </div>
                        <div className="accordion-details__item">
                            <FormControlLabel
                                control={<Checkbox sx={{ '& .MuiSvgIcon-root': { fontSize: 28 } }} />}
                                label={'Được phép tổ chức tiệc'}
                                sx={{ '.MuiTypography-root': { fontSize: 17 } }}
                                checked={policyPublic?.allowEvent}
                                onChange={(e) => setPolicyPublic({ ...policyPublic, allowEvent: e.target.checked })}
                                className="accordion-details__checkbox"
                            />
                        </div>
                        <div className="accordion-details__item">
                            <FormControlLabel
                                control={<Checkbox sx={{ '& .MuiSvgIcon-root': { fontSize: 28 } }} />}
                                label={'Được phép mang thú cưng'}
                                sx={{ '.MuiTypography-root': { fontSize: 17 } }}
                                checked={policyPublic?.allowPet}
                                onChange={(e) => setPolicyPublic({ ...policyPublic, allowPet: e.target.checked })}
                                className="accordion-details__checkbox"
                            />
                        </div>
                        <div className="accordion-details__section accordion-details__btn">
                            <p onClick={handleClose} className="accordion-details__btn-close">
                                Hủy
                            </p>
                            <button type="submit" className="accordion-details__btn-save">
                                Lưu
                            </button>
                        </div>
                    </AccordionDetails>
                </Accordion>
            </form>
        </div>
    );
};

export default PolicySetting;
