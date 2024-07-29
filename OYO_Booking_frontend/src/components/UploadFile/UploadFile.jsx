import { useRef } from 'react';
import './UploadFile.scss';
import { ImageConfig } from '~/config/ImageConfig';
import uploadImg from '~/assets/upload/cloud-upload-regular-240.png';
import { t } from 'i18next';
import { getFileExtension } from '~/utils/extensionFile';

const UploadFile = ({ dataStep4, onFileChange, videoIntro, setVideoIntro }) => {
    let fileList = null;
    if (videoIntro) {
        fileList = [...dataStep4, videoIntro];
    } else {
        fileList = [...dataStep4];
    }

    const wrapperRef = useRef(null);

    const onDragEnter = () => wrapperRef.current?.classList.add('dragover');

    const onDragLeave = () => wrapperRef.current?.classList.remove('dragover');

    const onDrop = () => wrapperRef.current?.classList.remove('dragover');

    const onFileDrop = (event) => {
        if (event.target.files) {
            if (event.target.files.length <= 1) {
                const newFile = event.target.files[0];
                if (newFile) {
                    const updatedList = [...dataStep4, newFile];
                    onFileChange(updatedList);
                }
            } else {
                let updatedList = [];
                for (let i = 0; i < event.target.files.length; i++) {
                    const newFile = event.target.files[i];
                    updatedList.push(newFile);
                }
                onFileChange(updatedList);
            }
        }
    };

    const fileRemove = (file) => {
        if (getFileExtension(file.name) === 'mp4') {
            setVideoIntro(null);
        } else {
            const updatedList = [...dataStep4];
            updatedList.splice(dataStep4.indexOf(file), 1);
            onFileChange(updatedList);
        }
    };

    return (
        <>
            <div
                ref={wrapperRef}
                className="drop-file-input"
                onDragEnter={onDragEnter}
                onDragLeave={onDragLeave}
                onDrop={onDrop}
            >
                <div className="drop-file-input__label">
                    <img src={uploadImg} alt="" />
                    <p>{t('setupOwner.upload')}</p>
                    <p className="desc-input-file">{t('setupOwner.maxUpload')}</p>
                </div>
                <input type="file" value="" onChange={onFileDrop} multiple />
            </div>
            {fileList.length > 0 ? (
                <div className="drop-file-preview">
                    <p className="drop-file-preview__title">Ready to upload</p>
                    <div className="list-preview">
                        {fileList.map((item, index) => (
                            <div key={index} className="drop-file-preview__item">
                                <img src={ImageConfig[`${getFileExtension(item.name)}`]} alt="" />
                                <div className="drop-file-preview__item__info">
                                    <p>{item.name}</p>
                                    <p className="size-file">{`${item.size} bytes`}</p>
                                </div>
                                <span className="drop-file-preview__item__del" onClick={() => fileRemove(item)}>
                                    x
                                </span>
                            </div>
                        ))}
                    </div>
                </div>
            ) : null}
        </>
    );
};

export default UploadFile;
