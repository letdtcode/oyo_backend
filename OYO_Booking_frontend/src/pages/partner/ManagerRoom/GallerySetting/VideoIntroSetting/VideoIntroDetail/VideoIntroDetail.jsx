import './VideoIntroDetail.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFileAlt, faSpinner, faTrash } from '@fortawesome/free-solid-svg-icons';

const VideoIntroDetail = ({ file, removeFile }) => {
    const deleteFileHandler = () => {
        removeFile();
    };
    return (
        <div className="video-intro-detail">
            {file ? (
                <div className="file-item">
                    <FontAwesomeIcon icon={faFileAlt} />
                    <p>{file.name}</p>
                    <div className="file-item__actions">
                        <div className="file-item__loading"></div>
                        {file.isUploading && (
                            <FontAwesomeIcon icon={faSpinner} className="fa-spin" onClick={() => deleteFileHandler()} />
                        )}
                        {!file.isUploading && <FontAwesomeIcon icon={faTrash} onClick={() => deleteFileHandler()} />}
                    </div>
                </div>
            ) : null}
        </div>
    );
};

export default VideoIntroDetail;
