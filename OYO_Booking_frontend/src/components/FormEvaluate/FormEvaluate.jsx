import { useEffect, useState } from 'react';

import { t } from 'i18next';
import { useSnackbar } from 'notistack';
import AddIcon from '@mui/icons-material/Add';
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Rating } from '@mui/material';
import uploadMedia from '~/services/apis/media/uploadMedia';
import bookingAPI from '~/services/apis/clientAPI/clientBookingAPI';
import { useDispatch } from 'react-redux';
import globalSlice from '~/redux/globalSlice';
import './FormEvaluate.scss';

const FormEvaluate = (props) => {
    const [open, setOpen] = useState(false);
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [valueReview, setValueReview] = useState(5);
    const [selectedImages, setSelectedImages] = useState([]);
    const dispatch = useDispatch();
    const { enqueueSnackbar } = useSnackbar();

    useEffect(() => {
        if (props.showFormReview) {
            setOpen(true);
        }
        props.handleCloseReview();
    }, [props, props.showFormReview]);

    const handleClose = () => {
        setOpen(false);
    };

    const submitFormHandler = async (event) => {
        event.preventDefault();
        if (selectedImages.length > 0) {
            dispatch(globalSlice.actions.setLoading(true));
            uploadMedia
                .multipleFile(selectedImages)
                .then((res) => {
                    const imagesUrls = res.data.flatMap((img) => img.imageUrl);
                    const dataReview = {
                        title: title,
                        content: content,
                        rateStar: valueReview,
                        imagesUrls: imagesUrls,
                        bookingCode: props.bookingCode
                    };
                    bookingAPI.createReviewBooking(dataReview).then((res) => {
                        if (res.statusCode === 200) {
                            enqueueSnackbar(t('message.reviewSuccess'), {
                                variant: 'success'
                            });
                            setOpen(false);
                            props.handleReload();
                        }
                    });
                    dispatch(globalSlice.actions.setLoading(false));
                })
                .catch((err) => {
                    enqueueSnackbar(err, {
                        variant: 'error'
                    });
                    dispatch(globalSlice.actions.setLoading(false));
                });
        } else {
            const dataReview = {
                title: title,
                content: content,
                rateStar: valueReview,
                imagesUrls: [],
                bookingCode: props.bookingCode
            };
            bookingAPI.createReviewBooking(dataReview).then((res) => {
                setOpen(false);
                if (res.statusCode === 200) {
                    enqueueSnackbar(t('message.reviewSuccess'), {
                        variant: 'success'
                    });
                    props.handleReload();
                }
                dispatch(globalSlice.actions.setLoading(false));
            });
        }
    };

    const handleImageChange = (event) => {
        const files = event.target.files;
        const allowedImageTypes = ['image/jpeg', 'image/png', 'image/bmp', 'image/webp', 'image/jpg'];

        for (const file of files) {
            if (!allowedImageTypes.includes(file.type)) {
                alert(t('validate.invalidImageType'));
                return;
            }
        }
        const fileArray = Array.from(files);
        setSelectedImages([...selectedImages, ...fileArray]);
    };
    return (
        <Dialog open={open} onClose={handleClose} maxWidth="xl">
            <div className="paper diaglog-feedback">
                <DialogTitle className="title">{t('title.review')}</DialogTitle>
                <form onSubmit={submitFormHandler} className="form__feedback">
                    <DialogContent>
                        <DialogContentText className="thanksReview">{t('label.thanksReview')}</DialogContentText>
                        <DialogContentText className="fillReview">{t('label.fillReview')}</DialogContentText>

                        <div className="selectedRate">
                            <p>{t('label.selectedRate')}</p>
                            <Rating
                                name="simple-controlled"
                                value={valueReview}
                                onChange={(event, newValue) => {
                                    setValueReview(newValue);
                                }}
                            />
                        </div>
                        <input
                            className="input__title"
                            name="title"
                            type="text"
                            placeholder={'Tiêu đề'}
                            onChange={(e) => setTitle(e.target.value)}
                            required
                        />
                        <input
                            className="input__feedback"
                            name="conten"
                            type="text"
                            placeholder={t('placeholder.feedback')}
                            onChange={(e) => setContent(e.target.value)}
                            required
                        />

                        <input
                            hidden
                            type="file"
                            id="imageUpload"
                            accept="image/jpeg, image/jpg, image/png, image/bmp, image/webp"
                            multiple
                            onChange={handleImageChange}
                        />
                        <button
                            type="button"
                            className="btn-upload"
                            onClick={() => document.getElementById('imageUpload').click()}
                        >
                            <AddIcon />
                            {t('common.addImage')}
                        </button>

                        {selectedImages.length > 0 && (
                            <div className="container__preview">
                                {selectedImages.map((image, index) => (
                                    <div key={index}>
                                        <img
                                            className="img__preview"
                                            src={URL.createObjectURL(image)}
                                            alt={`Selected ${index + 1}`}
                                        />
                                    </div>
                                ))}
                            </div>
                        )}
                    </DialogContent>
                    <DialogActions>
                        <button type="button" onClick={handleClose} className="btn-review-cancel">
                            {t('common.cancel')}
                        </button>
                        <button type="submit" className="btn-review-detail">
                            {t('common.review')}
                        </button>
                    </DialogActions>
                </form>
            </div>
        </Dialog>
    );
};

export default FormEvaluate;
