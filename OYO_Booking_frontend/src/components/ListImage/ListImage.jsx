import './ListImage.scss';
import ImageOutlinedIcon from '@mui/icons-material/ImageOutlined';

import { Cloudinary } from '@cloudinary/url-gen';
import { AdvancedVideo } from '@cloudinary/react';
import { scale } from '@cloudinary/url-gen/actions/resize';

const ListImage = ({ listImage, setOpen, cldVideoId }) => {
    const imgMain = listImage.slice(0, 1);
    const cutDataImage = listImage.slice(1, 5);
    const remainingIamge = listImage.length;

    const cld = new Cloudinary({
        cloud: {
            cloudName: 'dyv5zrsgj'
        }
    });

    const crop =
        cldVideoId !== null
            ? cld
                  .video(cldVideoId)
                  .resize(scale().width(810).height(500).aspectRatio('16:9'))
                  .quality('auto')
                  .format('auto')
            : null;

    return (
        <div className="list-image">
            {listImage ? (
                <div className="row">
                    <div className="col l-6 c-12">
                        <div className="image-item-thumbnail">
                            {cldVideoId ? (
                                <AdvancedVideo autoPlay controls loop muted cldVid={crop} />
                            ) : (
                                <img src={imgMain} alt="room_hot" onClick={() => props.setOpen(true)} />
                            )}
                        </div>
                    </div>
                    <div className="col l-6 c-12">
                        <div className="row">
                            {cutDataImage.map((imgs, index) => (
                                <div className="col l-6 c-6" key={index}>
                                    <div className="image-item">
                                        <img
                                            src={imgs}
                                            alt="room_hot"
                                            onClick={() => setOpen(true)}
                                            className={`img__${index}`}
                                        />
                                        {remainingIamge > 4 && index === 3 && (
                                            <div className="overlay" onClick={() => setOpen(true)}>
                                                <div className="overlay-text">
                                                    +{remainingIamge - 4} <ImageOutlinedIcon />
                                                </div>
                                            </div>
                                        )}
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            ) : (
                <></>
            )}
        </div>
    );
};

export default ListImage;
