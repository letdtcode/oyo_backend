import { useEffect, useState } from 'react';
import dayjs from 'dayjs';
import moment from 'moment';
import { useSnackbar } from 'notistack';
import { useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';
import Grid from '@mui/material/Grid';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import pricesOfHomeApi from '~/services/apis/partnerAPI/pricesOfHomeApi';
import formatPrice from '../../../utils/formatPrice';
import './CalendarSetting.scss';
import SelectedIdRoom from './SelectedIdRoom';

export default function CalendarSetting() {
    const [date, setDate] = useState(dayjs(moment().format('YYYY-MM-DD')));
    const [dateFormat, setDateFormat] = useState(
        `${moment().format('DD')} tháng ${moment().format('MM')} năm ${moment().format('YYYY')}`
    );
    const [idRoom, setIdRoom] = useState('');

    const [month, setMonth] = useState(moment().format('MM'));
    const [day, setDay] = useState(moment().format('DD'));
    const [year, setYear] = useState(moment().format('YYYY'));

    const [dataListhome, setDataListHome] = useState([]);
    const [listPriceDay, setListPriceDay] = useState([]);
    const [priceDay, setPriceDay] = useState('');

    const { enqueueSnackbar } = useSnackbar();

    const { register, reset, handleSubmit } = useForm();

    const onSubmit = (data) => {
        const newDataPrice = {
            price: parseInt(data.price),
            homeId: idRoom,
            dateStart: `${year}-${month}-${day}`,
            dateEnd: `${year}-${month}-${day}`
        };
        pricesOfHomeApi
            .customePriceDay(newDataPrice)
            .then((dataResponse) => {
                enqueueSnackbar('Cập nhật giá tiền thành công', { variant: 'success' });
                reset();
            })
            .catch((error) => {
                enqueueSnackbar(error.response?.data.message, { variant: 'error' });
            });
    };

    const handleChangeIdRoom = (value) => {
        setIdRoom(value);
    };

    useEffect(() => {
        partnerManageAccomAPI.getListHomeOfPartner().then((dataResponse) => {
            setDataListHome(dataResponse.data.content);
        });
        if (idRoom !== '') {
            pricesOfHomeApi.getPriceByMonthOfRoom(month, idRoom, year).then((data) => {
                setListPriceDay(data.data.prices);
            });
        }
    }, [idRoom, month, year]);

    const handleChangeMonth = (newValue) => {
        setDate(newValue);
        setDateFormat(`${newValue.$D} tháng ${parseInt(newValue.$M) + 1} năm ${newValue.$y}`);
        setMonth(parseInt(newValue.$M) < 10 ? `0${parseInt(newValue.$M) + 1}` : `${parseInt(newValue.$M) + 1}`);
        setDay(parseInt(newValue.$D) < 10 ? `0${newValue.$D}` : `${newValue.$D}`);
        setYear(`${newValue.$y}`);
        setPriceDay(listPriceDay[parseInt(newValue.$D) - 1]?.price);
    };

    return (
        <div className="calendar">
            <div className="idRoom-selected">
                <SelectedIdRoom
                    handleChangeIdRoom={handleChangeIdRoom}
                    dataListhome={dataListhome}
                    idFirst={dataListhome[0]?.id}
                />
            </div>
            <div className="row" style={{ margin: 0 }}>
                <div className="col l-6 c-6" style={{ paddingRight: '30px' }}>
                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                        <Grid container spacing={3}>
                            <Grid item xs={12} md={6}>
                                {/* <CalendarPicker date={date} onChange={handleChangeMonth} /> */}
                            </Grid>
                        </Grid>
                    </LocalizationProvider>
                </div>
                <div className="col l-6 c-6">
                    <div className="card-price__calendar">
                        <h2 className="title-date">{`${dateFormat}`}</h2>
                        <p className="price-show">
                            {idRoom !== '' ? `Định giá theo đêm: ${formatPrice(priceDay)}` : 'Vui lòng chọn nhà'}
                        </p>
                        <form onSubmit={handleSubmit(onSubmit)}>
                            <input type="number" className="input-price" {...register('price')} />
                            {idRoom !== '' ? (
                                <button type="submit" className="btn-save__price">
                                    Lưu
                                </button>
                            ) : (
                                <button className="btn-save__price-notallow">Lưu</button>
                            )}
                        </form>
                        <div className="img-price">
                            <img
                                src="https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/pricehome.png"
                                alt=""
                            />
                        </div>
                        <p className="policy__edit-price">
                            Bạn luôn có quyền kiểm soát mục cho thuê, giá cả và tình trạng phòng trống.
                            <Link to="" className="link-plus">
                                Tìm hiểu thêm.
                            </Link>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
}
