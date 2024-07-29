import { useEffect, useState } from 'react';
import publicFacilityAPI from '~/services/apis/publicAPI/publicFacilityAPI';
import SelectedMultiple from './SelectedMultiple';
import './StepperThree.scss';
import { t } from 'i18next';
import ConfirmClose from '~/components/ConfirmClose/ConfirmClose';

const StepperThree = (props) => {
    const [data, setData] = useState([]);

    useEffect(() => {
        publicFacilityAPI.getAllDataFacility().then((dataResponse) => {
            if (dataResponse?.data) {
                setData(dataResponse.data);
            }
        });
    }, []);

    return (
        <div className="step-three">
            <div className="row">
                <div className="col l-6 m-6">
                    <div className="require-step3">
                        {/* <img
                            src="https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Gif_Pro/Asset-5.png"
                            alt=""
                            className="image-step3"
                        /> */}
                        <h1>{t('setupOwner.content_step_three')}</h1>
                    </div>
                </div>
                <div className="col l-6 m-6">
                    <SelectedMultiple dataList={data} setDataStep3={props.setDataStep3} />
                    <ConfirmClose />
                </div>
            </div>
        </div>
    );
};

export default StepperThree;
