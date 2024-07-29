import './DrawerHome.scss';
import Drawer from '@mui/material/Drawer';
import CloseOutlinedIcon from '@mui/icons-material/CloseOutlined';
import IconButton from '@mui/material/IconButton';
import { useDispatch } from 'react-redux';
import globalSlice from '~/redux/globalSlice';
import { Cloudinary } from '@cloudinary/url-gen';
import { AdvancedVideo } from '@cloudinary/react';
import { scale } from '@cloudinary/url-gen/actions/resize';
import { useState } from 'react';

export default function DrawerHome({ anchor, setOpen, data, open, stars, dataComment }) {
    const [selectedImageType, setSelectedImageType] = useState('accommodation');
    const handleOnClose = () => {
        setOpen(false);
    };

    const toggleDrawer = (anchor, open) => (event) => {
        if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
            return;
        }
        setOpen(false);
    };

    let images = [];
    for (let i = 0; i < data.imageAccomsUrls.length; i++) {
        let size = 'small';
        if (i % 3 === 0 && data.imageAccomsUrls.length - i >= 3) {
            size = 'large';
        } else if (data.imageAccomsUrls.length - i === 1 && i % 3 === 0) {
            size = 'large';
        }
        images[i] = {
            url: data.imageAccomsUrls[i],
            size: size
        };
    }

    const dispatch = useDispatch();

    const imageClick = (img) => {
        dispatch(globalSlice.actions.setViewImg([img]));
    };
    let imagesComment = [];
    dataComment.map((item) => {
        for (let i = 0; i < item.imageReviewUrls.length; i++) {
            let size = 'small';
            if (i % 3 === 0 && item.imageReviewUrls.length - i >= 3) {
                size = 'large';
            } else if (item.imageReviewUrls.length - i === 1 && i % 3 === 0) {
                size = 'large';
            }
            imagesComment.push({
                url: item.imageReviewUrls[i],
                size: size
            });
        }
    });
    const handleImageTypeChange = (type) => {
        setSelectedImageType(type);
    };
    const crop = new Cloudinary({
        cloud: {
            cloudName: 'dyv5zrsgj'
        }
    })
        .video(data.cldVideoId)
        .resize(scale().width(810).height(500).aspectRatio('16:9'))
        .quality('auto')
        .format('auto');
    return (
        <Drawer anchor="right" open={open} onClose={toggleDrawer(anchor, false)}>
            <div className="drawer__home">
                <div className="drawer__home--header">
                    <div className="head-left">
                        <div className="accomName">{data.accomName}</div>
                        <div className="star-rating__home">{stars}</div>
                    </div>
                    <div className="head-right">
                        <IconButton onClick={handleOnClose}>
                            <CloseOutlinedIcon />
                        </IconButton>
                    </div>
                </div>
                <hr className="divider-full" />
                <div className="drawer__home--body">
                    <div className="type-image__container">
                        <div
                            className={`type-image ${selectedImageType === 'accommodation' ? 'selected' : ''}`}
                            onClick={() => handleImageTypeChange('accommodation')}
                        >
                            <img src={images[0].url} alt="room_hot" className="image-home" />
                            <div className="name-images">Ảnh từ khách sạn</div>
                        </div>

                        {imagesComment.length > 0 && (
                            <div
                                className={`type-image ${selectedImageType === 'userReview' ? 'selected' : ''}`}
                                onClick={() => handleImageTypeChange('userReview')}
                            >
                                <img src={imagesComment[0].url} alt="room_hot" className="image-home" />
                                <div className="name-images">Ảnh người dùng đánh giá</div>
                            </div>
                        )}
                    </div>

                    <hr className="divider-full" />
                    <div className="drawer__home-images">
                        <div className="image__container">
                            {selectedImageType === 'accommodation' && (
                                <>
                                    {data.cldVideoId && (
                                        <div className={`image__container__video`} key={0}>
                                            <AdvancedVideo autoPlay muted controls loop cldVid={crop} />
                                        </div>
                                    )}
                                    {images.map((image, index) => (
                                        <div className={`image__item ${image.size}`} key={index}>
                                            <img
                                                src={image.url}
                                                alt="room_hot"
                                                className="image-home"
                                                onClick={() => imageClick(image.url)}
                                            />
                                        </div>
                                    ))}
                                </>
                            )}
                            {selectedImageType === 'userReview' &&
                                imagesComment.map((image, index) => (
                                    <div className={`image__item ${image.size}`} key={index}>
                                        <img
                                            src={image.url}
                                            alt="room_hot"
                                            className="image-home"
                                            onClick={() => imageClick(image.url)}
                                        />
                                    </div>
                                ))}
                        </div>
                    </div>
                </div>
            </div>
        </Drawer>
    );
}
