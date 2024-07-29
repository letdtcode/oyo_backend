import { t } from 'i18next';
import ConfirmClose from '~/components/ConfirmClose/ConfirmClose';
import UploadFile from '~/components/UploadFile/UploadFile';
import './StepperFour.scss';

const StepperFour = ({ dataStep4, setDataStep4, videoIntro, setVideoIntro }) => {
    const onFileChange = (files) => {
        if (setDataStep4) {
            setDataStep4(files);
        }
    };
    const onVideoDrop = (e) => {
        if (e.target.files) {
            const newFileVideo = e.target.files[0];
            if (newFileVideo) {
                setVideoIntro(newFileVideo);
            }
        }
    };

    return (
        <div className="step-four">
            <div className="row">
                <div className="col l-6 m-6">
                    <div className="require-step4">
                        <h1>{t('setupOwner.content_step_four')}</h1>
                        <img
                            src="https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Gif_Pro/3625504_Mesa-de-trabajo-1.png"
                            alt=""
                            className="image-step4"
                        />
                    </div>
                </div>
                <div className="col l-6 m-6">
                    <div className="upload-file">
                        <UploadFile
                            dataStep4={dataStep4}
                            onFileChange={(files) => onFileChange(files)}
                            videoIntro={videoIntro}
                            setVideoIntro={setVideoIntro}
                        />
                    </div>

                    <div className="step-four__upload-video">
                        <label htmlFor="step-four__input-upload-video">
                            <div className="step-four__inner">
                                <div className="step-four__img-initial">
                                    <img src="https://cdn-icons-png.flaticon.com/512/3772/3772348.png" alt="ảnh" />
                                    <h3>Video Upload</h3>
                                </div>
                            </div>

                            <input
                                type="file"
                                id="step-four__input-upload-video"
                                value=""
                                onChange={onVideoDrop}
                                accept="video/mp4,video/x-m4v,video/*"
                                hidden
                            />
                        </label>
                    </div>

                    <ConfirmClose />
                </div>
            </div>
        </div>
    );
};

export default StepperFour;
