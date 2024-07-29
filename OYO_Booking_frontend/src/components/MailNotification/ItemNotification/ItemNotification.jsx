import React from 'react';
import bookingSuccessImage from '~/assets/img/booking-success.png';
import './ItemNotification.scss';

const ItemNotification = ({ title, content, dateTime }) => {
    return (
        <div className="item-notification">
            <img className="item-notification__image" src={bookingSuccessImage} alt="Đặt phòng thành công"></img>
            <div className="item-notification__container">
                <span className="item-notification__title">{title}</span>
                <p className="item-notification__content">{content}</p>
                <span className="item-notification__date-time">{dateTime}</span>
            </div>
        </div>
    );
};

export default ItemNotification;
