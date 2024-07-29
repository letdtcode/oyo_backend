import { useState } from 'react';
import Popover from '@mui/material/Popover';
import formatPrice from '~/utils/formatPrice';
import InfoIcon from '@mui/icons-material/Info';
import { t } from 'i18next';
import './PopoverPrice.scss';

export default function PopoverPrice({
    priceCustomForAccomList,
    discount,
    pricePerNightOrigin,
    pricePerNightCurrent,
    dayGapBooking
}) {
    const [anchorEl, setAnchorEl] = useState(null);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const open = Boolean(anchorEl);
    const id = open ? 'simple-popover' : undefined;


    return (
        <div className="popover-price">
            <InfoIcon color="primary" sx={{ fontSize: 'medium' }} onClick={handleClick} />
            <Popover
                id={id}
                open={open}
                anchorEl={anchorEl}
                onClose={handleClose}
                anchorOrigin={{
                    vertical: 'top',
                    horizontal: 'left'
                }}
                transformOrigin={{
                    vertical: 'bottom',
                    horizontal: 'right'
                }}
            >
                <h2 className="popover-price__title" style={{}}>
                    {t('title.priceDetail')}
                </h2>
                {priceCustomForAccomList?.length > 0 && (
                    <div className="item-price">
                        <p className="item-price__title">Giá linh hoạt</p>
                        {priceCustomForAccomList.map((item, index) => (
                            <div
                                className="item-price__content"
                                style={{ display: 'flex', justifyContent: 'space-between' }}
                                key={index}
                            >
                                <span>{`Ngày ${item.dateApply}`}</span>
                                <span>{formatPrice(item.priceApply)}</span>
                            </div>
                        ))}
                    </div>
                )}

                {dayGapBooking > 0 && (
                    <div className="item-price">
                        <p className="item-price__title">
                            {priceCustomForAccomList.length > 0 ? `Những ngày còn lại` : `Chi tiết giá`}
                        </p>

                        <div className="item-price__content">
                            {discount > 0 && (
                                <div className="price-before-discount">
                                    <span className="discount-percent">-{discount * 100}%</span>
                                    <span className="price-origin">
                                        {formatPrice(pricePerNightOrigin * dayGapBooking)}
                                    </span>
                                </div>
                            )}

                            <div className="price-after-discount">
                                <span className="title-price">{`Giá homestay x ${dayGapBooking} đêm`}</span>
                                <span className="price-current">
                                    {formatPrice(pricePerNightCurrent * dayGapBooking)}
                                </span>
                            </div>
                        </div>
                    </div>
                )}

                {/* <br /> */}
            </Popover>
        </div>
    );
}
