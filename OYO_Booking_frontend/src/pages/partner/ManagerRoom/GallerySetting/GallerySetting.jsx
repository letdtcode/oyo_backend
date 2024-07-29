import { useState, useEffect } from 'react';
import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import CircularProgress from '@mui/material/CircularProgress';
import './GallerySetting.scss';
import ImageSetting from './ImageSetting/ImageSetting';
import VideoIntroSetting from './VideoIntroSetting/VideoIntroSetting';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';

const GallerySetting = ({ accomId }) => {
    const [expanded, setExpanded] = useState(false);
    const [galleryAccom, setGalleryAccom] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        setIsLoading(true);
        partnerManageAccomAPI
            .getGallery(accomId)
            .then((dataResponse) => {
                setGalleryAccom(dataResponse.data);
            })
            .finally(() => {
                setIsLoading(false);
            });
    }, [accomId]);

    const handleChange = (panel) => (event, isExpanded) => {
        setExpanded(isExpanded ? panel : false);
    };

    const handleClose = () => {
        setExpanded(false);
    };

    const handleSaveGallery = (e) => {
        e.preventDefault();
        // Implement your save logic here
    };

    return (
        <div className="gallery-setting-container">
            <h3 className="gallery-setting-container__title">Hình ảnh & Video</h3>
            {isLoading ? (
                <div className="gallery-setting-container__loading">
                    <CircularProgress />
                </div>
            ) : (
                <form onSubmit={handleSaveGallery} className="gallery-setting-container__content">
                    <Accordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
                        <AccordionSummary
                            expandIcon={<ExpandMoreIcon />}
                            aria-controls="panel1bh-content"
                            id="panel1bh-header"
                            className="accordion-summary"
                        >
                            <p className="accordion-summary__title">Hình ảnh & Video</p>
                        </AccordionSummary>
                        <AccordionDetails className="accordion-details">
                            <ImageSetting
                                listImage={galleryAccom.imageAccomUrls}
                                thumbnail={galleryAccom.imageAccomUrls[0]}
                            />
                            <VideoIntroSetting cldVideoId={galleryAccom.cldVideoId} />
                            {/* <div className="accordion-details__section accordion-details__btn">
                                <p onClick={handleClose} className="accordion-details__btn-close">
                                    Hủy
                                </p>
                                <button type="submit" className="accordion-details__btn-save">
                                    Lưu
                                </button>
                            </div> */}
                        </AccordionDetails>
                    </Accordion>
                </form>
            )}
        </div>
    );
};

export default GallerySetting;
