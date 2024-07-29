import { useState, useEffect } from 'react';
import './PaymentInfoSetting.scss';
import { listBankModel } from '~/models/bank';
import CustomInput from '~/assets/custom/CustomInput';
import MenuItem from '@mui/material/MenuItem';
import { useTranslation } from 'react-i18next';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { useSnackbar } from 'notistack';

const PaymentInfoSetting = ({ accomId }) => {
    const { t } = useTranslation();
    const [listBank, setListBank] = useState(listBankModel);
    const [data, setData] = useState({});
    const [expanded, setExpanded] = useState(false);
    const { enqueueSnackbar } = useSnackbar();

    useEffect(() => {
        if (accomId) {
            partnerManageAccomAPI.getPaymentInfo(accomId).then((res) => {
                setData(res.data);
            });
        }
    }, [accomId]);

    const onChangeData = (event) => {
        setData({ ...data, [event.target.name]: event.target.value });
    };

    const handleChange = (panel) => (event, isExpanded) => {
        setExpanded(isExpanded ? panel : false);
    };

    const handleClose = () => {
        setExpanded(false);
    };

    const handleSave = (e) => {
        e.preventDefault();

        partnerManageAccomAPI
            .updatePayment({ id: accomId, data })
            .then((res) => {
                enqueueSnackbar('Cập nhật thành công', { variant: 'success' });
            })
            .catch(() => {
                enqueueSnackbar('Cập nhật thất bại', { variant: 'error' });
            });
    };

    return (
        <div className="payment-info-setting-container">
            <h3 className="payment-info-setting-container__title">Thanh toán</h3>
            <form onSubmit={handleSave} className="payment-info-setting-container__content">
                <Accordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls="panel1bh-content"
                        id="panel1bh-header"
                        className="accordion-summary"
                    >
                        <label className="accordion-summary__title">Thông tin thanh toán</label>
                    </AccordionSummary>
                    <AccordionDetails>
                        <div className="accordion-details row">
                            <CustomInput
                                name="bankId"
                                select={true}
                                size="small"
                                title={t('title.bank')}
                                className="custom-input col l-6 m-12 c-12"
                                value={data.bankId || ''}
                                onChange={onChangeData}
                                content={listBank.map((bank, index) => (
                                    <MenuItem key={index} value={bank.id}>
                                        {bank.name_bank}
                                    </MenuItem>
                                ))}
                            />
                            <CustomInput
                                size="small"
                                title={t('title.accountNumber')}
                                className="custom-input col l-6 m-12 c-12"
                                name="accountNumber"
                                value={data.accountNumber || ''}
                                onChange={onChangeData}
                            />
                            <CustomInput
                                size="small"
                                title={t('title.accountNameHost')}
                                className="custom-input col l-6 m-12 c-12"
                                name="accountNameHost"
                                value={data.accountNameHost || ''}
                                onChange={onChangeData}
                            />
                            <CustomInput
                                size="small"
                                title={t('title.swiftCode')}
                                className="custom-input col l-6 m-12 c-12"
                                name="swiftCode"
                                value={data.swiftCode || ''}
                                onChange={onChangeData}
                            />
                        </div>

                        <div className="accordion-details__section accordion-details__btn">
                            <p onClick={handleClose} className="accordion-details__btn-close">
                                Hủy
                            </p>
                            <button className="accordion-details__btn-save">Lưu</button>
                        </div>
                    </AccordionDetails>
                </Accordion>
            </form>
        </div>
    );
};

export default PaymentInfoSetting;
