import { useState, useEffect } from 'react';
import format from 'date-fns/format';
import { useSnackbar } from 'notistack';
import { useForm } from 'react-hook-form';
import { useParams } from 'react-router-dom';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import pricesOfHomeApi from '~/services/apis/partnerAPI/pricesOfHomeApi';
import formatPrice from '../../../utils/formatPrice';
import './ValuationDiscount.scss';

export default function ValuationDiscountSetting(props) {
    const [expanded, setExpanded] = useState(false);
    const [numberLength, setNumberLength] = useState(0);
    const [dateDiscountWeek, setDateDiscountWeek] = useState(null);
    const [dateDiscountMonth, setDateDiscountMonth] = useState(null);

    const { handleSubmit, register, setValue } = useForm();

    const params = useParams();

    const { enqueueSnackbar } = useSnackbar();

    useEffect(() => {
        if (props.detailPriceRoom.discounts) {
            setNumberLength(props.detailPriceRoom.discounts.length + 2);
        }
    }, [props.detailPriceRoom]);

    useEffect(() => {
        setValue('pricePerNight', props?.detailPriceRoom.pricePerNight);
        for (var i = 0; i < props?.detailPriceRoom.discounts.length; i++) {
            if (props?.detailPriceRoom.discounts[i].config !== null) {
                setValue(`percent${i}`, props?.detailPriceRoom.discounts[i].config.percent);
            }
        }
        for (var j = 0; j < props?.detailPriceRoom.surchargeList.length; j++) {
            if (props?.detailPriceRoom.surchargeList[j].cost !== null) {
                setValue(`cost${j}`, props?.detailPriceRoom.surchargeList[j].cost);
            }
        }
    }, [
        props?.detailPriceRoom.pricePerNight,
        setValue,
        props?.detailPriceRoom.discounts,
        props?.detailPriceRoom.surchargeList
    ]);

    const handleChange = (panel) => (event, isExpanded) => {
        setExpanded(isExpanded ? panel : false);
    };

    const handleClose = () => {
        setExpanded(false);
    };

    const onSubmit = (dataPrice) => {
        const newData = {
            pricePerNight: parseFloat(dataPrice.pricePerNight),
            id: params.idHome
        };
        partnerManageAccomAPI
            .updatePriceHome(newData)
            .then((dataResponse) => {
                enqueueSnackbar('Cập nhật thành công', { variant: 'success' });
            })
            .catch((error) => {
                enqueueSnackbar(error.response?.data.message, { variant: 'error' });
            });
    };

    const onSubmitDiscount = (dataDiscount) => {
        const newData = {
            listDiscountOfHomeDetail: [
                {
                    percent: dataDiscount.percent0 ? parseFloat(dataDiscount.percent0) : null,
                    homeId: params.idHome,
                    categoryId: dataDiscount.idCategory_0,
                    dateStart: dateDiscountMonth ? format(dateDiscountMonth[0].startDate, 'yyyy-MM-dd') : null,
                    dateEnd: dateDiscountMonth ? format(dateDiscountMonth[0].endDate, 'yyyy-MM-dd') : null
                },
                {
                    percent: dataDiscount.percent1 ? parseFloat(dataDiscount.percent1) : null,
                    homeId: params.idHome,
                    categoryId: dataDiscount.idCategory_1,
                    dateStart: dateDiscountWeek ? format(dateDiscountWeek[0].startDate, 'yyyy-MM-dd') : null,
                    dateEnd: dateDiscountWeek ? format(dateDiscountWeek[0].endDate, 'yyyy-MM-dd') : null
                }
            ]
        };
        pricesOfHomeApi
            .setDiscountOfHome(newData)
            .then((dataResponse) => {
                enqueueSnackbar('Cập nhật thành công', { variant: 'success' });
            })
            .catch((error) => {
                enqueueSnackbar(error.response?.data.message, { variant: 'error' });
            });
    };

    const onSubmitSurcharge = (dataSurcharge) => {
        const data = transformSurchargeData(dataSurcharge);
        partnerManageAccomAPI.setSurcharge({ data: data, id: params.idHome });
        // .then((dataResponse) => {
        //     enqueueSnackbar('Cập nhật thành công', { variant: 'success' });
        // })
        // .catch((error) => {
        //     enqueueSnackbar(error.response?.data.message, { variant: 'error' });
        // });
    };

    return (
        <div style={{ fontSize: '15px', paddingRight: '50px', paddingBottom: '50px' }} className="ValuationDiscount">
            <h3>Định giá và phụ phí</h3>
            <form onSubmit={handleSubmit(onSubmit)}>
                <h4>Định giá</h4>
                <Accordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls="panel1bh-content"
                        id="panel1bh-header"
                    >
                        <p style={{ width: '33%', flexShrink: 0 }}>Giá tiền theo đêm</p>
                        <p style={{ color: 'text.secondary' }}>{formatPrice(props?.detailPriceRoom.pricePerNight)}</p>
                    </AccordionSummary>
                    <AccordionDetails>
                        <div className="content-input">
                            <h4>Giá theo đêm</h4>
                            <p>Bạn chịu trách nhiệm chọn giá cho thuê nhà/phòng của mình.</p>
                            <input className="input-price-room__setting" {...register('pricePerNight')} />
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

            <h4>Giảm giá</h4>

            {props?.detailPriceRoom.discounts?.map((discount, index) => {
                var i = 2 + index;
                return (
                    <form onSubmit={handleSubmit(onSubmitDiscount)} key={index}>
                        <Accordion expanded={expanded === `panel${i}`} onChange={handleChange(`panel${i}`)}>
                            <AccordionSummary
                                expandIcon={<ExpandMoreIcon />}
                                aria-controls="panel1bh-content"
                                id="panel1bh-header"
                            >
                                <p style={{ width: '33%', flexShrink: 0 }}>{discount?.category.name}</p>
                                <p style={{ color: 'text.secondary' }}>
                                    {discount?.config !== null &&
                                    discount?.config.percent &&
                                    discount?.config.percent !== ''
                                        ? discount?.config.percent
                                        : 'Chưa thiết lập'}
                                </p>
                            </AccordionSummary>
                            <AccordionDetails>
                                <div className="content-input-discount">
                                    <h4>Mô tả {discount?.category.name}</h4>
                                    <p>
                                        {discount?.category.description
                                            ? discount?.category.description
                                            : 'Không có mô tả'}
                                    </p>
                                    {/* {index === 0 ? (
                                        <DateDiscount
                                            setDataDay={setDateDiscountMonth}
                                            dateStart={discount?.config?.dateStart}
                                            dateEnd={discount?.config?.dateEnd}
                                        />
                                    ) : (
                                        <DateDiscount
                                            setDataDay={setDateDiscountWeek}
                                            dateStart={discount?.config?.dateStart}
                                            dateEnd={discount?.config?.dateEnd}
                                        />
                                    )} */}

                                    <input className="input-price-room__setting" {...register(`percent${index}`)} />
                                    <input
                                        {...register(`idCategory_${index}`)}
                                        type="hidden"
                                        defaultValue={discount?.category.id}
                                    />
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
                );
            })}

            <h4>Phụ phí</h4>
            {props?.detailPriceRoom.surchargeList?.map((surchargeList, index) => {
                var j = numberLength + index;
                return (
                    <form onSubmit={handleSubmit(onSubmitSurcharge)} key={index}>
                        <Accordion expanded={expanded === `panel${j}`} onChange={handleChange(`panel${j}`)} key={j}>
                            <AccordionSummary
                                expandIcon={<ExpandMoreIcon />}
                                aria-controls="panel1bh-content"
                                id="panel1bh-header"
                            >
                                <p style={{ width: '33%', flexShrink: 0 }}>{surchargeList?.surchargeName}</p>
                                <p style={{ color: 'text.secondary' }}>
                                    {surchargeList?.cost ? surchargeList?.cost : 'Chưa thiết lập'}
                                </p>
                            </AccordionSummary>
                            <AccordionDetails>
                                <div className="content-input">
                                    <h4>{surchargeList?.surchargeName}</h4>
                                    {/* <p>{surchargeList?.description ? surchargeList?.description : 'Không có mô tả'}</p> */}
                                    <input className="input-price-room__setting" {...register(`cost${index}`)} />
                                    <input
                                        {...register(`surchargeName_${index}`)}
                                        type="hidden"
                                        defaultValue={surchargeList?.surchargeName}
                                    />
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
                );
            })}
        </div>
    );
}
function transformSurchargeData(data) {
    const surchargeArray = [];

    for (let i = 0; data[`cost${i}`] !== undefined; i++) {
        const surchargeName = data[`surchargeName_${i}`];
        const cost = data[`cost${i}`];

        if (surchargeName !== undefined && cost !== undefined) {
            surchargeArray.push({ surchargeName: surchargeName, cost: cost });
        }
    }

    return surchargeArray;
}
