import { ChangeEvent, useState } from 'react';
import { t } from 'i18next';

import ConfirmClose from '~/components/ConfirmClose/ConfirmClose';
import './StepperFive.scss';

const StepperFive = (props) => {
    const [nameRoom, setNameRoom] = useState('')
    const [descRoom, setDescRoom] = useState('')
    const [priceRoom, setPriceRoom] = useState('')
    const [acreage, setAreage] = useState('')
    const handleChangeNameRoom = (event) => {
        setNameRoom(event.currentTarget?.value);
        if (props.handleSetDataStep5) {
            props.handleSetDataStep5({
                name: event.currentTarget?.value,
                description: descRoom,
                costPerNightDefault: priceRoom,
                acreage: acreage,
            });
        }
    };

    const handleChangeDescRoom = (event) => {
        setDescRoom(event.currentTarget?.value);
        if (props.handleSetDataStep5) {
            props.handleSetDataStep5({
                name: nameRoom,
                description: event.currentTarget?.value,
                costPerNightDefault: priceRoom,
                acreage: acreage,
            });
        }
    };

    const handleChangePriceRoom = (event) => {
        if (event.currentTarget?.value < 0) {
            return;
        }
        setPriceRoom(event.currentTarget?.value);
        if (props.handleSetDataStep5) {
            props.handleSetDataStep5({
                name: nameRoom,
                description: descRoom,
                costPerNightDefault: event.currentTarget?.value,
                acreage: acreage,
            });
        }
    };

    const handleChangeAcreage = (event) =>{
        if (event.currentTarget?.value < 0) {
            return;
        }
        setAreage(event.currentTarget?.value);
        if (props.handleSetDataStep5) {
            props.handleSetDataStep5({
                name: nameRoom,
                description: descRoom,
                costPerNightDefault: priceRoom,
                acreage:event.currentTarget?.value
            });
        }
    }
    return (
        <div className="step-five">
            <div className="row">
                <div className="col l-6 m-6">
                    <img
                        src="https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Gif_Pro/nha-dep_tu-van-xay-nha-600-trieu-tien-ich-5.png"
                        alt=""
                        className="image-step5"
                    />
                    <h1>{t('setupOwner.content_step_five')}</h1>
                </div>
                <div className="col l-6 m-6">
                    <form>
                        <p className="title-desc-step5">{t('label.nameHome')}</p>
                        <input
                            type="text"
                            placeholder={t('placeholder.nameHome')}
                            className="input-step5"
                            onChange={handleChangeNameRoom}
                        />

                        <p className="title-desc-step5">{t('label.descHome')}</p>
                        <textarea className="text-step5" onChange={handleChangeDescRoom} />

                        <p className="title-desc-step5">{t('label.priceHome')}</p>
                        <input
                            type="number"
                            placeholder={t('placeholder.priceVND')}
                            className="input-step5"
                            onChange={handleChangePriceRoom}
                        />
                        <p className="title-desc-step5">{t('label.acreageHome')}</p>
                        <input
                            type="number"
                            placeholder={t('placeholder.acreageM2')}
                            className="input-step5"
                            onChange={handleChangeAcreage}
                        />
                    </form>
                    <ConfirmClose />
                </div>
            </div>
        </div>
    );
};

export default StepperFive;
