import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Fade from '@mui/material/Fade';
import Typography from '@mui/material/Typography';
import './ModalConfirm.scss';
import { t } from 'i18next';
import { useState } from 'react';

export default function ModalConfirm({ title, content, setOpen, setConfirm }) {
    const [open1, setOpen1] = useState(true);
    const handleClose = () => setOpen(false);
    const handleYes = () => {
        setConfirm(true);
        handleClose();
    };

    return (
        <div>
            <Modal
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                open={open1}
                onClose={handleClose}
                closeAfterTransition
            >
                <Fade in={open1}>
                    <Box className="modal-confirm">
                        <div className="modal-confirm__title">
                            {title}
                        </div>
                        <div className="modal-confirm__content">
                            {content}
                        </div>
                        <div className="modal-confirm__buttons">
                            <button onClick={handleClose} className="modal-confirm__buttons__button button-no">
                                {t('common.no')}
                            </button>
                            <button onClick={handleYes} className="modal-confirm__buttons__button button-yes">
                                {t('common.yes')}
                            </button>
                        </div>
                    </Box>
                </Fade>
            </Modal>
        </div>
    );
}
