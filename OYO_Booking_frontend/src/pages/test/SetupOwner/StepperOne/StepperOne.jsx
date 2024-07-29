import { ChangeEvent } from 'react';
import SelectAddress from '~/components/SelectAddress/SelectAddress';
import './StepperOne.scss';
import { t } from 'i18next';
import ConfirmClose from '~/components/ConfirmClose/ConfirmClose';
import location_marker from '~/assets/imageMaster/location_marker.png';

const StepperOne = ({ data, setData, handleSetAddressDetail, setGuide }) => {
    const handleChangeAddrees = (event) => {
        if (handleSetAddressDetail) {
            handleSetAddressDetail(event.currentTarget?.value);
        }
    };

    const handleChangeGuide = (e) => {
        setGuide(e.currentTarget?.value);
    };

    return (
        <div className="step-one">
            <div className="row">
                <div className="col l-6 m-6 c-6">
                    <div className="require-step1">
                        <img src={location_marker} height={400} alt="" className="image-step1" />
                        <h1>{t('setupOwner.content_step_one')}</h1>
                    </div>
                </div>
                <div className="col l-6 m-6 c-6">
                    <div className="box-address">
                        <label>{t('label.address')}</label>
                        <SelectAddress setData={setData} data={data} />
                        <p className="span-address-step1">{t('contentMess.address')}</p>
                        <input
                            name="input-address-step1"
                            type="text"
                            className="input-address-step1"
                            onChange={handleChangeAddrees}
                            required
                        />
                        <p style={{ marginTop: 30, fontSize: 13, fontStyle: 'italic' }} className="span-address-step1">
                            {t('contentMess.guide')}
                        </p>
                        <textarea
                            name="input-guide-step1"
                            type="text"
                            className="input-guide-step1"
                            onChange={handleChangeGuide}
                        />
                        <ConfirmClose />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default StepperOne;
