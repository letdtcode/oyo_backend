import './Gallery.scss';
import { t } from 'i18next';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import { useState, useEffect } from 'react';
import Checkbox from '~/components/Checkbox/Checkbox';
import FileUpload from '../../ManagerRoom/GallerySetting/VideoIntroSetting/FileUpload/FileUpload';
import VideoIntroDetail from '../../ManagerRoom/GallerySetting/VideoIntroSetting/VideoIntroDetail/VideoIntroDetail';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import uploadMedia from '~/services/apis/media/uploadMedia';

export default function Gallery({ id, save, doneSave }) {
    const [images, setImages] = useState([]);
    const [selectedImage, setSelectedImage] = useState([]);
    const [loading, setLoading] = useState(false);
    const [fileVideo, setFileVideo] = useState({
        cldVideoId: null,
        isUploading: false,
        name: null
    });

    useEffect(() => {
        if (id) {
            partnerManageAccomAPI.getGallery(id).then((res) => {
                setImages(res.data.imageAccomUrls);
                setFileVideo({ cldVideoId: res.data.cldVideoId, isUploading: false, name: res.data.cldVideoId });
                setLoading(false);
            });
        }
    }, []);

    useEffect(() => {
        const fetchData = async () => {
            if (save) {
                const uploadImages = await Promise.all(
                    images.map(async (img) => {
                        if (typeof img !== 'string') {
                            const imageUrl = (await uploadMedia.singleFile(img)).data.imageUrl;
                            return imageUrl;
                        } else {
                            return img;
                        }
                    })
                );

                const dataUpdate = {
                    imageAccomUrls: uploadImages,
                    cldVideoId: fileVideo?.cldVideoId
                };

                partnerManageAccomAPI
                    .updateGallery({ id, data: dataUpdate })
                    .then((res) => {
                        doneSave(true);
                    })
                    .catch(() => {
                        doneSave(false);
                    });
            }
        };

        fetchData().then(); // Gọi hàm bất đồng bộ từ useEffect
    }, [save]);

    const handleImageChange = (event) => {
        const files = event.target.files;
        const fileArray = Array.from(files);
        setImages([...images, ...fileArray]);
    };

    const handleRemove = () => {
        setImages(images.filter((_, index) => !selectedImage.includes(index)));
        setSelectedImage([]);
    };

    const removeFileVideo = () => {
        setFileVideo(null);
    };

    const onSubmit = () => {
        const newData = {
            data: {
                cldVideoId: file?.cldVideoId
            },
            id: params.idHome
        };
        partnerManageAccomAPI
            .updateGallery(newData)
            .then((res) => {
                enqueueSnackbar('Cập nhật thành công', { variant: 'success' });
                // dispatch(settingAccomSlice.actions.setAccom(res.data));
            })
            .catch((error) => {
                enqueueSnackbar(error.response?.data.message, { variant: 'error' });
            });
    };
    return (
        <div className="gallery__content">
            <div className="gallery__content__title">
                <h3>Ảnh khách sạn</h3>
                {selectedImage.length > 0 && (
                    <button className="remove-images" onClick={handleRemove}>
                        Xóa
                    </button>
                )}

                <Checkbox
                    id={'all'}
                    checked={selectedImage.length === images.length && images.length !== 0}
                    onChange={(e) => {
                        if (e.target.checked) {
                            setSelectedImage(images.map((_, index) => index));
                        } else {
                            setSelectedImage([]);
                        }
                    }}
                />
            </div>

            <div className="images-upload__container paper">
                <div className="row">
                    {images.map((image, index) => (
                        <div className="col l-2 m-3 c-4" key={index}>
                            <div className="image__container">
                                {typeof image === 'string' ? (
                                    <img src={image} alt={t('alt.imageHotel')} />
                                ) : (
                                    <img src={URL.createObjectURL(image)} alt={t('alt.imageHotel')} />
                                )}

                                <div className="image__checkbox">
                                    <Checkbox
                                        id={index}
                                        checked={selectedImage.includes(index)}
                                        onChange={(e) => {
                                            if (e.target.checked) {
                                                setSelectedImage([...selectedImage, index]);
                                            } else {
                                                setSelectedImage(selectedImage.filter((item) => item !== index));
                                            }
                                        }}
                                    />
                                </div>
                            </div>
                        </div>
                    ))}
                    <div className="col l-2 m-3 c-4">
                        <div
                            className="button-add-image__container"
                            onClick={() => document.getElementById('imageUpload').click()}
                        >
                            <AddCircleOutlineIcon />
                            <div>{t('common.addImage')}</div>
                        </div>
                    </div>
                    <input hidden type="file" id="imageUpload" accept="image/*" multiple onChange={handleImageChange} />
                </div>
            </div>
            <div className="gallery__content__title">
                <h3>Video</h3>
            </div>
            <div className="video-upload__container paper">
                <div className="video-intro-setting__container">
                    <FileUpload file={fileVideo} setFile={setFileVideo} />
                    <VideoIntroDetail file={fileVideo} removeFile={removeFileVideo} />
                </div>
            </div>
        </div>
    );
}
