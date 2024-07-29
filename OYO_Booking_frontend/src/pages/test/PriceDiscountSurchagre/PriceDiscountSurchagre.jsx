import { useSnackbar } from 'notistack';
import { useParams } from 'react-router-dom';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import publicSurchargeAPI from '~/services/apis/publicAPI/publicSurchargeAPI';
import './PriceDiscountSurchagre.scss';
import { useEffect, useState } from 'react';

export default function PriceDiscountSurchagre(props) {
    const [expanded, setExpanded] = useState(false);
    const params = useParams();
    const { enqueueSnackbar } = useSnackbar();

    const [allSurcharge, setAllSurchagre] = useState([]);
    const [discount, setDiscount] = useState(0);
    const [pricePerNight, setPricePerNight] = useState(0);
    const handleChange = (panel) => (event, isExpanded) => {
        setExpanded(isExpanded ? panel : false);
    };

    const handleClose = () => {
        setExpanded(false);
    };

    useEffect(() => {
        setDiscount(props.detailPriceRoom.discount);
        setPricePerNight(props.detailPriceRoom.pricePerNight);
        publicSurchargeAPI.getAllSurcharge().then((res) => {
            setAllSurchagre(
                res.data.map((surcharge) => {
                    const temp = props.detailPriceRoom.surchargeList.find(
                        (x) => x.surchargeCode === surcharge.surchargeCode
                    );
                    if (temp) {
                        return { ...surcharge, cost: temp.cost };
                    } else {
                        return { ...surcharge, cost: 0 };
                    }
                })
            );
        });
    }, [props]);

    const onChangeCharge = (e) => {
        setAllSurchagre([...allSurcharge], ([...allSurcharge][e.target.name].cost = e.target.value));
    };
    const handleSave = (e) => {
        e.preventDefault();
        partnerManageAccomAPI
            .setSurcharge({ id: params.idHome, data: { surchargeList: allSurcharge } })
            .then(
                partnerManageAccomAPI
                    .updatePriceHome({ id: params.idHome, data: pricePerNight })
                    .then(partnerManageAccomAPI.updateDiscount({ id: params.idHome, data: discount })),

                enqueueSnackbar('Cập nhật thành công', { variant: 'success' }),
                setExpanded(false)
            )
            .catch((error) => {
                enqueueSnackbar(error.response?.data.message, { variant: 'error' });
            });
    };
    return (
        <div style={{ fontSize: '15px', paddingRight: '50px', paddingBottom: '50px' }} className="ValuationDiscount">
            <h3>Định giá và phụ phí</h3>
            <form onSubmit={handleSave}>
                <Accordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls="panel1bh-content"
                        id="panel1bh-header"
                    >
                        <p style={{ width: '33%', flexShrink: 0 }}>Định giá</p>
                        <p style={{ color: 'text.secondary' }}>Giá tiền, giảm giá, phụ phí</p>
                    </AccordionSummary>
                    <AccordionDetails>
                        <div className="content-input">
                            <h4>Giá theo đêm</h4>
                            <p>Bạn chịu trách nhiệm chọn giá cho thuê nhà/phòng của mình.</p>
                            <input
                                className="input-price-room__setting"
                                value={pricePerNight || 0}
                                onChange={(e) => setPricePerNight(e.target.value)}
                            />
                        </div>

                        <div className="content-input-discount">
                            <h4>Giảm giá</h4>
                            <p>Giá giảm sẽ được áp dụng cho nhà của bạn</p>
                            <input
                                className="input-price-room__setting"
                                value={discount}
                                onChange={(e) => setDiscount(e.target.value)}
                            />
                        </div>

                        <div className="content-input-discount">
                            <h4>Các loại phụ phí</h4>
                            <p>Các loại phụ phí này sẽ được áp dụng cho nhà của bạn</p>

                            {allSurcharge?.map((charge, index) => (
                                <div key={index} className="content-input">
                                    <h4>{charge?.surchargeCateName}</h4>
                                    <input
                                        className="input-price-room__setting"
                                        name={index}
                                        value={charge.cost}
                                        onChange={(e) => onChangeCharge(e)}
                                    />
                                </div>
                            ))}
                        </div>

                        <div className="btn">
                            <p onClick={handleClose} className="btn-close">
                                Hủy
                            </p>
                            <button className="btn-save">Lưu</button>
                        </div>
                    </AccordionDetails>
                </Accordion>
            </form>
        </div>
    );
}
